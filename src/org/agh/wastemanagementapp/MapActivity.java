package org.agh.wastemanagementapp;

import org.agh.map.managament.PointManagament;

import android.app.Activity;
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
