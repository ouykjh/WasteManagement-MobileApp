package org.agh.wastemanagementapp;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.agh.connector.ApiConnector;
import org.agh.map.managament.AddressPoint;
import org.agh.map.managament.GlobalState;
import org.agh.map.managament.PointManagament;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;

public class SplashScreenActivity extends Activity {
	private String host;
	private String port;
	private String url;
	private String routeID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		initApiConnectorStrings();
		url ="http://" + host + ":" + port;
		Log.i("HOSTsplash", url);
		ApiConnector apiConnector = new ApiConnector(url);
		try {
			getRouteData(apiConnector);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
			Intent intent = new Intent(SplashScreenActivity.this, MapActivity.class);
			setRoutePoints(jsonArray);
			startActivity(intent);
			finish();
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
	
	public void setRoutePoints(JSONArray jsonArray){
		Integer j = 1;
		for(int i=0; i<jsonArray.length(); i++){
			JSONObject json = null;
			try{
				json = jsonArray.getJSONObject(i);
				PointManagament.pointsList.add(new AddressPoint(json.getLong("id"), json.getDouble("latitude"), json.getDouble("longitude"), j.toString()));
				++j;
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}
}
	
