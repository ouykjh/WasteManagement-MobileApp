package org.agh.wastemanagementapp;
 
import org.agh.connector.ApiConnector;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
 
public class SettingsActivity extends PreferenceActivity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.settings);    
    }
        
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case 0:
                    startActivity(new Intent(this, ApiConnector.class));
                    return true;
            }
            return false;
        }

}