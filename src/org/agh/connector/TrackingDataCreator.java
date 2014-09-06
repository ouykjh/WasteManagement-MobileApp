package org.agh.connector;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackingDataCreator {
	
	//To create MobileUserRoute model in django
	private static JSONObject route = new JSONObject();
	private static JSONObject address = new JSONObject();
	private static JSONObject mobileUser = new JSONObject();
	private static String longitude;
	private static String latitude;

	//To create Point model in django
	private static JSONObject pointRoute = new JSONObject();
	
	//Returning current data in format: YEAR-MONTH-DAY
	private static String getCurrentDate(){
		final Calendar c = Calendar.getInstance();
	    int mYear = c.get(Calendar.YEAR);
	    int mMonth = c.get(Calendar.MONTH);
	    int mDay = c.get(Calendar.DAY_OF_MONTH);
	    return mYear + "-" + mMonth + "-" + mDay;
	}
	
	public static void createMobileUserRouteData() throws JSONException{
		//TODO Smart route naming
		String routeName = "Tracking SomeUser";
		
		//TODO USER SHOULD SOMEHOW KNOW HIS ID
		int myId = 1;
		
		route.accumulate("name", routeName);
		mobileUser.put("id", Integer.toString(myId)); //expect mobileUser to exist in Django server
	}

	public static void createAddressData(String street, String number, String postCode, String city) throws JSONException{
		address.put("street", street);
		address.put("number", number);
		address.put("postCode", postCode);
		address.put("city", city);
	}
	
	public static void createPointsData(int routeId, String lon, String lat) throws JSONException{
		pointRoute.put("id", Integer.toString(routeId));
		longitude = lon;
		latitude = lat;
	}

	public static void accumulateMobileUserRouteData(JSONObject jsonObject) throws JSONException{
		jsonObject.accumulate("route", route);
		jsonObject.accumulate("date", getCurrentDate());
		jsonObject.accumulate("mobileUser", mobileUser);
	}

	public static void accumulatePointData(JSONObject jsonObject) throws JSONException {
		jsonObject.accumulate("address", address);
		jsonObject.accumulate("longitude", longitude);
		jsonObject.accumulate("latitude", latitude);
		jsonObject.accumulate("route", pointRoute);
	}
}
