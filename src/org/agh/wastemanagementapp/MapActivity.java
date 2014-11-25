package org.agh.wastemanagementapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;

import org.agh.connector.Tracker;
import org.agh.map.managament.GlobalState;
import org.agh.map.managament.PointManagament;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class MapActivity extends Activity {
		MapView map = null;
		GraphicsLayer graphicsLayer;
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.map);
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(true);
			initMap();	
			/*TODO 	Application should work when server is not responding
			*		Proper message should be shown instead of
			*		Just turning of the app
			*/
			startTracking();
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            GlobalState globalState = new GlobalState();
            String time = sharedPrefs.getString("prefSyncFrequency", "5000");
            Log.i("time" , time);
            globalState.setTimeIntervalLoctionUpdate(Integer.parseInt(time)*1000);
            getMenuInflater().inflate(R.menu.map, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()){
                case R.id.formular:
                    openFormular();
                    break;
                case R.id.database:
                openDatabase();
                    break;
                case R.id.settings:
                    openSettings();
                    break;
                default:
                    break;
            }
			return true;
				
		}
		
		private void startTracking() {
			String serverAddress = GlobalState.getInstance().getServerAddress();
			
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Tracker tracker = new Tracker(serverAddress, locationManager);
			
			try {
				tracker.initTrackingRoute();
				Log.i("TRACKER", "Tracking started");
				tracker.sendLocation();
				Log.i("TRACKER", "Sending location");
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

		private void initMap(){
			graphicsLayer = new GraphicsLayer();
			map = (MapView)findViewById(R.id.map);
			map.addLayer(graphicsLayer);
			map.enableWrapAround(true);
			graphicsLayer.removeAll();
			try {
				PointManagament.markPoints(map, graphicsLayer);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		protected void onPause() {
			super.onPause();
			map.pause();
		}

		protected void onResume() {
			super.onResume();
			map.unpause();
		}

    private void openSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    private void openDatabase(){
        Intent intent = new Intent(getApplicationContext(), DatabaseViewActivity.class);
        startActivity(intent);
    }

    private void openFormular(){
        Location location = map.getLocationDisplayManager().getLocation();
        Intent intent = new Intent(getApplicationContext(), FormularActivity.class);
        startActivity(intent);
    }
	}	

