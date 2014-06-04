package org.agh.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ApiConnector {
	
	public String url;
	
	public ApiConnector(String host){
		this.url = host;
		Log.i("GEtRoute", this.url);
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
				
				Log.i("Entity Response : ", entityResponse);
				
				jsonArray = new JSONArray(entityResponse);
			}catch(JSONException e){
				e.printStackTrace();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		return jsonArray;
	}
	
	public String postDataToServer() throws JSONException, UnsupportedEncodingException{
		String url ="http://192.168.0.103:8000/api/route/";
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("name", "kuba"));
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		int status = 0;
		String json = "";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("name", "kuba");
		json = jsonObject.toString();
		StringEntity se = new StringEntity(json);
		
		try {
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			status = response.getStatusLine().getStatusCode();
			Log.i("tag", "Post status: " + status);
				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.toString(status);
	}
	
}

