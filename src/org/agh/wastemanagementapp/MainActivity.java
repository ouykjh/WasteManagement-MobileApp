package org.agh.wastemanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.agh.map.managament.GlobalState;
import org.agh.map.managament.PointManagament;

public class MainActivity extends Activity {

    EditText etLogin;
    EditText etPassword;
    Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		initUIElements();
		initButtonsOnClickListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
			switch(item.getItemId()){
			case R.id.action_settings:
				openSettings();
				break;
			default:
				break;
			}
			return true;
			
	}
	private void initUIElements(){
		etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
	}

	private void initButtonsOnClickListeners(){
		OnClickListener onClickListener = new OnClickListener(){
			public void onClick(View v){
				switch (v.getId()){
				case R.id.btnLogin:
                    loginToApp();
					break;
				default:
					break;
				}
			}
		};
        btnLogin.setOnClickListener(onClickListener);
	}

    private void loginToApp(){
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        GlobalState.getInstance().setLogin(etLogin.getText().toString());
        GlobalState.getInstance().setPassword(etPassword.getText().toString());
        startActivity(intent);
        finish();
    }

    private void openSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

}