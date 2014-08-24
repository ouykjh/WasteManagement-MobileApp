package org.agh.map.managament;

import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

import android.graphics.Color;

public class GlobalState  {
	public static int notVisitedColor = Color.WHITE;
	public static int visitedColor = Color.BLUE;
	public static int nextColor = Color.RED;
	public static int lineColor = Color.GREEN;
	public static int textColor = Color.MAGENTA;
	public static STYLE style = (STYLE) SimpleMarkerSymbol.STYLE.DIAMOND;
	public static int textSize = 10;
	
	//minTime minimum time interval between location updates, in milliseconds
	public static long timeIntervalLoctionUpdate = 5000; 
	
	//minDistance minimum distance between location updates, in meters
	public static long minDistanceBetweenLocationUpdate = 5;
}
