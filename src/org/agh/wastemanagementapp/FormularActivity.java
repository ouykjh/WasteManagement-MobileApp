package org.agh.wastemanagementapp;

import org.agh.db.DatabaseHelper;
import org.agh.db.Formular;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormularActivity extends Activity {
	
	private Button btnSave;
	private Button btnSend;
	private EditText etLongitude;
	private EditText etLatitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formular_view);
		initUIElements();
		setLocation();
		initButtonsListener();
	}

	private void initUIElements(){
		btnSave = (Button) findViewById(R.id.btnSendFormular);
		btnSend = (Button) findViewById(R.id.btnSaveToDatabase);
		etLatitude = (EditText) findViewById(R.id.etLatitude);
		etLongitude = (EditText) findViewById(R.id.etLongitude);
	}
	/*TODO
	 * warning information about turning on GPS
	 */
	private void setLocation(){
		etLatitude.setText(String.valueOf(getLocation().getLatitude()));
		etLatitude.setEnabled(false);
		etLongitude.setText(String.valueOf(getLocation().getLongitude()));
		etLongitude.setEnabled(false);
	}
	
	private void initButtonsListener(){
		OnClickListener onClickListener = new OnClickListener(){
			public void onClick(View v) {
				switch (v.getId()){
				case R.id.btnSaveToDatabase:
					saveFormularToDatabase();
					break;
				case R.id.btnSend:
					sendFormular();
					break;
				default:
					break;
				}	
			}
		};
		btnSave.setOnClickListener(onClickListener);
		btnSend.setOnClickListener(onClickListener);
	}
	
	private void saveFormularToDatabase(){
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
		Formular formular = new Formular();
		
		formular.setLatitude(Double.parseDouble(etLatitude.getText().toString()));
		formular.setLongitude(Double.parseDouble(etLongitude.getText().toString()));
		formular.setRouteId(1);
		db.createFormular(formular);
		Toast.makeText(getApplicationContext(), "Formularz zosta³ zapisany!", Toast.LENGTH_SHORT).show();
		this.finish();
	}
	
	private void sendFormular(){
		
	}
	
	/*TODO 	getLocation should be in
	 * 		GlobalState
	 */
	private Location getLocation(){
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return location;
	}
}
