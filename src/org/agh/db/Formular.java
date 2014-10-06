package org.agh.db;

public class Formular {
	int id;
	long routeId;
	long pointId;
	long mobileUserId;
	int amountOfBins;
	int percentageFilling;
	
	public Formular(){
	}
	
	public Formular(long routeId, int amountOfBins, int percentageFilling) {
		this.routeId = routeId;
		this.amountOfBins = amountOfBins;
		this.percentageFilling = percentageFilling;
	}
	
	public long getPointId() {
		return pointId;
	}

	public void setPointId(long pointId) {
		this.pointId = pointId;
	}

	public long getMobileUserId() {
		return mobileUserId;
	}

	public void setMobileUserId(long mobileUserId) {
		this.mobileUserId = mobileUserId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAmountOfBins() {
		return amountOfBins;
	}

	public void setAmountOfBins(int amountOfBins) {
		this.amountOfBins = amountOfBins;
	}

	public int getPercentageFilling() {
		return percentageFilling;
	}

	public void setPercentageFilling(int percentageFilling) {
		this.percentageFilling = percentageFilling;
	}

	public long getRouteId() {
		return routeId;
	}
	public void setRouteId(long routeId2) {
		this.routeId = routeId2;
	}
	
}
