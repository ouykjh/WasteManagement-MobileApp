package org.agh.connector;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.agh.map.managament.GlobalState;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Tracker {
	
	private final ApiConnector API_CONNECTOR;
	private final LocationManager LOCATION_MANAGER;
	private final String POINT_PATH_API = "/api/point/";
	private int routeId;
	
	public Tracker(String url, LocationManager locationManager){
		API_CONNECTOR = new ApiConnector(url);
		this.LOCATION_MANAGER = locationManager;
	}
	
	private void setRouteId(int routeId){
		this.routeId = routeId;
	}
	
	private class RouteInitializer extends AsyncTask<Void, Long, Integer>{

		@Override
		protected Integer doInBackground(Void...params) {
			try {
				return postMobileUserRoute();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Integer result){
			Log.i("TRACKER", "Route created with id = " + result);
		}
	}
	
	//TODO Make it return proper message
	private class LocationSender extends AsyncTask<JSONObject, Long, JSONObject>{

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			try {
				postPoints(params[0]);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(JSONObject result){
		}
	}
	
	private int postMobileUserRoute() throws JSONException, UnsupportedEncodingException{
		String path = "/api/mobileUserRoute/";
		TrackingDataCreator.createMobileUserRouteData();
		JSONObject mobileUserRouteJson = new JSONObject();
		TrackingDataCreator.accumulateMobileUserRouteData(mobileUserRouteJson);
		
		//Post data, get responseJson and return created route_id
		JSONObject responseJson = new JSONObject();
		responseJson = API_CONNECTOR.postDataToServer(mobileUserRouteJson, path);
		
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(responseJson.get("route").toString());
		m.find();
		return(Integer.parseInt(m.group()));
	}
	
	private class MyLocationListener implements LocationListener {
		
	    @Override
	    public void onLocationChanged(Location location) {
	    	
	    	if(location == null){
	    		Log.w("TRACKER", "Location of the device is unknown");
	    	}else{
		        Log.i("TRACKER", "Sending location: latitude: " + location.getLatitude() + " longitude " + location.getLongitude());
		        
				JSONObject pointJson = new JSONObject();
				try {
					TrackingDataCreator.createAddressData("Kochanowskiego", "16", "33-330", "Grybow");
					TrackingDataCreator.createPointsData( routeId, Double.toString(location.getLatitude()), Double.toString(location.getLongitude()) );
					TrackingDataCreator.accumulatePointData(pointJson);
					new LocationSender().execute(pointJson);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    	}
	    }

	    @Override
	    public void onProviderDisabled(String provider) {}

	    @Override
	    public void onProviderEnabled(String provider) {}

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	}


	private void postPoints(JSONObject pointJson) throws JSONException, UnsupportedEncodingException{
		API_CONNECTOR.postDataToServer(pointJson, POINT_PATH_API);
	}
	
	/*	TODO initedRoute should be saved in SQLLITE
	 * 	Because if user closes the app it should NOT
	 * 	Create new route, but check if trackingRoute
	 *  Was created on particular day and send points
	 *  To it 
	 */	
	public void initTrackingRoute() throws InterruptedException, ExecutionException{
		RouteInitializer routeInitializer = new RouteInitializer();
		routeInitializer.execute();
		
		//Get created route id and set
		setRouteId(routeInitializer.get());
	}
	
	public void sendLocation() throws UnsupportedEncodingException, JSONException{
		LocationListener locationListener = new MyLocationListener();
		LOCATION_MANAGER.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
												GlobalState.minDistanceBetweenLocationUpdate, 
												GlobalState.minDistanceBetweenLocationUpdate, 
												locationListener);
	}
	
}
