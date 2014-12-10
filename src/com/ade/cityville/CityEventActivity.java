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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class CityEventActivity extends Activity {
	GoogleMap map;
	CircleOptions radiusIndicator;
	Circle currentRadius;
	Geocoder geocoder;
	Marker selectedMarker;
	MarkerOptions currentMarker;
	private static MapFragment mapFrag;
	private List<Address> searchResults;
	
	
	private CityEvent aCityEvent;
	private int eventPostion;
	private TabHost thMain;
	private TextView eventName, eventDate, eventAge, eventAddress, eventTime, eventPhone, eventCost, eventDescription;
	private ImageView ivIcon;
	private RatingBar rBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_event);
		
		geocoder = new Geocoder(this);
		
		ivIcon = (ImageView) findViewById(R.id.imageViewCEIcon);
		eventName 			= (TextView) findViewById(R.id.eventName);
		eventDate 			= (TextView) findViewById(R.id.eventDate);
		eventAge 			= (TextView) findViewById(R.id.eventAge);
		eventAddress 		= (TextView) findViewById(R.id.eventAddress);
		eventTime 			= (TextView) findViewById(R.id.eventTime);
		eventPhone 			= (TextView) findViewById(R.id.eventPNumber);
		eventCost 			= (TextView) findViewById(R.id.eventCost);
		eventDescription 	= (TextView) findViewById(R.id.eventDescription);
		rBar = (RatingBar) findViewById(R.id.ratingBarEvent);
		
		Bundle b = getIntent().getExtras();
		int index = b.getInt("id");
		aCityEvent = AppData.getCityEventsList().get(index);
		eventName.setText(aCityEvent.getName());
		if (aCityEvent.getAge() <= 0){
			eventAge.setText("Everyone");
		}else{
			eventAge.setText(aCityEvent.getAge()+"");
		}
		eventDate.setText(aCityEvent.getDate());
		eventAddress.setText(aCityEvent.getAddress());
		eventTime.setText(aCityEvent.getTime());
		eventPhone.setText(aCityEvent.getPhonenumber());
		if (aCityEvent.getCost() <= 0){
			eventCost.setText("Free");
		}else{
			eventCost.setText("$"+aCityEvent.getCost());
		}
		eventDescription.setText(aCityEvent.getDescription());
		
		String image = aCityEvent.getImg();
		ivIcon.setImageResource(AppData.getEventIcon(aCityEvent.getName().substring(0, 1).toLowerCase()));
		
		rBar.setRating((float) aCityEvent.getRating());
		rBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				ratingBar.setEnabled(false);
				if (UpdateRating(rating)){
					Toast.makeText(AppData.getContext(), "Thank you for rating!", Toast.LENGTH_SHORT).show();
				}
				
			}});
		
		/*rBar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				rBar.setEnabled(false);
				UpdateRating(rBar.getRating());
			}});*/
		//ivIcon.setImageURI(Uri.parse(getString(R.string.image_server_address) + image));
        
		//eventPostion = mIntent.getIntExtra("CITY_EVENT_POSITION", 0);
		/*
		if (aCityEvent != null){
			String image = aCityEvent.getImg();
			if (image.equalsIgnoreCase("") || image.equalsIgnoreCase(" ") || image == null){
				ivIcon.setImageResource(AppData.getEventIcon(aCityEvent.getName().substring(0, 1).toLowerCase()));
	        }else{
	        	ivIcon.setImageURI(Uri.parse(getString(R.string.image_server_address) + image));
	        }
			tvName.setText(aCityEvent.getName());
			tvCost.setText("Cost: "+ aCityEvent.getCost());
			tvAge.setText("Age: "+ aCityEvent.getAge() + "+");
		}
		*/
	}
	
	protected boolean UpdateRating(float rating) {
		
		final float newRating = rating + (float)aCityEvent.getRating() /2;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("CityEvent");
		 
		query.getInBackground(aCityEvent.getId(), new GetCallback<ParseObject>() {

		@Override
		public void done(ParseObject object, ParseException e) {
			if (e == null) {
			      object.put("rating", newRating);
			      try {
					object.save();
				} catch (ParseException e1) {
					Log.e("Parse - Update Rating", e1.toString());
				}
			}
		}
		});
		return true;
	}

	@Override
	protected void onStart(){
		super.onStart();
		
		// Get a handle to the Map Fragment
	       mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapEvent));
	       map = mapFrag.getMap();
	       map.setMyLocationEnabled(true);
	 
	       /**/
	       
	       if (aCityEvent.getLocation() != null){
	    	   if (!aCityEvent.getLocation().getProvider().contains("error")){
	    		   LatLng current = new LatLng(aCityEvent.getLocation().getLatitude(), aCityEvent.getLocation().getLongitude());
	    		   map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
	    	   }
	       }else if (AppData.getCurrentLocation() != null){
		       LatLng current = new LatLng(AppData.getCurrentLocation().getLatitude(), AppData.getCurrentLocation().getLongitude());
		       map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
		   }
	       
	       map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener(){

			@Override
			public boolean onMyLocationButtonClick() {
				
				return false;
			}});
	       
	       /*map.setOnMapLongClickListener(new OnMapLongClickListener(){

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
		  					//etAddress.setText(searchResults.get(0).getAddressLine(0)+ ", " + searchResults.get(0).getAddressLine(1) + ", " + searchResults.get(0).getAddressLine(2));
		  					address = searchResults.get(0).getAddressLine(0)+ ", " + searchResults.get(0).getAddressLine(1) + ", " + searchResults.get(0).getAddressLine(2);
		  					//etGeofenceName.setText(searchResults.get(0).getFeatureName());
		  				}else{
		  					//etAddress.setText("Latitude: "+ point.latitude + "," + "Longitude: "+ point.longitude);
		  					address = "Latitude: "+ point.latitude + "," + "Longitude: "+ point.longitude;
		  				}
		  	        }
		          }
				
				
				currentMarker = new MarkerOptions()
		        .title("Selected Location")
		        .snippet(address)
		        .position(point);
		        selectedMarker = map.addMarker(currentMarker);
				
			}});*/
	       
	       map.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker arg0) {
				selectedMarker = arg0;
				return true;
			}});
	       
	       loadMapEvent(aCityEvent);
	}

	private void loadMapEvent(CityEvent ce) {
		String cost = "";
		if (ce.getCost() <= 0){
			cost = "Free";
		}else{
			cost = "$" + ce.getCost();
		}
		if (ce.getLocation() != null){
			if (!ce.getLocation().getProvider().equalsIgnoreCase("error")){
				Marker mark = map.addMarker(new MarkerOptions()
				.title(ce.getName())
				.snippet("" + ce.getDate() + " @ " + ce.getTime() + ", Cost: "+ cost)
				.position(new LatLng(ce.getLocation().getLatitude(),ce.getLocation().getLongitude())
				));	
			}
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_event, menu);
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
}
