package org.agh.connector;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackingDataCreator {
	
	//To create MobileUserRoute model in django
	private static JSONObject trackingRoute = new JSONObject();
	private static JSONObject route = new JSONObject();
	private static JSONObject mobileUser = new JSONObject();
	private static String longitude;
	private static String latitude;
	private static String mobileUserRouteId;

	//To create Point model in django
	private static JSONObject pointRoute = new JSONObject();
	
	public static void createMobileUserRouteData(String id) throws JSONException{
		//TODO Smart route naming
		String routeName = "Tracking SomeUser";
		trackingRoute.put("name", routeName);
		
		//TODO USER SHOULD SOMEHOW KNOW HIS ID
		int myId = 1;
		mobileUserRouteId = id;
		mobileUser.put("id", myId);
		
		route.put("id", mobileUserRouteId);
	}
	
	public static void createPointsData(int routeId, String lon, String lat) throws JSONException{
		pointRoute.put("id", Integer.toString(routeId));
		longitude = lon;
		latitude = lat;
	}

	public static void accumulateMobileUserRouteData(JSONObject jsonObject) throws JSONException{
		jsonObject.put("trackingRoute", trackingRoute);
		jsonObject.put("id", mobileUserRouteId);
		jsonObject.put("date", "2014-09-07");
		jsonObject.put("mobileUser", mobileUser);
		jsonObject.put("route",route);
	}

	public static void accumulatePointData(JSONObject jsonObject) throws JSONException {
		jsonObject.accumulate("longitude", longitude);
		jsonObject.accumulate("latitude", latitude);
		jsonObject.accumulate("route", pointRoute);
	}
}
