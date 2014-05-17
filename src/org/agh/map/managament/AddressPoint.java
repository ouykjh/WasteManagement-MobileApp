package org.agh.map.managament;

import com.esri.core.geometry.Point;

public class AddressPoint {
	private Point point;
	private String address;
	
	public AddressPoint(double lattitude, double longitude, String address){
		point = new Point(lattitude,longitude);
		this.address = address;
	}
	
	public Point getPoint(){
		return point;
	}
	
	public String getAddress(){
		return address;
	}
}
