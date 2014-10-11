package org.agh.map.managament;

import jcifs.util.Base64;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;import com.esri.core.portal.Portal.GetAuthCodeCallback;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

public class GlobalState  {
	
	//Singleton
	private static final GlobalState instance = new GlobalState();
	
	public static GlobalState getInstance() {
		return instance;
	}
	
	private String mobileUserRouteId;
	private String routeId;
	
	private boolean createNewRoute = true;
	private long sqliteRouteId;
	
	private int notVisitedColor = Color.WHITE;
	private int visitedColor = Color.BLUE;
	private int nextColor = Color.RED;
	private int lineColor = Color.GREEN;
	private int textColor = Color.MAGENTA;
	private STYLE style = (STYLE) SimpleMarkerSymbol.STYLE.DIAMOND;
	private int textSize = 10;
	private String serverAddress = "http://192.168.43.185:8000";
	private int myId = 2;
	private String login = "admin";
	private String password = "123qwe";
	private JSONObject loginJson = new JSONObject();
	

	
	//minTime minimum time interval between location updates, in milliseconds
	private long timeIntervalLoctionUpdate = 5000; 
	
	//minDistance minimum distance between location updates, in meters
	private long minDistanceBetweenLocationUpdate = 5;
	
	public void showAlertMsg(String message, Context context){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public int getNotVisitedColor() {
		return notVisitedColor;
	}
	
	public JSONObject getLoginJson() throws JSONException{
		loginJson.put("username", login);
		loginJson.put("password", password);
		return loginJson;
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

	public String getServerAddress() {
		return serverAddress;
	}
	
	public String getHost(){
		return serverAddress.substring(0, serverAddress.lastIndexOf(':'));
	}
	
	public int getPort(){
		return Integer.parseInt(serverAddress.substring(serverAddress.lastIndexOf(':') + 1, serverAddress.length()));
	}
	
	public void setServerAddres(String serverAddres){
		this.serverAddress = serverAddres;  
	}

	public boolean isCreateNewRoute() {
		return createNewRoute;
	}

	public void setCreateNewRoute(boolean createNewRoute) {
		this.createNewRoute = createNewRoute;
	}

	public long getSqliteRouteId() {
		return sqliteRouteId;
	}

	public void setSqliteRouteId(long sqliteRouteId) {
		this.sqliteRouteId = sqliteRouteId;
	}

	public int getMyId() {
		return myId;
	}

	public void setMyId(int myId) {
		this.myId = myId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthHeader(HttpGet httpGet){
		httpGet.setHeader("Authorization", "Basic "+Base64.encode((getLogin() + ":" + getPassword()).getBytes()));
	}
	
	public void setAuthHeader(HttpPost httpPost){
		httpPost.setHeader("Authorization", "Basic "+Base64.encode((getLogin() + ":" + getPassword()).getBytes()));
	}
	
	public void setAuthHeader(HttpPut httpPost){
		httpPost.setHeader("Authorization", "Basic "+Base64.encode((getLogin() + ":" + getPassword()).getBytes()));
	}
}
