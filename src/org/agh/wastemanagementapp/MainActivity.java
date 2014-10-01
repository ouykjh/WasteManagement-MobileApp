package org.agh.wastemanagementapp;

import org.agh.map.managament.PointManagament;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

