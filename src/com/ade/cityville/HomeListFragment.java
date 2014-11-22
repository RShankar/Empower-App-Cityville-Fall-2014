package com.ade.cityville;

import com.ade.cityville.AppData.TrackerName;
import com.ade.cityville.adapters.HomeEventListAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeListFragment extends Fragment {

	private HomeEventListAdapter listAdapter;
	private ListView lv_events;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_home_list, container, false);
		
		AppData.updateCityEvents();
		lv_events = (ListView) vi.findViewById(R.id.listViewEvents);
		
		//If the ArrayList of CityEvents has something in it.
		if (AppData.getCityEventsList().size() > 0){
			listAdapter = new HomeEventListAdapter(getActivity(), AppData.getCityEventsList());
			lv_events.setAdapter(listAdapter);
			
			lv_events.setOnItemClickListener(new OnItemClickListener(){
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (position < listAdapter.getCurrentCEvents().size()){
					toCityEventActivity(listAdapter.getCurrentCEvents().get(position));}
				}});
		}else{
			//If there are no city events, display some image and text.
		}
		
		// Get tracker.
        Tracker t =  AppData.getTracker(TrackerName.APP_TRACKER);
        
        t.setScreenName("Home List View Activity");
        
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
            .setCategory("Activity")
            .setAction("Loaded")
            .setLabel("List View Loaded")
            //.setValue(1)
            .build());
        
		return vi;
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
	
	protected void toCityEventActivity(CityEvent ce) {
		Intent intent = new Intent(getActivity(), CityEventActivity.class);
		//intent.putExtra("CITY_EVENT_Name", position);
		intent.putExtra("THE-CITY-EVENT", ce);
		startActivity(intent);
	}
	
	public void searchEvents(String text){
		HomeListFragment.this.listAdapter.getFilter().filter(text);
	}
}
