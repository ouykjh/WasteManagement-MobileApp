package org.agh.map.managament;

import com.esri.core.geometry.Point;

public class AddressPoint {
	private long id;
	private Point point;
	private String address;
	
	public AddressPoint(long id, double lattitude, double longitude, String address){
		point = new Point(lattitude,longitude);
		this.id = id;
		this.address = address;
	}
	
	public AddressPoint() {
	}

	public Point getPoint(){
		return point;
	}
	
	public Long getId(){
		return id;
	}
	
	public String getAddress(){
		return address;
	}
}
