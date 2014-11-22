package com.ade.cityville;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class PostEventActivity extends Activity {
	
	GoogleMap map;
	CircleOptions radiusIndicator;
	Circle currentRadius;
	Geocoder geocoder;
	Marker selectedMarker;
	MarkerOptions currentMarker;
	private static MapFragment mapFrag;
	
	private List<Address> searchResults;
	private EditText etName, etDate, etTime, etPNumber, 
	etCost, etAge, etAddress, etDescription;
	private Button btnPost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_event);
		
		geocoder = new Geocoder(this);
		
		etName = (EditText) findViewById(R.id.editTextName);
		etDate = (EditText) findViewById(R.id.editTextDate);
		etTime = (EditText) findViewById(R.id.editTextTime);
		etPNumber = (EditText) findViewById(R.id.editTextPNumber);
		etCost = (EditText) findViewById(R.id.editTextCost);
		etAge = (EditText) findViewById(R.id.editTextAge);
		etAddress = (EditText) findViewById(R.id.editTextAddress);
		etDescription = (EditText) findViewById(R.id.editTextDescription);
		
		etAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		            showOnMap();
		            return true;
		        }
				return false;
			}
		});
		
		btnPost = (Button) findViewById(R.id.buttonPost);
		btnPost.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (validate() == true){
					if (postEvent(v)){
						AppData.updateCityEvents();
						done();
					}else {
						alertError("Unable to create new event, please try again.");
					}
				}
			}});
		
		
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		
		// Get a handle to the Map Fragment
	       mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapPost));
	       map = mapFrag.getMap();
	       map.setMyLocationEnabled(true);
	 
	       if (AppData.getCurrentLocation() != null){
	       LatLng current = new LatLng(AppData.getCurrentLocation().getLatitude(), AppData.getCurrentLocation().getLongitude());
	       map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));}
	       
	       map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener(){

			@Override
			public boolean onMyLocationButtonClick() {
				
				return false;
			}});
	       
	       map.setOnMapLongClickListener(new OnMapLongClickListener(){

			@Override
			public void onMapLongClick(LatLng point) {
				map.clear();
				try {
					searchResults = geocoder.getFromLocation(point.latitude,point.longitude, 5);
				} catch (IOException e) {
		  			Log.e("Location Search", e.toString());
		  		}
				String address = "";
				
				if (searchResults != null){
		  			if(searchResults.size() > 0)
		  	        {
		  				if (searchResults.get(0).getAddressLine(0) != null){
		  					etAddress.setText(searchResults.get(0).getAddressLine(0)+ ", " + searchResults.get(0).getAddressLine(1) + ", " + searchResults.get(0).getAddressLine(2));
		  					address = searchResults.get(0).getAddressLine(0)+ ", " + searchResults.get(0).getAddressLine(1) + ", " + searchResults.get(0).getAddressLine(2);
		  					//etGeofenceName.setText(searchResults.get(0).getFeatureName());
		  				}else{
		  					etAddress.setText("Latitude: "+ point.latitude + "," + "Longitude: "+ point.longitude);
		  					address = "Latitude: "+ point.latitude + "," + "Longitude: "+ point.longitude;
		  				}
		  	        }
		          }
				
				
				currentMarker = new MarkerOptions()
		        .title("Selected Location")
		        .snippet(address)
		        .position(point);
		        selectedMarker = map.addMarker(currentMarker);
				
			}});
	       
	       map.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker arg0) {
				selectedMarker = arg0;
				return true;
			}});
	}

	protected void showOnMap() {
		Location loc = AppData.getLatLongFromAddress(etAddress.getText().toString());
		if (loc != null){
			if (!loc.getProvider().equalsIgnoreCase("error")){
				map.clear();
				currentMarker = new MarkerOptions()
		        .title("Selected Location")
		        .snippet(etAddress.getText().toString())
		        .position(new LatLng(loc.getLatitude(), loc.getLongitude()));
		        selectedMarker = map.addMarker(currentMarker);
		        
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 10));
			}
		}
	}

	protected void alertError(String string) {
		new AlertDialog.Builder(this)
	    .setTitle("Error")
	    .setMessage(string)
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        }
	     }).show();	
	}

	protected void done() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_event, menu);
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
	
	public boolean postEvent(View v){
		ParseObject newCityEvent = new ParseObject("CityEvent");
		newCityEvent.put("name", etName.getText().toString());
		newCityEvent.put("date", etDate.getText().toString());
		newCityEvent.put("time", etTime.getText().toString());
		newCityEvent.put("phonenumber", etPNumber.getText().toString());
		newCityEvent.put("cost", etCost.getText().toString());
		newCityEvent.put("age", etAge.getText().toString());
		newCityEvent.put("rating", 0);
		newCityEvent.put("address", etAddress.getText().toString());
		newCityEvent.put("description", etDescription.getText().toString());
		
		if (currentMarker != null){
			newCityEvent.put("latitude", currentMarker.getPosition().latitude);
			newCityEvent.put("longitude", currentMarker.getPosition().longitude);
		}
		newCityEvent.saveInBackground();
		
		Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
		return true;
	}
	
	public boolean validate(){
		if(etName.getText().toString().equals("") || etName.getText().toString().equals(" ") || etName.getText().toString() == null
				|| etDate.getText().toString().equals("") || etDate.getText().toString().equals(" ") || etDate.getText().toString() == null
				|| etTime.getText().toString().equals("") || etTime.getText().toString().equals(" ") || etTime.getText().toString() == null
				|| etPNumber.getText().toString().equals("") || etPNumber.getText().toString().equals(" ") || etPNumber.getText().toString() == null
				|| etCost.getText().toString().equals("") || etCost.getText().toString().equals(" ") || etCost.getText().toString() == null
				|| etAge.getText().toString().equals("") || etAge.getText().toString().equals(" ") || etAge.getText().toString() == null
				|| etAddress.getText().toString().equals("") || etAddress.getText().toString().equals(" ") || etAddress.getText().toString() == null
				|| etDescription.getText().toString().equals("") || etDescription.getText().toString().equals(" ") || etDescription.getText().toString() == null){
			
			Toast.makeText(this, "One or More of the fields are empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
