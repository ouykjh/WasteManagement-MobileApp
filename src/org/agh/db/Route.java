package org.agh.db;

public class Route {
	
	long id;
	int externalRouteId;
	String name;
	
	public Route(){
	}
	
	public Route(int externalRouteId, String name) {
		this.externalRouteId = externalRouteId;
		this.name = name;
	}
	
	public int getExternalRouteId() {
		return externalRouteId;
	}
	public void setExternalRouteId(int externalRouteId) {
		this.externalRouteId = externalRouteId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
