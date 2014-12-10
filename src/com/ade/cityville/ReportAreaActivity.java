package com.ade.cityville;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ReportAreaActivity extends Activity {
	GoogleMap map;
	CircleOptions radiusIndicator;
	Circle currentRadius;
	Geocoder geocoder;
	Marker selectedMarker;
	MarkerOptions currentMarker;
	private static MapFragment mapFrag;
	
	private List<Address> searchResults;
	private Spinner spinTypes;
	private EditText etAddress, etRadius;
	private Button btnReport, btnRadius;
	private double radius;
	ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_area);
		
		geocoder = new Geocoder(this);
		
		spinTypes = (Spinner) findViewById(R.id.spinnerTypes);
		etAddress = (EditText) findViewById(R.id.editTextAddress);
		etRadius = (EditText) findViewById(R.id.editTextRadius);
		btnReport = (Button) findViewById(R.id.buttonReport);
		btnRadius =  (Button) findViewById(R.id.buttonRadius);
		
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  getResources().getStringArray(R.array.report_types)); 
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinTypes.setAdapter(spinnerArrayAdapter);
		spinTypes.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	//For future
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // For future use
		    }

		});
		
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
		
		etRadius.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				
				
			}});
		btnRadius.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SoundManager.playSound(6, 1);
				if (etRadius.getText().toString().equals("") || etRadius.getText().toString().equalsIgnoreCase(" ") || etRadius.getText() == null){
					radius = 0;
					setRadius();
				}else{
					radius = Double.parseDouble(etRadius.getText().toString());
					setRadius();
				}
			}});
		
		btnReport.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SoundManager.playSound(6, 1);
				if (validate() == true){
					if (postReport(v)){
						AppData.updateReportedAreas();
						done();
					}else {
						alertError("Unable to report this location, please try again.");
					}
				}
			}});
		
	}
	
	protected void done() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	protected boolean postReport(View v) {
		dialog = ProgressDialog.show(this, "","Processing Request, Please Wait...", true);
		ParseObject newReportedArea = new ParseObject("ReportedArea");
		newReportedArea.put("type", spinTypes.getSelectedItem().toString());
		newReportedArea.put("address", etAddress.getText().toString());
		newReportedArea.put("radius", Double.parseDouble(etRadius.getText().toString()));
		
		if (currentMarker != null){
			newReportedArea.put("latitude", ""+currentMarker.getPosition().latitude);
			newReportedArea.put("longitude", ""+currentMarker.getPosition().longitude);
		}
		
		//newReportedArea.saveInBackground();
		try {
			newReportedArea.save();
		} catch (ParseException e) {
			Log.e("Parse - Report Area", e.toString());
			dialog.dismiss();
			return false;
		}
		dialog.dismiss();
		Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	protected void onStart(){
		super.onStart();
		
		// Get a handle to the Map Fragment
	       mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapReport));
	       map = mapFrag.getMap();
	       map.setMyLocationEnabled(true);
	 
	       if (AppData.getCurrentLocation() != null){
	       LatLng current = new LatLng(AppData.getCurrentLocation().getLatitude(), AppData.getCurrentLocation().getLongitude());
	       map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 8));}
	       
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
	
	public void setRadius(){
		//currentMarker.icon(BitmapDescriptorFactory.fromBitmap(getBitmap()));
		if (!(etRadius.getText().toString().equals("")) && etRadius.getText().toString() != null){
			if (currentMarker != null){
				int shadeColor = 0x880000FF;
				int strokeColor = 0xFF0000FF;
				
				String type = spinTypes.getSelectedItem().toString();
				if (type.equalsIgnoreCase("police")){
					shadeColor = 0x880000FF;
					strokeColor = 0xFF0000FF;
				}else if (type.equalsIgnoreCase("fire")){
					shadeColor = 0x88FF3333;
					strokeColor = 0xFFFF3333;
				}else if (type.equalsIgnoreCase("traffic")){
					shadeColor = 0x88FF9933;
					strokeColor = 0xFFFF6600;
				}
				else if (type.equalsIgnoreCase("other")){
					shadeColor = 0x88DCDCDC;
					strokeColor = 0xFFA9A9A9;
				}
				
				/*if (radiusUnits.getSelectedItem().equals("ft")){
					radius = Integer.parseInt(etRadius.getText().toString()) / 3.28084;
				}
				else if (radiusUnits.getSelectedItem().equals("mi")){
					radius = Integer.parseInt(etRadius.getText().toString()) * 1609.34;
				}else{radius = Integer.parseInt(etRadius.getText().toString());}*/
				
				
				if (currentRadius != null){
					currentRadius.remove();
				}
				
				radiusIndicator = new CircleOptions()
				.radius(radius)
				.center(currentMarker.getPosition())
				.fillColor(shadeColor)
				.strokeColor(strokeColor)
				.strokeWidth(8);
				
				currentRadius = map.addCircle(radiusIndicator);
			}
			else{Toast.makeText(this, "You have not selected/set a marker", Toast.LENGTH_SHORT).show();}
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etAddress.getWindowToken(), 0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_area, menu);
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
	
	public boolean validate(){
		if(etRadius.getText().toString().equals("") || etRadius.getText().toString().equals(" ") || etRadius.getText().toString() == null
				|| etAddress.getText().toString().equals("") || etAddress.getText().toString().equals(" ") || etAddress.getText().toString() == null){
			
			Toast.makeText(this, "One or more of the fields are empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
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
}
