package com.ade.cityville;

import java.net.InetAddress;

import com.ade.cityville.listeners.*;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class InitializationActivity extends Activity {
	private TextView tvProgress;
	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initialization);
		
		tvProgress = (TextView) findViewById(R.id.textViewProgress);
		pb = (ProgressBar) findViewById(R.id.progressBar);
		
		//Checks if the GPS is enabled
		ContentResolver contentResolver = this.getBaseContext().getContentResolver();
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		boolean gpsStatus = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);  
		if (gpsStatus == false) {
			Toast.makeText(this, "Please enable your gps settings", Toast.LENGTH_LONG).show();
			Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
				startActivity(gpsOptionsIntent);
		}
		
		//Initialize periodic updates of location
		LocationListener locationListener = new MyLocationListener(this);
		lm.requestLocationUpdates(lm.GPS_PROVIDER, 1000*60*5, 6, locationListener);
		
		//Initialize Parse
		 Parse.initialize(this, "qOkwubtF8mugPpjcf1RgaE3MfvNlkGNT58AlP70q", "qr41Q00Te0PfJ1KOlfz29olypPaNdKDxOvXwXAk3");
		 checkParse();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		final Context c = this;
					
				//done();
		//Make sure everything is up and running
		tvProgress.setText("Initializing...");
		if (presystemsCheck()){
			tvProgress.setText("Initializing local vars...");
			//Initialize all local variables
			AppData.setContext(c);
			if (AppData.initializeData()){pb.setProgress((int) (pb.getMax() * 0.80));}
			else{displayError("Server Error", "We were unable to get a list of events from our servers, please try again later.");}
			
			//All checks are a GO, finish up
			done();
		}else{}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.initialization, menu);
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
	public void checkParse(){
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
	}
	
	public boolean presystemsCheck(){
		//Checks if the device is connected to a network
		tvProgress.setText("Checking Networks...");
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
			  displayError("Network Provider","Please turn on/enable a network connection type (i.e. Wifi or Cellular)");
		   return false;
		  } 
		  pb.setProgress((int) (pb.getMax() * 0.20));
		  
		  //Checks if the device is connected to our server
		  /*try {
	            InetAddress ipAddr = InetAddress.getByName(getString(R.string.server_address)); 
	            if (ipAddr.equals("")) {
	            	//The device is not connected to our servers/Internet
	            	displayError("Internet Connectivity","Please connect to the internet before proceeding");
	                return false;
	            }
	      }catch(Exception e){
	            Log.e("PresystemsCheck - Internet Connectivity", e.toString());	
	            displayError("Internet Connectivity","Please connect to the internet before proceeding");
	            return false;
	      }   */ 
		  pb.setProgress((int) (pb.getMax() * 0.40));
		  
		  //All system checks are OK
		  return true;
	}
	
	public void displayError(String title, String msg){
		final AlertDialog.Builder ad = new AlertDialog.Builder(this)
		.setTitle(title)
	    .setMessage(msg)
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	Intent startMain = new Intent(Intent.ACTION_MAIN);
	    		startMain.addCategory(Intent.CATEGORY_HOME);
	    		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		startActivity(startMain);
	        }
	     })
	    /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })*/
	    .setIcon(android.R.drawable.ic_dialog_alert);
		
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				ad.show();
			}});
	}
	
	public void done(){
		pb.setProgress(pb.getMax());
		
		//TODO Change to Login Activity when done.
		//Intent intent = new Intent(this, LoginActivity.class);
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
