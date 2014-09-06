package org.agh.wastemanagementapp;

import java.util.List;

import org.agh.db.DatabaseHelper;
import org.agh.db.models.Formular;
import org.agh.db.models.Route;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class DatabaseViewActivity extends Activity {
	private TextView txtView;
	DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_view);
		txtView = (TextView) findViewById(R.id.dbRoutes);
		String routes = "";
		String formulars = "";
		db = new DatabaseHelper(getApplicationContext());
		
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

		//Testing adding routes to db 
		List<Route> routeList = db.getAllRoutes();
		for (Route r : routeList){
			routes = routes + r.getName() + "\n";
		}
		
		//Testing deleting routes
		db.deleteRoute(route1Id);
		
		//Testing adding formulars to db 
		List<Formular> formularList = db.getAllFormulars();
		for (Formular r : formularList){
			formulars = formulars + " " + r.getId() + r.getLatitude() + " " + r.getLongitude() + "\n";
		}
		
//		//Testing deleting formulars
//		db.deleteFormular(formular3Id);

//		//Testing fetching formulars by routeId
//		List<Formular> formularList = db.getFormularsByRouteId(route2Id);
//		for (Formular r : formularList){
//			formulars = formulars + r.getId() + " " + r.getLatitude() + " " + r.getLongitude() + "\n";
//		}
		
		txtView.setText(formulars);
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_view, menu);
		return true;
	}

}
