package org.agh.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


//TODO Tutaj nie potrzebny jest url podawany w konstur
public class ApiConnector {
	
	public String url;
	
	public ApiConnector(String host){
		this.url = host;
		Log.i("GEtRoute", this.url);
	}
	
	//Returning current data in format: YEAR-MONTH-DAY
	private static String getCurrentDate(){
		final Calendar c = Calendar.getInstance();
	    int mYear = c.get(Calendar.YEAR);
	    int mMonth = c.get(Calendar.MONTH);
	    int mDay = c.get(Calendar.DAY_OF_MONTH);
	    return mYear + "-" + mMonth + "-" + mDay;
	}
	
	public JSONArray getMobileUserRoute(){
		//TODO get from getCurrentDate !!!
		url = "http://192.168.0.101:8000/api/mobileUserRoute/?format=json&date=2015-9-9";
		HttpEntity httpEntity = null;
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
		
			HttpResponse httpResponse = httpClient.execute(httpGet);
		
			httpEntity = httpResponse.getEntity();
		} catch (ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		}
		
		JSONArray jsonArray = null;
		
		if(httpEntity != null){
			try{
				String entityResponse = EntityUtils.toString(httpEntity);
				jsonArray = new JSONArray(entityResponse);
				Log.i("TRACKER", "MobileUserRoute " + jsonArray);
			}catch(JSONException e){
				e.printStackTrace();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		return jsonArray;
	}
	
	public JSONArray GetRoute(){
		HttpEntity httpEntity = null;
		Log.i("GEtRoute", url);
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
		
			HttpResponse httpResponse = httpClient.execute(httpGet);
		
			httpEntity = httpResponse.getEntity();
		} catch (ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		}
		
		JSONArray jsonArray = null;
		
		if(httpEntity != null){
			try{
				String entityResponse = EntityUtils.toString(httpEntity);
				
				jsonArray = new JSONArray(entityResponse);
				Log.i("Entity Response : ", entityResponse);

			}catch(JSONException e){
				e.printStackTrace();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		return jsonArray;
	}
	
	public JSONObject postDataToServer(JSONObject jsonObject, String path) throws JSONException, UnsupportedEncodingException{
		String fullUrl = url + path;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(fullUrl);
		String json = "";
		
		json = jsonObject.toString();
		StringEntity se = new StringEntity(json);

		try {
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			//TODO should always return some kind of response... 
			if( responseString.length() != 0 ){
				JSONObject obj = new JSONObject(responseString);
				return obj;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
