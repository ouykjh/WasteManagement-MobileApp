package org.agh.wastemanagementapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;

import org.agh.connector.ApiConnector;
import org.agh.map.managament.AddressPoint;
import org.agh.map.managament.GlobalState;
import org.agh.map.managament.PointManagament;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashScreenActivity extends Activity {
	private String host;
	private String port;
	private String url;
	private String routeID;
	
	private List<JSONObject> addresses = new ArrayList<JSONObject>();
	private JSONArray jsonPoints = new JSONArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		initApiConnectorStrings();
		url ="http://" + host + ":" + port;
		Log.i("HOSTsplash", url);
		ApiConnector apiConnector = new ApiConnector(url);
        try {
            if(getMobileUserData(apiConnector)) {
                Log.d("IF", Integer.toString(GlobalState.getInstance().getMyId()));
                try {
                    getRouteData(apiConnector);
                    Log.d("TRY", GlobalState.getInstance().getRouteId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                returnToLogin();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean getMobileUserData(ApiConnector apiConnector) throws ExecutionException, InterruptedException, JSONException {
        GetMobileUserTask getMobileUserTask = new GetMobileUserTask();
        JSONArray resultArray;
        JSONObject resultObject;
        getMobileUserTask.execute(apiConnector);
        resultArray = getMobileUserTask.get();

        if(resultArray == null){
            return false;
        }
        resultObject = getMobileUserTask.get().getJSONObject(0);
        Log.i("getMobileUserData", "result Here " + resultObject);
        String id = resultObject.getString("id");
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(id);
        m.find();

        GlobalState.getInstance().setMyId(Integer.parseInt(m.group()));
        return true;
    }

	private void getRouteData(ApiConnector apiConnector) throws InterruptedException, ExecutionException, JSONException{
		JSONArray result = new JSONArray();
		GetMobileUserRouteTask getMobileUserRouteTask = new GetMobileUserRouteTask();
		getMobileUserRouteTask.execute(apiConnector);
		result = getMobileUserRouteTask.get();
		Log.i("TRACKER", "result Here " + result);
		
		JSONObject json_0;
		json_0 = result.getJSONObject(0);
		
		String route = json_0.getString("route");
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(route);
		m.find();
		
		String routeId = m.group();
		
		String mobileUserRouteId = json_0.getString("resource_uri");
		Pattern.compile("\\d+");
		Matcher m2 = p.matcher(mobileUserRouteId);
		m2.find();
		mobileUserRouteId = m2.group();
		
		GlobalState.getInstance().setRouteId(routeId);
		GlobalState.getInstance().setMobileUserRouteId(mobileUserRouteId);
		
		GetRouteTask getRouteTask = new GetRouteTask();
		getRouteTask.execute(apiConnector);
	}

	private void initApiConnectorStrings(){
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.host = sharedPrefs.getString("prefHost", "NULL");
		this.port = sharedPrefs.getString("prefPort", "NULL");
		GlobalState.getInstance().setServerAddres("http://" + host + ":" + port);
		Log.i("TRACKER", "ADDRESS " + GlobalState.getInstance().getServerAddress());
		this.routeID = sharedPrefs.getString("prefRoute", "1");
	}

    private void returnToLogin(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        GlobalState.getInstance().showAlertMsg("Wrong Login or Password", getApplicationContext());
        finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	private class GetRouteTask extends AsyncTask<ApiConnector, Long, JSONArray>{

		@Override
		protected JSONArray doInBackground(ApiConnector... params) {
			return params[0].GetRoute();
		}
		
		@Override
		protected void onPostExecute(JSONArray jsonArray){
			setRoutePoints(jsonArray);
			ApiConnector apiConnector = new ApiConnector(host);
			SetAddressesRunner setAddressesRunner = new SetAddressesRunner();
			setAddressesRunner.execute(apiConnector);
		}
		
	}
	
	private class GetMobileUserTask extends AsyncTask<ApiConnector, Long, JSONArray>{
        private String name = GlobalState.getInstance().getLogin();
        private String password = GlobalState.getInstance().getPassword();

        @Override
		protected JSONArray doInBackground(ApiConnector... params) {
			return params[0].getMobileUser(name, password);
		}
		
		@Override
		protected void onPostExecute(JSONArray jsonArray){
		}
		
	}

    private class GetMobileUserRouteTask extends AsyncTask<ApiConnector, Long, JSONArray>{

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].getMobileUserRoute();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray){
        }

    }

	private class GetAddressTask extends AsyncTask<Void, Long, JSONObject>{
		private String addressId;
		private ApiConnector apiConnector = new ApiConnector(host);
		private int taskId;
		
		public void setAddressId(String addressId){
			this.addressId = addressId;
		}
		public void setTaskId(int taskId){
			this.taskId = taskId;
		}
		
		@Override
		protected JSONObject doInBackground(Void... arg0) {
			return apiConnector.getAddress(addressId);
		}
		
		@Override
		protected void onPostExecute(JSONObject jsonObject){
			setAddresses(taskId, jsonObject);
			Log.i("back", Boolean.toString(addresses.isEmpty()));
			Log.i("onPostExecute", "qwere");
			try {
				Log.i("onPostExecute", jsonObject.getString("city"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class SetAddressesRunner extends AsyncTask<ApiConnector, Long, Void>{

		@Override
		protected Void doInBackground(ApiConnector... arg0) {
			Integer j = 0;
			Log.i("jsonPoints", Integer.toString(jsonPoints.length()));
			try {
				while(j.intValue() < jsonPoints.length()) {
					GetAddressTask getAddressTask = new GetAddressTask();
					getAddressTask.setTaskId(j);
					getAddressTask.setAddressId(jsonPoints.getJSONObject(j).getString("address"));
					StartAsyncTaskInParallel(getAddressTask);
					j++;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result){
			Log.i("back1", Boolean.toString(addresses.isEmpty()));
			Intent intent = new Intent(SplashScreenActivity.this, MapActivity.class);
			startActivity(intent);
			finish();
		}
		
	}
	
	private void setAddresses(int id, JSONObject jsonAddress){
		Log.i("setAddress", Boolean.toString(addresses.isEmpty()));
		try {
			PointManagament.pointsList.get(id).setAddress(jsonAddress.getString("city") + 
					" " + jsonAddress.getString("street") + " " + jsonAddress.getString("number"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("setAddress", PointManagament.pointsList.get(id).getAddress());
	}
	
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	 private void StartAsyncTaskInParallel(GetAddressTask task) {
	     if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	         task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	     else
	         task.execute();
	 }
	
	public void setRoutePoints(JSONArray jsonArray){
		Integer j = 1;
		JSONObject jsonPoint = null;
		Log.i("jsonArrat.length", Integer.toString(jsonArray.length()));
		for(int i=0; i<jsonArray.length(); i++){
			try{
				jsonPoints = jsonArray;
				jsonPoint = jsonArray.getJSONObject(i);
				PointManagament.pointsList.add(new AddressPoint(jsonPoint.getLong("id"), jsonPoint.getDouble("latitude"), 
						jsonPoint.getDouble("longitude"), j.toString()));
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}
}
	
