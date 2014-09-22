package org.agh.map.managament;

import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

import android.graphics.Color;

public class GlobalState  {
	
	//Singleton
	private static final GlobalState instance = new GlobalState();
	
	public static GlobalState getInstance() {
		return instance;
	}
	
	private String mobileUserRouteId;
	private String routeId;
	
	private int notVisitedColor = Color.WHITE;
	private int visitedColor = Color.BLUE;
	private int nextColor = Color.RED;
	private int lineColor = Color.GREEN;
	private int textColor = Color.MAGENTA;
	private STYLE style = (STYLE) SimpleMarkerSymbol.STYLE.DIAMOND;
	private int textSize = 10;
	
	//minTime minimum time interval between location updates, in milliseconds
	private long timeIntervalLoctionUpdate = 5000; 
	
	//minDistance minimum distance between location updates, in meters
	private long minDistanceBetweenLocationUpdate = 5;
	
	public int getNotVisitedColor() {
		return notVisitedColor;
	}

	public void setNotVisitedColor(int notVisitedColor) {
		this.notVisitedColor = notVisitedColor;
	}

	public int getVisitedColor() {
		return visitedColor;
	}

	public void setVisitedColor(int visitedColor) {
		this.visitedColor = visitedColor;
	}

	public int getNextColor() {
		return nextColor;
	}

	public void setNextColor(int nextColor) {
		this.nextColor = nextColor;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public STYLE getStyle() {
		return style;
	}

	public void setStyle(STYLE style) {
		this.style = style;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public long getTimeIntervalLoctionUpdate() {
		return timeIntervalLoctionUpdate;
	}

	public void setTimeIntervalLoctionUpdate(long timeIntervalLoctionUpdate) {
		this.timeIntervalLoctionUpdate = timeIntervalLoctionUpdate;
	}

	public long getMinDistanceBetweenLocationUpdate() {
		return minDistanceBetweenLocationUpdate;
	}

	public void setMinDistanceBetweenLocationUpdate(
			long minDistanceBetweenLocationUpdate) {
		this.minDistanceBetweenLocationUpdate = minDistanceBetweenLocationUpdate;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getMobileUserRouteId() {
		return mobileUserRouteId;
	}

	public void setMobileUserRouteId(String mobileUserRouteId) {
		this.mobileUserRouteId = mobileUserRouteId;
	}
}
