package com.ade.cityville;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

public class HomeMapFragment extends Fragment implements Filterable{
	GoogleMap map;
	CircleOptions radiusIndicator;
	Circle currentRadius;
	Geocoder geocoder;
	Marker selectedMarker;
	MarkerOptions currentMarker;
	private static MapFragment mapFrag;
	
	 // decimal formats for latitude, longitude, and radius
    private DecimalFormat mLatLngFormat;
    private DecimalFormat mRadiusFormat;
	
	private static final double EARTH_RADIUS = 6378100.0;

	private View vi;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (vi == null){}
		vi = inflater.inflate(R.layout.fragment_home_map, container, false);
		
		radiusIndicator = new CircleOptions();
		
		// Set the format for latitude and longitude
        mLatLngFormat = new DecimalFormat(getString(R.string.lat_lng_pattern));

        // Localize the format
        mLatLngFormat.applyLocalizedPattern(mLatLngFormat.toLocalizedPattern());
        
        // Set the format for the radius
        mRadiusFormat = new DecimalFormat(getString(R.string.radius_pattern));

        // Localize the pattern
        mRadiusFormat.applyLocalizedPattern(mRadiusFormat.toLocalizedPattern());
        
       
		return vi;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
       // Get a handle to the Map Fragment
       mapFrag = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapHome));
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
       
       map.setOnMapClickListener(new OnMapClickListener(){

		@Override
		public void onMapClick(LatLng arg0) {
			
			
		}});
       
       map.setOnMarkerClickListener(new OnMarkerClickListener(){

		@Override
		public boolean onMarkerClick(Marker arg0) {
			Intent intent = new Intent(getActivity(), CityEventActivity.class);
			intent.putExtra("THE-CITY-EVENT", findCityEvent(arg0.getTitle()));
			startActivity(intent);
			return false;
		}});
       
       loadCityEvents(AppData.getCityEventsList());
       loadReportedAreas();
       
	}

	

	private void loadCityEvents(ArrayList<CityEvent> alCE) {
	
		if (alCE != null && alCE.size() > 0 )
		{
			for (CityEvent ce: alCE){
				if (ce.getLocation() != null){
					if (!ce.getLocation().getProvider().equalsIgnoreCase("error")){
						Marker mark = map.addMarker(new MarkerOptions()
						.title(ce.getName())
						.snippet("Cost: $"+ce.getCost())
						.position(new LatLng(ce.getLocation().getLatitude(),ce.getLocation().getLongitude())
						));	
					}
				}
			}
		}
	}
	
	private void loadReportedAreas() {
		
		if (AppData.getReportsList() != null && AppData.getReportsList().size() > 0){
			int shadeColor = 0x880000FF;
			int strokeColor = 0xFF0000FF;
			
			for (ReportedArea ra: AppData.getReportsList()){
				if (ra.getType().equalsIgnoreCase("police")){
					shadeColor = 0x880000FF;
					strokeColor = 0xFF0000FF;
				}else if (ra.getType().equalsIgnoreCase("fire")){
					shadeColor = 0x88FF3333;
					strokeColor = 0xFFFF3333;
				}else if (ra.getType().equalsIgnoreCase("traffic")){
					shadeColor = 0x88FF9933;
					strokeColor = 0xFFFF6600;
				}
				
				if (!ra.getLocation().getProvider().equals("error")){
					radiusIndicator = new CircleOptions()
					.radius(ra.getRadius())
					.center(new LatLng(ra.getLocation().getLatitude(),ra.getLocation().getLongitude()))
					.fillColor(shadeColor)
					.strokeColor(strokeColor)
					.strokeWidth(8);
					
					map.addCircle(radiusIndicator);
				}
			}
		}
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
	
	public CityEvent findCityEvent(String title){
		for (CityEvent ce: AppData.getCityEventsList()){
			if (ce.getName().equals(title)){
				return ce;
			}
		}
		
		return null;
	}

	@Override
	public Filter getFilter() {
		
		return new Filter()
	       {
	            @Override
	            protected FilterResults performFiltering(CharSequence charSequence)
	            {
	                FilterResults results = new FilterResults();

	                //If there's nothing to filter on, return the original data for your list
	                if(charSequence == null || charSequence.length() == 0 || charSequence.equals(""))
	                {
	                    results.values = AppData.getCityEventsList();
	                    results.count = AppData.getCityEventsList().size();
	                }
	                else
	                {
	                	ArrayList<CityEvent> filterResultsData = new ArrayList<CityEvent>();

	                    for(CityEvent ce : AppData.getCityEventsList())
	                    {
	                        //In this loop, you'll filter through originalData and compare each item to charSequence.
	                        //If you find a match, add it to your new ArrayList
	                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
	                        if(ce.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
	                        {
	                            filterResultsData.add(ce);
	                        }
	                    }            

	                    results.values = filterResultsData;
	                    results.count = filterResultsData.size();
	                }

	                return results;
	            }

	            @Override
	            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
	            {
	                map.clear();
	                loadCityEvents((ArrayList<CityEvent>) filterResults.values);
	                loadReportedAreas();
	            }
	        };
	}
}
