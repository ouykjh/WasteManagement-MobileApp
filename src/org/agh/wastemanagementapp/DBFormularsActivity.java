package org.agh.wastemanagementapp;

import java.util.List;

import org.agh.db.DatabaseHelper;
import org.agh.db.Formular;
import org.agh.db.FormularsAdapter;
import org.agh.db.Route;
import org.agh.db.RoutesAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DBFormularsActivity extends Activity {
	private DatabaseHelper db;
	private ListView lvFormulars;
	private FormularsAdapter formularAdapter;
	private List<Formular> formulars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_formulars_view);
		lvFormulars = (ListView) findViewById(R.id.listViewFormulars);
		initListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dbformulars, menu);
		return true;
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
