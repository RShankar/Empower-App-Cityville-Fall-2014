package com.example.settings;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.content.Intent;

public class EventFilterActivity extends ActionBarActivity {
	
	
	private CheckBox educational;
	private CheckBox entertainment;
	private CheckBox family;
	private CheckBox food;
	private CheckBox music;
	private CheckBox sports;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        
        educational = (CheckBox)findViewById(R.id.educational);
        entertainment = (CheckBox)findViewById(R.id.entertainment);
        family = (CheckBox)findViewById(R.id.family);
        food = (CheckBox)findViewById(R.id.food);
        music = (CheckBox)findViewById(R.id.music);
        sports = (CheckBox)findViewById(R.id.sports);
     
        
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
	
    
    public void onClickEducational(){
    	
    
    	
    	
    	
    }
    
    public void onClickEntertainment(){
    	
    	
    	
    	
    	
    }
    
    public void onClickFamily(){
    	
    	
    	
    	
    	
    }
    
    public void onClickFood(){
    	
    	
    	
    	
    	
    }
    
    public void onClickMusic(){
    	
    	
    	
    	
    	
    }
    
    public void onClickSports(){
    	
    	
    	
    	
    
    }
}
