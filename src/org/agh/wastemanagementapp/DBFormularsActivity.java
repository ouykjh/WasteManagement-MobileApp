package org.agh.wastemanagementapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import org.agh.connector.ApiConnector;
import org.agh.db.DatabaseHelper;
import org.agh.db.Formular;
import org.agh.db.FormularsAdapter;
import org.agh.jsoncreators.FormularDataCreator;
import org.agh.map.managament.GlobalState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DBFormularsActivity extends Activity {
	private Button btnSendAll;
	private ListView lvFormulars;

	private DatabaseHelper db;
	private FormularsAdapter formularAdapter;
	private List<Formular> formulars;
	
	private final String FORMULAR_PATH = "/api/formular/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_formulars_view);
		initUIElements();
		initListView();
		initButtonsOnClickListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbformulars, menu);
		return true;
	}
	private void initUIElements(){
		lvFormulars = (ListView) findViewById(R.id.listViewFormulars);
		btnSendAll = (Button) findViewById(R.id.btnSendAll);
	}
	
	private void initButtonsOnClickListeners(){
		OnClickListener onClickListener = new OnClickListener(){
			public void onClick(View v){
				switch (v.getId()){
				case R.id.btnSendAll:
					sendFormulars();
					break;
				default:
					break;
				}
			}
		};
		btnSendAll.setOnClickListener(onClickListener);
	}
	
	private void sendFormulars(){
		SendFormularTask sendFormularTask;
		FormularDataCreator formularDataCreator;
		Log.i("sendFormulars", Integer.toString(formulars.size()));
		for(int i=0; i<formulars.size(); i++){
			formularDataCreator = new FormularDataCreator(formulars.get(i));
			sendFormularTask = new SendFormularTask();
			sendFormularTask.setJsonFormular(formularDataCreator.createJsonFormular());
			StartAsyncTaskInParallel(sendFormularTask);
		}
		GlobalState.getInstance().showAlertMsg("Wys�ano formularze", getApplicationContext());
		this.finish();
	}
	
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	 private void StartAsyncTaskInParallel(SendFormularTask task) {
	     if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	         task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	     else
	         task.execute();
	 }
	
	private void initListView(){
		fillListViewData();
	}
	
	private void fillListViewData() {
		db = new DatabaseHelper(getApplicationContext());
		getFormulars();
		if(formulars.isEmpty())
			Log.i("DBFormular", "empty");
		formularAdapter = new FormularsAdapter(this, formulars);
		if (formularAdapter != null)
			lvFormulars.setAdapter(formularAdapter);
	}
	
	private void getFormulars() {
		Intent intent = getIntent();
		formulars = db.getFormularsByRouteId(intent.getLongExtra("ROUTE_ID", 1));
	}

	@Override
	protected void onDestroy(){
		if(db != null)
			db.close();
		super.onDestroy();
	}
	
	private class SendFormularTask extends AsyncTask<Void, Long, Void>{
		private JSONObject jsonFormular; 
		public void setJsonFormular(JSONObject jsonFormular){
			this.jsonFormular = jsonFormular;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
				try {
					new ApiConnector(GlobalState.getInstance().getServerAddress()).postDataToServer(jsonFormular, FORMULAR_PATH);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}

		
		
	}
}
