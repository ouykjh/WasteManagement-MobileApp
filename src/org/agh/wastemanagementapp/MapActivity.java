package org.agh.wastemanagementapp;

import org.agh.map.managament.PointManagament;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
		}
		
		public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
	        return true;
	    }
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
			switch(item.getItemId()){
			case R.id.menu_formular:
				Intent intent = new Intent(getApplicationContext(), FormularActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
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
