package org.agh.wastemanagementapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DatabaseViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_view, menu);
		return true;
	}

}
