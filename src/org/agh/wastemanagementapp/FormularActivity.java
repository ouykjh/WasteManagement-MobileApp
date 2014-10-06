package org.agh.wastemanagementapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.agh.connector.ApiConnector;
import org.agh.db.DatabaseHelper;
import org.agh.db.Formular;
import org.agh.db.Route;
import org.agh.map.managament.AddressPoint;
import org.agh.map.managament.GlobalState;
import org.agh.map.managament.PointManagament;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularActivity extends Activity {
	
	private Button btnSave;
	private Button btnSend;
	private EditText etPercentageFilling;
	private EditText etAmountOfBins;
	private Spinner spAddress;
	
	private GlobalState globalState = GlobalState.getInstance();
	private Formular formular = new Formular();
	private List<String> addresses = new ArrayList<String>();
	private List<Long> addressesIds = new ArrayList<Long>();
	
	private final String FORMULAR_PATH = "/api/formular";
	private final String SAVED_MESSAGE = "Formularz zosta³ zapisany!";
	private final String BINS_AMOUNT_CLEAR = "Pole \" iloœæ koszy \" nie mo¿e byæ puste!";
	private final String FILL_PERCENTAGE_CLEAR = "Pole \" zawartoœæ procentowa \" nie mo¿e byæ puste!";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formular_view);
		initUIElements();
		initButtonsListener();
		initSpinner();
		
	}
	
	private void initSpinner(){
		findNearestPoints();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, addresses);
		spAddress.setAdapter(adapter);
		spAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int id, long position) {
				formular.setPointId(addressesIds.get((int) position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spAddress.setSelection(1);
	}
	
	private void findNearestPoints() {
		for(AddressPoint ap : PointManagament.pointsList){
			if(distFrom(ap.getPoint().getX(), ap.getPoint().getY(), getLocation().getLatitude(), getLocation().getLongitude()) < globalState.getMinDistanceBetweenLocationUpdate()){
				addresses.add(ap.getAddress());
				addressesIds.add(ap.getId());
			}
		}
	}

	private void initUIElements(){
		btnSave = (Button) findViewById(R.id.btnSendFormular);
		btnSend = (Button) findViewById(R.id.btnSaveToDatabase);
		etAmountOfBins = (EditText) findViewById(R.id.etAmountOfBins);
		etPercentageFilling = (EditText) findViewById(R.id.etPercentageFilling);
		spAddress = (Spinner) findViewById(R.id.spAddress);
	}
	/*TODO
	 * warning information about turning on GPS
	 */
	
	private void initButtonsListener(){
		OnClickListener onClickListener = new OnClickListener(){
			public void onClick(View v) {
				switch (v.getId()){
				case R.id.btnSaveToDatabase:
					saveFormularToDatabase();
					break;
				case R.id.btnSend:
//					try {
//						sendFormular();
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
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
		
		if (checkFormular()){
			if(globalState.isCreateNewRoute()){
				Route route = new Route();
				//route.setExternalRouteId(Integer.parseInt(globalState.getRouteId()));
				route.setExternalRouteId(1);

				route.setName("kuba");
				globalState.setSqliteRouteId(db.createRoute(route));
			}
			formular.setAmountOfBins(Integer.parseInt(etAmountOfBins.getText().toString()));
			formular.setPercentageFilling(Integer.parseInt(etPercentageFilling.getText().toString()));
			formular.setRouteId(globalState.getSqliteRouteId());
			//formular.setMobileUserId(Integer.parseInt(globalState.getMobileUserRouteId()));
			formular.setMobileUserId(1);

			db.createFormular(formular);
			globalState.showAlertMsg(SAVED_MESSAGE, getApplicationContext());
			this.finish();
		}
	}
	
	private boolean checkFormular(){
		boolean formular = true;
		String msg = "";
		if(etAmountOfBins.getText().toString().matches("")){
			msg+=(BINS_AMOUNT_CLEAR);
			formular = false;
		}
		if(etPercentageFilling.getText().toString().matches("")){
			msg+=("\n" + FILL_PERCENTAGE_CLEAR);
			formular = false;
		}
		if(formular == false){
			globalState.showAlertMsg(msg, getApplicationContext());
		}
		return formular;
	}
	
//	private void sendFormular() throws JSONException{
//		JSONObject formularJson = new JSONObject();
//		formularJson.put("pointId", formular.getPointId());
//		formularJson.put("amountOfBins", formular.getAmountOfBins());
//		formularJson.put("filledPercentage", formular.getPercentageFilling());
//		formularJson.put("mobileUserId", formular.getMobileUserId());
//		new SendFormularTask().execute(formularJson);
//	}
//	
//	private class SendFormularTask extends AsyncTask<JSONObject, Long, Void>{
//
//		@Override
//		protected Void doInBackground(JSONObject... params) {
//			try {
//				new ApiConnector(GlobalState.getInstance().getServerAddress()).postDataToServer(params[0], FORMULAR_PATH);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//		
//	}
	
	/*TODO 	getLocation should be in
	 * 		GlobalState
	 */
	private Location getLocation(){
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return location;
	}
	
	private static double distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 6371; //kilometers
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = (double) (earthRadius * c);

	    return dist;
	    }
}
