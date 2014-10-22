package org.agh.jsoncreators;

import org.agh.db.Formular;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FormularDataCreator {
	private Formular formular;
	
	public FormularDataCreator(Formular formular){
		this.formular = formular;
	}
	
	public JSONObject createJsonFormular(){
		JSONObject formularJson = new JSONObject();
		JSONObject mobileUserJson = new JSONObject();
		JSONObject pointJson = new JSONObject();
		
		try {
			mobileUserJson.put("id", formular.getMobileUserId());
			Log.i("mobileid", Long.toString(formular.getMobileUserId()));
			pointJson.put("id", formular.getPointId());
			Log.i("pointid", Long.toString(formular.getPointId()));
			formularJson.put("point", pointJson);
			Log.i("json", Integer.toString((int) formular.getPointId()));
			formularJson.put("amountOfBins", formular.getAmountOfBins());
			Log.i("json", Integer.toString(formular.getAmountOfBins()));
			formularJson.put("percentageFilling", formular.getPercentageFilling());
			Log.i("json", Integer.toString(formular.getPercentageFilling()));
			formularJson.put("mobileUser", mobileUserJson);
			Log.i("json", Integer.toString((int) formular.getMobileUserId()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formularJson;

	}
	
}
