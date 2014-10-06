package org.agh.wastemanagementapp;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.agh.connector.Tracker;
import org.agh.map.managament.GlobalState;
import org.agh.map.managament.PointManagament;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOptions;
import com.esri.android.map.MapOptions.MapType;
import com.esri.android.map.MapView;

public class MapActivity extends Activity {
		MapView map = null;
		GraphicsLayer graphicsLayer;
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.map);
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
			getMenuInflater().inflate(R.menu.map, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
			if(item.getItemId() == R.id.formular){
				Intent intent = new Intent(getApplicationContext(), FormularActivity.class);
				startActivity(intent);
				return true;
			}
			return true;
				
		}
		
		private void startTracking() {
			String serverAddress = GlobalState.getInstance().getServerAddress();
			
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

		private void initMap(){
			graphicsLayer = new GraphicsLayer();
			map = (MapView)findViewById(R.id.map);
			MapOptions mapOptions = new MapOptions(MapType.TOPO);
			map.setMapOptions(mapOptions);
			
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
	}	

