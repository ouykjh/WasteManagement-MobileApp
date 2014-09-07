package org.agh.wastemanagementapp;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.agh.connector.Tracker;
import org.agh.map.managament.PointManagament;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;

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
		
		private void startTracking() {
			String serverAddress = "http://192.168.0.101:8000";
			
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Tracker tracker = new Tracker(serverAddress, locationManager, getApplicationContext());
			
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
			
			map.addLayer(new ArcGISTiledMapServiceLayer("" +
			"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer"));
			map.addLayer(graphicsLayer);
			map.enableWrapAround(true);
			com.esri.core.geometry.Point centerPt = new com.esri.core.geometry.Point(29.959, 50.060);
			map.zoomToScale(centerPt, 100.00);

			graphicsLayer.removeAll();
			PointManagament.markPoints(map, graphicsLayer);
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
