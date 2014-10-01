package org.agh.wastemanagementapp;

import java.util.List;

import org.agh.db.DatabaseHelper;
import org.agh.db.Formular;
import org.agh.db.FormularsAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class DBFormularsActivity extends Activity {
	private Button btnSelectAll;
	private Button btnSend;
	private ListView lvFormulars;

	private DatabaseHelper db;
	private FormularsAdapter formularAdapter;
	private List<Formular> formulars;

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
		btnSelectAll = (Button) findViewById(R.id.btnSelectAll);
		btnSend = (Button) findViewById(R.id.btnSend);
	}
	
	private void initButtonsOnClickListeners(){
		OnClickListener onClickListener = new OnClickListener(){
			public void onClick(View v){
				switch (v.getId()){
				case R.id.btnSelectAll:
					selectAllCheckboxes(v);
					break;
				case R.id.btnSend:
					sendFormulars(); 
					break;
				
				default:
					break;
				}
			}
		};
		btnSelectAll.setOnClickListener(onClickListener);
		btnSend.setOnClickListener(onClickListener);
	}
	
	private void selectAllCheckboxes(View v){
        int size = lvFormulars.getCount();
		boolean check = lvFormulars.isItemChecked(0);
	    for(int i = 0; i < size; i++)
	        lvFormulars.setItemChecked(i, !check);
	}
	
	private void sendFormulars(){
		
	}
	
	private void initListView(){
		fillListViewData();
	}
	
	private void fillListViewData() {
		db = new DatabaseHelper(getApplicationContext());
		getFormulars();
		if(formulars.isEmpty())
			Log.i("yodo", "pusty");
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
}
