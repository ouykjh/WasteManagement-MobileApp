
package org.agh.wastemanagementapp;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.agh.connector.Tracker;
import org.agh.map.managament.PointManagament;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button btnFollowRoute;
	Button btnSendToDatabase;
	Button btnSettings;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		initUIElements();
		initButtonsOnClickListeners();
		/*TODO 	Application should work when server is not responding
		*		Proper message should be shown instead of
		*		Just turning of the app
		*/
		//startTracking();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
	//TODO SERVER ADDRESS SHOULD BE KNOWN SOMEHOW
	private void startTracking() {
		String serverAddress = "http://192.168.0.101:8000";
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Tracker tracker = new Tracker(serverAddress, locationManager);
		
		try {
			tracker.initTrackingRoute();
			tracker.sendLocation();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initUIElements(){
		btnFollowRoute = (Button) findViewById(R.id.btnFollowRoute);
		btnSendToDatabase = (Button) findViewById(R.id.btnSendSavedData);
		btnSettings = (Button) findViewById(R.id.btnSettings);
	}
	
	private void initButtonsOnClickListeners(){
		OnClickListener onClickListener = new OnClickListener(){
			public void onClick(View v){
				switch (v.getId()){
				case R.id.btnFollowRoute:
					openMap();
					break;
				case R.id.btnSendSavedData:
					openDatabase(); 
					break;
				case R.id.btnSettings:
					openSettings(); 
					break;
				default:
					break;
				}
			}
		};
		btnFollowRoute.setOnClickListener(onClickListener);
		btnSendToDatabase.setOnClickListener(onClickListener);
		btnSettings.setOnClickListener(onClickListener);
	}
	
	private void openSettings(){
		Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivity(intent);
	}
	
	private void openMap(){
		Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
		if (PointManagament.pointsList.isEmpty())
			intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
		startActivity(intent);
	}
	
	private void openDatabase(){
		Intent intent = new Intent(getApplicationContext(), DatabaseViewActivity.class);
		startActivity(intent);
	}

}
