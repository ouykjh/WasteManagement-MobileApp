package org.agh.connector;

import android.util.Log;

import org.agh.map.managament.GlobalState;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;


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
	    int mMonth = c.get(Calendar.MONTH) + 1;
	    int mDay = c.get(Calendar.DAY_OF_MONTH);
	    return mYear + "-" + mMonth + "-" + mDay;
	}

    public JSONArray getMobileUser(String name, String password){
        String serverAddress = GlobalState.getInstance().getServerAddress();
        String mobileUserUrl = serverAddress + "/api/mobileUser/?format=json&username=" + name;
        HttpEntity httpEntity = null;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(mobileUserUrl);
            GlobalState.getInstance().setAuthHeader(httpGet);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 401)
                return null;
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
            }catch(JSONException e){
                e.printStackTrace();
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }

        return jsonArray;
    }

	public JSONArray getMobileUserRoute(){
		String serverAddress = GlobalState.getInstance().getServerAddress();
        String mobileUser = Integer.toString(GlobalState.getInstance().getMyId());
		String mobileUserRouteApiUrl = serverAddress + "/api/mobileUserRoute/?format=json&date=" + getCurrentDate() + "&mobileUser=" + mobileUser;
		HttpEntity httpEntity = null;
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(mobileUserRouteApiUrl);
			GlobalState.getInstance().setAuthHeader(httpGet);
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
			}catch(JSONException e){
				e.printStackTrace();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		return jsonArray;
	}
	
	public JSONArray GetRoute(){
		String ServerAddress = GlobalState.getInstance().getServerAddress();
		HttpEntity httpEntity = null;
		Log.i("GEtRoute", url);
		String routeId = GlobalState.getInstance().getRouteId();
		Log.i("GEtRoute", "ROUTE ID = " + routeId);
		String getRouteUrl = ServerAddress + "/api/point/?format=json&route=" + routeId;
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(getRouteUrl);
			GlobalState.getInstance().setAuthHeader(httpGet);
		
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
	
	public JSONObject getAddress(String addressId){
		String ServerAddress = GlobalState.getInstance().getServerAddress();
		HttpEntity httpEntity = null;
		Log.i("GEtRoute", "POINT ID = " + addressId);
		String getRouteUrl = ServerAddress + addressId + "?format=json";
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(getRouteUrl);
			GlobalState.getInstance().setAuthHeader(httpGet);
		
			HttpResponse httpResponse = httpClient.execute(httpGet);
		
			httpEntity = httpResponse.getEntity();
		} catch (ClientProtocolException e){
			e.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		}
		
		JSONObject jsonObject = null;
		
		if(httpEntity != null){
			try{
				String entityResponse = EntityUtils.toString(httpEntity);
				
				jsonObject = new JSONObject(entityResponse);
				Log.i("Entity Response : ", entityResponse);

			}catch(JSONException e){
				e.printStackTrace();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		
		return jsonObject;
	}

	
	public JSONObject postDataToServer(JSONObject jsonObject, String path) throws JSONException, UnsupportedEncodingException{
		String fullUrl = url + path;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(fullUrl);
		GlobalState.getInstance().setAuthHeader(post);
		String json = "";
		
		json = jsonObject.toString();
		StringEntity se = new StringEntity(json);

		try {
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			Log.i("formular", "response " + responseString);
			JSONObject oj = new JSONObject(responseString);
			Log.i("formular", "response1 " + oj);
			//TODO should always return some kind of response... 
			if( responseString.length() != 0 ){
				JSONObject obj = new JSONObject(responseString);
				Log.i("formular", "response " + obj);
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
	
	public JSONObject putDataToServer(JSONObject jsonObject, String path) throws JSONException, UnsupportedEncodingException {
        String fullUrl = url + path;
        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(fullUrl);
        GlobalState.getInstance().setAuthHeader(put);
        String json = "";

        json = jsonObject.toString();
        StringEntity se = new StringEntity(json);

        try {
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");
            put.setEntity(se);
            HttpResponse response = client.execute(put);
            Log.i("TRACKER", "response " + response);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

            //TODO should always return some kind of response...
            if (responseString.length() != 0) {
                JSONObject obj = new JSONObject(responseString);
                Log.i("TRACKER", "response " + obj);

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
