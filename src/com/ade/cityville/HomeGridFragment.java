package com.ade.cityville;

import java.util.ArrayList;

import com.ade.cityville.AppData.TrackerName;
import com.ade.cityville.Server.RServer;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;

public class HomeGridFragment extends Fragment implements OnClickListener, Filterable{
	private View vi;
	private ArrayList<CityEvent> filteredResults = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vi = inflater.inflate(R.layout.fragment_home_grid, container, false);
		AppData.updateCityEvents();
		if (AppData.getCityEventsList() != null || 
				AppData.getCityEventsList().size() > 0){
			
			if (filteredResults != null){
				generateView(filteredResults);
			}else{
				generateView(AppData.getCityEventsList());
			}
		}else{}
		
		
		// Get tracker.
        Tracker t =  AppData.getTracker(TrackerName.APP_TRACKER);
        
        t.setScreenName("Home Grid View Activity");
        
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
            .setCategory("Activity")
            .setAction("Loaded")
            .setLabel("Grid View Loaded")
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
	
	private void generateView(ArrayList<CityEvent> alCE) {
		int i=0;
		for (CityEvent ce: alCE){
			final CompositeView View = new CompositeView(getActivity());
			View.setId(i);
			View.setTitle(ce.getName());
			View.setDatenTime(ce.getDate() + " @ " + ce.getTime());
			View.setOnClickListener(this);
			
			final String image = ce.getImg(); final CityEvent tempce = ce;
	        //if (image.equalsIgnoreCase("") || image.equalsIgnoreCase(" ") || image == null){
	        	View.getIvIcon().setImageResource(AppData.getEventIcon(ce.getName().substring(0, 1).toLowerCase()));
	       /*}else{
	        	View.getIvIcon().setImageURI(Uri.parse(getString(R.string.image_server_address) + image));
	        	getActivity().runOnUiThread(new Runnable(){ //Cannot run http request on main thread

					@Override
					public void run() {
						Bitmap bm = RServer.getImg(getString(R.string.image_server_address) + image);
						if (bm != null){View.getIvIcon().setImageBitmap(bm);}
						else{View.getIvIcon().setImageResource(AppData.getEventIcon(tempce.getName().substring(0, 1).toLowerCase()));}
						
					}});
	        	
	        }*/
			
			View.setBackgroundResource(R.color.light_gray);
			View.getTvTitle().setPadding(5, 0, 0, 0);
			View.getTvDatenTime().setPadding(5, 0, 0, 0);
			
			final int id = i;
			final View tempvi = vi;
			final RelativeLayout mainLayout = (RelativeLayout) vi.findViewById(R.id.home_grid_layout);
			ViewTreeObserver vto = mainLayout.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					//final int oneUnitWidth = mainLayout.getMeasuredWidth() / 8;
					//final int oneUnitHeight = mainLayout.getMeasuredHeight() / 6;
					
					final int oneUnitWidth = tempvi.getWidth() / 8;
					final int oneUnitHeight = tempvi.getHeight() / 6;
					
					Log.i("Home Grid Layout", "Unit Width: " + oneUnitWidth);
					Log.i("Home Grid Layout", "Unit Height: " + oneUnitHeight);
					
					if (id==0){ //if it is the 1st view to be added
						final RelativeLayout.LayoutParams otelParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						otelParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						otelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						//otelParams.setMargins(0, 0, 6, 0);
						View.setLayoutParams(otelParams);
						
					}else if (id%10 == 0){ //if it is the second time the 1st view is to be added
						final RelativeLayout.LayoutParams otelParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						otelParams.addRule(RelativeLayout.BELOW, id-2);
						otelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						otelParams.setMargins(0, 2, 0, 2);
						View.setLayoutParams(otelParams);
						
					}else if (id == 1 || id%10 == 1){ //if it is the 2nd view to be added
						final RelativeLayout.LayoutParams otherParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						if (id == 1){
							otherParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
							otherParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						}else{otherParams.addRule(RelativeLayout.BELOW, id-2);
						otherParams.addRule(RelativeLayout.RIGHT_OF, id-1);}
						otherParams.setMargins(2, 0, 0, 0);
						View.setLayoutParams(otherParams);
						
					}else if (id == 2 || id%10 == 2){ //if it is the 3rd view to be added
						final RelativeLayout.LayoutParams wtdParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 5, oneUnitHeight);
						wtdParams.addRule(RelativeLayout.BELOW, id-1);
						//wtdParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						wtdParams.setMargins(0, 2, 2, 2);
						View.setLayoutParams(wtdParams);
						
					}else if (id == 3 || id%10 == 3){ //if it is the 4th view to be added
						final RelativeLayout.LayoutParams animationParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 3, oneUnitHeight);
						animationParams.addRule(RelativeLayout.BELOW, id-2);
						animationParams.addRule(RelativeLayout.RIGHT_OF, id-1);
						animationParams.setMargins(0, 2, 0, 2);

						View.setLayoutParams(animationParams);
						
					}else if (id == 4 || id%10 == 4){ //if it is the 5th view to be added
						final RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						tvParams.addRule(RelativeLayout.BELOW, id-2);
						View.setLayoutParams(tvParams);
						
					}else if (id == 5 || id%10 == 5){ //if it is the 6th view to be added
						final RelativeLayout.LayoutParams dealsParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						dealsParams.addRule(RelativeLayout.RIGHT_OF, id-1);
						dealsParams.addRule(RelativeLayout.BELOW, id-2);
						dealsParams.setMargins(2, 0, 0, 0);
						View.setLayoutParams(dealsParams);
						
					}else if (id == 6 || id%10 == 6){ //if it is the 7th view to be added
						final RelativeLayout.LayoutParams weatherParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						weatherParams.addRule(RelativeLayout.BELOW, id-2);
						weatherParams.setMargins(0, 2, 0, 2);
						View.setLayoutParams(weatherParams);
						
					}else if (id == 7 || id%10 == 7){ //if it is the 8th view to be added
						final RelativeLayout.LayoutParams alacarteParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight * 2);
						alacarteParams.addRule(RelativeLayout.RIGHT_OF, id-1);
						alacarteParams.addRule(RelativeLayout.BELOW, id-2);
						alacarteParams.setMargins(2, 2, 0, 2);
						View.setLayoutParams(alacarteParams);
						
					}else if (id == 8 || id%10 == 8){ //if it is the 9th view to be added
						final RelativeLayout.LayoutParams wteParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight * 2);
						wteParams.addRule(RelativeLayout.BELOW, id-2);
						View.setLayoutParams(wteParams);
						
					}else if (id == 9 || id%10 == 9){ //if it is the 10th view to be added
						final RelativeLayout.LayoutParams myInfoParams = new RelativeLayout.LayoutParams(
								oneUnitWidth * 4, oneUnitHeight);
						myInfoParams.addRule(RelativeLayout.BELOW, id-2);
						myInfoParams.addRule(RelativeLayout.RIGHT_OF, id-1);
						myInfoParams.setMargins(2, 0, 0, 2);

						View.setLayoutParams(myInfoParams);
					}
					
					ViewTreeObserver obs = mainLayout.getViewTreeObserver();
					obs.removeGlobalOnLayoutListener(this);
				}
			});
			mainLayout.addView(View);
			
			i++;
		}
	}


	@Override
	public void onClick(View v) {
		toCityEventActivity(v.getId());
	}
	
	protected void toCityEventActivity(int position) {
		SoundManager.playSound(6, 1);
		Intent intent = new Intent(getActivity(), CityEventActivity.class);
		Bundle b = new Bundle();
		b.putInt("id", position); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		startActivity(intent);
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
	                	ArrayList<CityEvent> checkedEvents = new ArrayList<CityEvent>();
	                	for (CityEvent ce: AppData.getCityEventsList()){
		                	if (AppData.checkFilters(ce) && AppData.checkAgeRestriction(ce)){
		                		checkedEvents.add(ce);
		                	}
	                	}
	                	
	                	//results.values = AppData.getCityEventsList();
	                    //results.count = AppData.getCityEventsList().size();
	                	results.values = checkedEvents;
                    	results.count = checkedEvents.size();
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
	                        	if (AppData.checkFilters(ce) && AppData.checkAgeRestriction(ce)){
	                        		filterResultsData.add(ce);
	                        	}
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
	                //TODO Update view here, by clearing the view before redrawing it.
	            	if (((ArrayList<CityEvent>) filterResults.values).size() <= 0){
	            		filteredResults = new ArrayList<CityEvent>();
	            		HomeActivity.getHomeGridFragment().getView().postInvalidate();
	            		//HomeActivity.getHomeGridFragment().onCreate(null);
	            		//HomeActivity.getHomeGridFragment().getView().invalidate();
	            	}else{
	            		filteredResults = (ArrayList<CityEvent>) filterResults.values;
	            		HomeActivity.getHomeGridFragment().getView().postInvalidate();
	            		//HomeActivity.getHomeGridFragment().onCreate(null);
		            	//HomeActivity.getHomeGridFragment().getView().invalidate();
	            	}
	            	
	            	
	            	//HomeActivity.getHomeGridFragment().onCreate(null);
	            	//generateView((ArrayList<CityEvent>) filterResults.values);
	            }
	        };
	}
}
