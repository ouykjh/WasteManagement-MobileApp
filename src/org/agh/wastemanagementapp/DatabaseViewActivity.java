package org.agh.wastemanagementapp;

import java.util.List;

import org.agh.db.DatabaseHelper;
import org.agh.db.Formular;
import org.agh.db.Route;
import org.agh.db.RoutesAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DatabaseViewActivity extends Activity {
	private ListView lvRoutes;
	
	private DatabaseHelper db;
	private RoutesAdapter routeAdapter;
	private List<Route> routes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_view);
		initUIElements();
		initListView();
		
		Route route1 = new Route(1, "kuba");
		Route route2 = new Route(1, "piotrek");

		long route1Id = db.createRoute(route1);
		long route2Id = db.createRoute(route2);

		
		Formular formular1 = new Formular(route1Id, 50.234, 19.3435);
		Formular formular2 = new Formular(route2Id, 10.234, 49.3435);

		long formular1Id = db.createFormular(formular1);
		long formular6Id = db.createFormular(formular1);
		long formular7Id = db.createFormular(formular1);
		long formular2Id = db.createFormular(formular2);
		long formular3Id = db.createFormular(formular2);
		long formular4Id = db.createFormular(formular2);
		long formular5Id = db.createFormular(formular2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_view, menu);
		return true;
	}
	
	private void initUIElements(){
		lvRoutes = (ListView) findViewById(R.id.listViewRoutes);
	}
	
	private void initListView(){
		fillListViewData();
	    initListViewOnItemClick();
	}

	private void fillListViewData() {
		db = new DatabaseHelper(getApplicationContext());
		getAllRoutes();
		routeAdapter = new RoutesAdapter(this, routes);
		lvRoutes.setAdapter(routeAdapter);
	}

	private void initListViewOnItemClick() {
		lvRoutes.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Route route = routes.get(position);
				Intent intent = new Intent(getApplicationContext(), DBFormularsActivity.class);
				intent.putExtra("ROUTE_ID", route.getId());
				startActivity(intent);
			}
			
		});
	}
	
	private void getAllRoutes() {
		routes = db.getAllRoutes();
	}

	@Override
	protected void onDestroy(){
		if(db != null)
			db.close();
		super.onDestroy();
	}
}
