/**
 * 
 */
package com.ade.cityville.adapters;

import java.util.ArrayList;
import java.util.Random;

import com.ade.cityville.*;
import com.ade.cityville.Server.RServer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author alainedwards
 *
 */
public class HomeEventListAdapter extends ArrayAdapter implements Filterable{

	private Context c;
	private ArrayList<CityEvent> events, filteredEvents;
	
	public HomeEventListAdapter(Context acontext, ArrayList<CityEvent> aevents){
		super(acontext, R.layout.eventlistrow, aevents);
		
		c = acontext;
		events = aevents;
		filteredEvents = aevents;
	}
	
	@Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.eventlistrow, parent, false);
        TextView tv_EventName = (TextView) vi.findViewById(R.id.textViewEventName);
        TextView tv_EventTime = (TextView) vi.findViewById(R.id.textViewEventDateNTime);
        final ImageView iv_EventIcon = (ImageView) vi.findViewById(R.id.imageViewEventIcon);
        
        tv_EventName.setText(events.get(position).getName());
        tv_EventTime.setText(events.get(position).getDate() + " @ " + events.get(position).getTime());
        
        final String image = events.get(position).getImg(); final CityEvent tempce = events.get(position);
        if ( image == null){
        	iv_EventIcon.setImageResource(getEventIcon(events.get(position).getName().substring(0, 1).toLowerCase()));
        }else if (image.equalsIgnoreCase("") || image.equalsIgnoreCase(" ")){
        	iv_EventIcon.setImageResource(getEventIcon(events.get(position).getName().substring(0, 1).toLowerCase()));
        }
        else{
        	//iv_EventIcon.setImageURI(Uri.parse(c.getString(R.string.image_server_address) + image));
        	/*new Thread(new Runnable(){//Cannot run http request on main thread

				@Override
				public void run() {
					Bitmap bm = RServer.getImg(c.getString(R.string.image_server_address) + image);
					if (bm !=null){
					iv_EventIcon.setImageBitmap(bm);}
					else{iv_EventIcon.setImageResource(getEventIcon(tempce.getName().substring(0, 1).toLowerCase()));}
				}});*/
        }
        
        
        return vi;
    }
    
    public ArrayList<CityEvent> getCurrentCEvents(){
    	return events;
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
	                	for (CityEvent ce: filteredEvents){
		                	if (AppData.checkFilters(ce) && AppData.checkAgeRestriction(ce)){
		                		checkedEvents.add(ce);
		                	}
	                	}
	                	
	                	//results.values = filteredEvents;
                    	//results.count = filteredEvents.size();
	                	results.values = checkedEvents;
                    	results.count = checkedEvents.size();
	                	
	                }
	                else
	                {
	                	ArrayList<CityEvent> filterResultsData = new ArrayList<CityEvent>();

	                    for(CityEvent ce : events)
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
	                //TODO Update view here
	            	events = (ArrayList<CityEvent>) filterResults.values;
	            	notifyDataSetChanged();
	            }
	        };
	}
	private int getEventIcon(String letter) {
		Random rand = new Random();
		int randNum = rand.nextInt(8);
		switch(letter){
			case "a":
					switch (randNum){
						case 0: return R.drawable.a0;
						case 1: return R.drawable.a1;
							
						case 2: return R.drawable.a2;
							
						case 3: return R.drawable.a3;
							
						case 4: return R.drawable.a4;
							
						case 5: return R.drawable.a5;
							
						case 6: return R.drawable.a6;
							
						case 7: return R.drawable.a7;
							
						default: return R.drawable.a0;
							
					}
			case "b":
				switch (randNum){
				case 0: return R.drawable.b0;
					
				case 1: return R.drawable.b1;
					
				case 2: return R.drawable.b2;
					
				case 3: return R.drawable.b3;
					
				case 4: return R.drawable.b4;
					
				case 5: return R.drawable.b5;
					
				case 6: return R.drawable.b6;
					
				case 7: return R.drawable.b7;
					
				default: return R.drawable.b0;
					
			}
				
			case "c":
				switch (randNum){
				case 0: return R.drawable.c0;
					
				case 1: return R.drawable.c1;
					
				case 2: return R.drawable.c2;
					
				case 3: return R.drawable.c3;
					
				case 4: return R.drawable.c4;
					
				case 5: return R.drawable.c5;
					
				case 6: return R.drawable.c6;
					
				case 7: return R.drawable.c7;
					
				default: return R.drawable.c0;
					
			}
				
			case "d":
				switch (randNum){
				case 0: return R.drawable.d0;
					
				case 1: return R.drawable.d1;
					
				case 2: return R.drawable.d2;
					
				case 3: return R.drawable.d3;
					
				case 4: return R.drawable.d4;
					
				case 5: return R.drawable.d5;
					
				case 6: return R.drawable.d6;
					
				case 7: return R.drawable.d7;
					
				default: return R.drawable.d0;
					
			}
				
			case "e":
				switch (randNum){
				case 0: return R.drawable.e0;
					
				case 1: return R.drawable.e1;
					
				case 2: return R.drawable.e2;
					
				case 3: return R.drawable.e3;
					
				case 4: return R.drawable.e4;
					
				case 5: return R.drawable.e5;
					
				case 6: return R.drawable.e6;
					
				case 7: return R.drawable.e7;
					
				default: return R.drawable.e0;
					
			}
				
			case "f":
				switch (randNum){
				case 0: return R.drawable.f0;
					
				case 1: return R.drawable.f1;
					
				case 2: return R.drawable.f2;
					
				case 3: return R.drawable.f3;
					
				case 4: return R.drawable.f4;
					
				case 5: return R.drawable.f5;
					
				case 6: return R.drawable.f6;
					
				case 7: return R.drawable.f7;
					
				default: return R.drawable.f0;
					
			}
				
			case "g":
				switch (randNum){
				case 0: return R.drawable.g0;
					
				case 1: return R.drawable.g1;
					
				case 2: return R.drawable.g2;
					
				case 3: return R.drawable.g3;
					
				case 4: return R.drawable.g4;
					
				case 5: return R.drawable.g5;
					
				case 6: return R.drawable.g6;
					
				case 7: return R.drawable.g7;
					
				default: return R.drawable.g0;
					
			}
				
			case "h":
				switch (randNum){
				case 0: return R.drawable.h0;
					
				case 1: return R.drawable.h1;
					
				case 2: return R.drawable.h2;
					
				case 3: return R.drawable.h3;
					
				case 4: return R.drawable.h4;
					
				case 5: return R.drawable.h5;
					
				case 6: return R.drawable.h6;
					
				case 7: return R.drawable.h7;
					
				default: return R.drawable.h0;
					
			}
				
			case "i":
				switch (randNum){
				case 0: return R.drawable.i0;
					
				case 1: return R.drawable.i1;
					
				case 2: return R.drawable.i2;
					
				case 3: return R.drawable.i3;
					
				case 4: return R.drawable.i4;
					
				case 5: return R.drawable.i5;
					
				case 6: return R.drawable.i6;
					
				case 7: return R.drawable.i7;
					
				default: return R.drawable.i0;
					
			}
				
			case "j":
				switch (randNum){
				case 0: return R.drawable.j0;
					
				case 1: return R.drawable.j1;
					
				case 2: return R.drawable.j2;
					
				case 3: return R.drawable.j3;
					
				case 4: return R.drawable.j4;
					
				case 5: return R.drawable.j5;
					
				case 6: return R.drawable.j6;
					
				case 7: return R.drawable.j7;
					
				default: return R.drawable.j0;
					
			}
				
			case "k":
				switch (randNum){
				case 0: return R.drawable.k0;
					
				case 1: return R.drawable.k1;
					
				case 2: return R.drawable.k2;
					
				case 3: return R.drawable.k3;
					
				case 4: return R.drawable.k4;
					
				case 5: return R.drawable.k5;
					
				case 6: return R.drawable.k6;
					
				case 7: return R.drawable.k7;
					
				default: return R.drawable.k0;
					
			}
				
			case "l":
				switch (randNum){
				case 0: return R.drawable.l0;
					
				case 1: return R.drawable.l1;
					
				case 2: return R.drawable.l2;
					
				case 3: return R.drawable.l3;
					
				case 4: return R.drawable.l4;
					
				case 5: return R.drawable.l5;
					
				case 6: return R.drawable.l6;
					
				case 7: return R.drawable.l7;
					
				default: return R.drawable.l0;
					
			}
				
			case "m":
				switch (randNum){
				case 0: return R.drawable.m0;
					
				case 1: return R.drawable.m1;
					
				case 2: return R.drawable.m2;
					
				case 3: return R.drawable.m3;
					
				case 4: return R.drawable.m4;
					
				case 5: return R.drawable.m5;
					
				case 6: return R.drawable.m6;
					
				case 7: return R.drawable.m7;
					
				default: return R.drawable.m0;
					
			}
				
			case "n":
				switch (randNum){
				case 0: return R.drawable.n0;
					
				case 1: return R.drawable.n1;
					
				case 2: return R.drawable.n2;
					
				case 3: return R.drawable.n3;
					
				case 4: return R.drawable.n4;
					
				case 5: return R.drawable.n5;
					
				case 6: return R.drawable.n6;
					
				case 7: return R.drawable.n7;
					
				default: return R.drawable.n0;
					
			}
				
			case "o":
				switch (randNum){
				case 0: return R.drawable.o0;
					
				case 1: return R.drawable.o1;
					
				case 2: return R.drawable.o2;
					
				case 3: return R.drawable.o3;
					
				case 4: return R.drawable.o4;
					
				case 5: return R.drawable.o5;
					
				case 6: return R.drawable.o6;
					
				case 7: return R.drawable.o7;
					
				default: return R.drawable.o0;
					
			}
				
			case "p":
				switch (randNum){
				case 0: return R.drawable.p0;
					
				case 1: return R.drawable.p1;
					
				case 2: return R.drawable.p2;
					
				case 3: return R.drawable.p3;
					
				case 4: return R.drawable.p4;
					
				case 5: return R.drawable.p5;
					
				case 6: return R.drawable.p6;
					
				case 7: return R.drawable.p7;
					
				default: return R.drawable.p0;
					
			}
				
			case "q":
				switch (randNum){
				case 0: return R.drawable.q0;
					
				case 1: return R.drawable.q1;
					
				case 2: return R.drawable.q2;
					
				case 3: return R.drawable.q3;
					
				case 4: return R.drawable.q4;
					
				case 5: return R.drawable.q5;
					
				case 6: return R.drawable.q6;
					
				case 7: return R.drawable.q7;
					
				default: return R.drawable.a0;
					
			}
				
			case "r":
				switch (randNum){
				case 0: return R.drawable.r0;
					
				case 1: return R.drawable.r1;
					
				case 2: return R.drawable.r2;
					
				case 3: return R.drawable.r3;
					
				case 4: return R.drawable.r4;
					
				case 5: return R.drawable.r5;
					
				case 6: return R.drawable.r6;
					
				case 7: return R.drawable.r7;
					
				default: return R.drawable.r0;
					
			}
				
			case "s":
				switch (randNum){
				case 0: return R.drawable.s0;
					
				case 1: return R.drawable.s1;
					
				case 2: return R.drawable.s2;
					
				case 3: return R.drawable.s3;
					
				case 4: return R.drawable.s4;
					
				case 5: return R.drawable.s5;
					
				case 6: return R.drawable.s6;
					
				case 7: return R.drawable.s7;
					
				default: return R.drawable.s0;
					
			}
				
			case "t":
				switch (randNum){
				case 0: return R.drawable.t0;
					
				case 1: return R.drawable.t1;
					
				case 2: return R.drawable.t2;
					
				case 3: return R.drawable.t3;
					
				case 4: return R.drawable.t4;
					
				case 5: return R.drawable.t5;
					
				case 6: return R.drawable.t6;
					
				case 7: return R.drawable.t7;
					
				default: return R.drawable.t0;
					
			}
				
			case "u":
				switch (randNum){
				case 0: return R.drawable.u0;
					
				case 1: return R.drawable.u1;
					
				case 2: return R.drawable.u2;
					
				case 3: return R.drawable.u3;
					
				case 4: return R.drawable.u4;
					
				case 5: return R.drawable.u5;
					
				case 6: return R.drawable.u6;
					
				case 7: return R.drawable.u7;
					
				default: return R.drawable.u0;
					
			}
				
			case "v":
				switch (randNum){
				case 0: return R.drawable.v0;
					
				case 1: return R.drawable.v1;
					
				case 2: return R.drawable.v2;
					
				case 3: return R.drawable.v3;
					
				case 4: return R.drawable.v4;
					
				case 5: return R.drawable.v5;
					
				case 6: return R.drawable.v6;
					
				case 7: return R.drawable.v7;
					
				default: return R.drawable.v0;
					
			}
				
			case "w":
				switch (randNum){
				case 0: return R.drawable.w0;
					
				case 1: return R.drawable.w1;
					
				case 2: return R.drawable.w2;
					
				case 3: return R.drawable.w3;
					
				case 4: return R.drawable.w4;
					
				case 5: return R.drawable.w5;
					
				case 6: return R.drawable.w6;
					
				case 7: return R.drawable.w7;
					
				default: return R.drawable.w0;
					
			}
				
			case "y":
				switch (randNum){
				case 0: return R.drawable.y0;
					
				case 1: return R.drawable.y1;
					
				case 2: return R.drawable.y2;
					
				case 3: return R.drawable.y3;
					
				case 4: return R.drawable.y4;
					
				case 5: return R.drawable.y5;
					
				case 6: return R.drawable.y6;
					
				case 7: return R.drawable.y7;
					
				default: return R.drawable.y0;
					
			}
				
			case "x":
				switch (randNum){
				case 0: return R.drawable.x0;
					
				case 1: return R.drawable.x1;
					
				case 2: return R.drawable.x2;
					
				case 3: return R.drawable.x3;
					
				case 4: return R.drawable.x4;
					
				case 5: return R.drawable.x5;
					
				case 6: return R.drawable.x6;
					
				case 7: return R.drawable.x7;
					
				default: return R.drawable.x0;
					
			}
				
			case "z":
				switch (randNum){
				case 0: return R.drawable.z0;
					
				case 1: return R.drawable.z1;
					
				case 2: return R.drawable.z2;
					
				case 3: return R.drawable.z3;
					
				case 4: return R.drawable.z4;
					
				case 5: return R.drawable.z5;
					
				case 6: return R.drawable.z6;
					
				case 7: return R.drawable.z7;
					
				default: return R.drawable.z0;
					
			}
			default: return R.drawable.ic_launcher;	
		}
	}
}
