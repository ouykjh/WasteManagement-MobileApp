package org.agh.wastemanagementapp;

import org.agh.connector.ApiConnector;
import org.agh.map.managament.AddressPoint;
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
		url ="http://" + host + ":" + port + "/api/point?format=json&route=" + routeID ;
		Log.i("HOSTsplash", url);
		ApiConnector apiConnector = new ApiConnector(url);

		new GetRouteTask().execute(apiConnector);
	}

	private void initApiConnectorStrings(){
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.host = sharedPrefs.getString("prefHost", "NULL");
		this.port = sharedPrefs.getString("prefPort", "NULL");
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
	
	public void setRoutePoints(JSONArray jsonArray){
		Integer j = 1;
		for(int i=0; i<jsonArray.length(); i++){
			JSONObject json = null;
			try{
				json = jsonArray.getJSONObject(i);
				PointManagament.pointsList.add(new AddressPoint(json.getDouble("latitude"), json.getDouble("longitude"), j.toString()));
				++j;
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}
}
	
