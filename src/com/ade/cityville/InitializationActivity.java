package com.ade.cityville;

import java.net.InetAddress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InitializationActivity extends Activity {
	private TextView tvProgress;
	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initialization);
		
		tvProgress = (TextView) findViewById(R.id.textViewProgress);
		pb = (ProgressBar) findViewById(R.id.progressBar);
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		final Context c = this;
		new Thread(new Runnable(){

			@Override
			public void run() {
			
				
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
			}}).start();
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
		new AlertDialog.Builder(this)
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
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	public void done(){
		pb.setProgress(pb.getMax());
		
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
