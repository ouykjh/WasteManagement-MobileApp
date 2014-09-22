package org.agh.db;

public class Formular {
	int id;
	long routeId;
	double longitude;
	double latitude;
	
	public Formular(){
	}
	
	public Formular(long routeId, double longitude, double latitude) {
		this.routeId = routeId;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public long getRouteId() {
		return routeId;
	}
	public void setRouteId(long routeId2) {
		this.routeId = routeId2;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
