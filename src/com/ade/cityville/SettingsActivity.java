package com.example.settings;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.Activity;
import android.content.Intent;


public class SettingsActivity extends Activity {

	private Button themes;
	private Button filters;
	private Button restrictions;
	private CheckBox checkfilters;
	
	boolean filter;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        themes = (Button)findViewById(R.id.themes);
        filters = (Button)findViewById(R.id.filters);
        restrictions = (Button)findViewById(R.id.restrictions);
        checkfilters = (CheckBox)findViewById(R.id.checkfilters);
        
        
        checkfilters.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				AppData.setfiltersActivated(isChecked);
				
			}});
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void goToTheme(View v){                  //Launches ThemesActivity 
    	Intent intent = new Intent(SettingsActivity.this, ThemesActivity.class);
    	startActivity(intent);
    
    }
    
    public void goToFilters(View v){                //Launches EventFilterActivity
    	Intent intent = new Intent(SettingsActivity.this,EventFilterActivity.class);
    	startActivity(intent);
    	
    }
    
    public void goToRestrictions(View v){			//Launches AgeRestrictionsActivity
    	Intent intent = new Intent(SettingsActivity.this,AgeRestrictionsActivity.class);
    	startActivity(intent);
    	
    }
    
    public void checkFilter(View v){                
    	
    	
    	
    }
    
    
}
