package com.ade.cityville;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.ade.cityville.Server.RServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AppData {

	private static ArrayList<CityEvent> CityEventsList = new ArrayList<CityEvent>();
	private static ArrayList<ReportedArea> ReportsList = new ArrayList<ReportedArea>();
	private static Location currentLocation;
	private static Context c;
	public static boolean loggedIn = false;
	private static boolean filtersActivated;

	public static boolean initializeData(){
		if (c == null){return false;}
		//Set all the class context here
		RServer.setContext(c);
		
		
		try {
			CityEventsList = RServer.getAllEvents();
		} catch (Exception e) {
			Log.e("Initilize City Events List", e.toString());
		}
		
		try{
			ReportsList = RServer.getAllReportedAreas();
		} catch (Exception e) {
			Log.e("Initilize Reported Areas List", e.toString());
		}
		
		try{
		//TODO Remove on release
		/*CityEventsList.add(new CityEvent("0","11/14/2014","12:00","Cleanup the park", "901 NW 178th Ave Pembroke Pines, FL 33029","954 205-1872", 0,5, 13, "","fun","1.png" ));
		CityEventsList.add(new CityEvent("1","11/15/2014","1:00","Rose G Price Park", "901 NW 208th Ave , Hollywood, FL 33029","954 205-1872", 0,5, 13, "","fun","" ));
		CityEventsList.add(new CityEvent("2","11/16/2014","2:00","Frontier Trails Park", "Southwest Ranches, FL 33332","954 205-1872", 10,5, 0, "","fun","" ));
		CityEventsList.add(new CityEvent("3","11/17/2014","3:00","HAPPI Farm", "17800 SW 52nd Ct Southwest Ranches, FL 33331","(954) 629-8133", 1,4, 10, "","fun","" ));
		CityEventsList.add(new CityEvent("4","11/18/2014","4:00","Naacp", "13230 NW 7th Ave North Miami, FL 33168","(305) 685-8694", 8,5, 18, "","fun","" ));
		//FAU Related
		CityEventsList.add(new CityEvent("5","12/12/2014","2:00","Engineering Graduation", "777 Glades Rd, Boca Raton, FL 33431","(561) 297-3000", 0,5, 13, "","Education","" ));
		CityEventsList.add(new CityEvent("6","11/27/2014","1:00","Cinemark Palace 20 Movie Night", "3200 Airport Rd Boca Raton, FL 33431","(561) 395-4695", 0,5, 13, "","Enetertainment,fun","" ));
		CityEventsList.add(new CityEvent("7","11/31/2014","2:00","University Woodlands Park", "University Woodlands Park, Boca Raton, FL 33434","954 205-1872", 10,5, 0, "","fun","" ));
		CityEventsList.add(new CityEvent("8","11/17/2014","3:00","HAPPI Farm", "17800 SW 52nd Ct Southwest Ranches, FL 33331","(954) 629-8133", 1,4, 10, "","fun","" ));
		CityEventsList.add(new CityEvent("9","11/18/2014","4:00","Naacp", "13230 NW 7th Ave North Miami, FL 33168","(305) 685-8694", 8,5, 18, "","fun","" ));
		
		//Length Test
		CityEventsList.add(new CityEvent("10","12/12/2014","2:00","Engineering Graduation", "777 Glades Rd, Boca Raton, FL 33431","(561) 297-3000", 0,5, 13, "","Education","" ));
		CityEventsList.add(new CityEvent("11","11/27/2014","1:00","Cinemark Palace 20 Movie Night", "3200 Airport Rd Boca Raton, FL 33431","(561) 395-4695", 0,5, 13, "","Enetertainment,fun","" ));
		CityEventsList.add(new CityEvent("12","11/31/2014","2:00","University Woodlands Park", "University Woodlands Park, Boca Raton, FL 33434","954 205-1872", 10,5, 0, "","fun","" ));
		CityEventsList.add(new CityEvent("13","11/17/2014","3:00","HAPPI Farm", "17800 SW 52nd Ct Southwest Ranches, FL 33331","(954) 629-8133", 1,4, 10, "","fun","" ));
		CityEventsList.add(new CityEvent("14","11/18/2014","4:00","Naacp", "13230 NW 7th Ave North Miami, FL 33168","(305) 685-8694", 8,5, 18, "","fun","" ));*/
		}catch(Exception e){
			Log.e("Initilize Data",e.toString());
		}
		
		try{
			/*ReportsList.add(new ReportedArea("Police",5.0,"3400 Airport Rd Boca Raton, FL 33431",null,null));
			ReportsList.add(new ReportedArea("Fire",30.0,"980 N Federal Hwy #200 Boca Raton, FL 33432",null,null));
			ReportsList.add(new ReportedArea("Traffic",100.0,"5800 NW 2nd Ave Boca Raton, FL 33487",null,null));*/
		}catch(Exception e){
			Log.e("Initilize Data",e.toString());
		}
		
		return true;
	}
	
	public static Location getLatLongFromAddress(String address){
		Geocoder geocoder = new Geocoder(c);
		Location location = null;

		if(address==null || address.equals("")){
            return null;
        }
		
		List<Address> searchResults = new ArrayList<Address>();
		try {
			searchResults  = geocoder.getFromLocationName(address, 5);
		} catch (IOException e) {
			Log.e("Location Search", e.toString());
		}
		
		if (searchResults != null){
			if(searchResults.size() > 0)
	        {
				String locationName = searchResults.get(0).getFeatureName();
				location = new Location(locationName);
				location.setLatitude(searchResults.get(0).getLatitude());
				location.setLongitude(searchResults.get(0).getLongitude());
	        }else{Log.i("LocationFinder", "No results found");};
		}else{Log.i("LocationFinder", "No results found");}
		
		return location;
	}
	
	/**
	 * @return the cityEventsList
	 */
	public static ArrayList<CityEvent> getCityEventsList() {
		return CityEventsList;
	}

	/**
	 * @param cityEventsList the cityEventsList to set
	 */
	public static void setCityEventsList(ArrayList<CityEvent> cityEventsList) {
		CityEventsList = cityEventsList;
	}
	/**
	 * @return the c
	 */
	public static Context getContext() {
		return c;
	}
	/**
	 * @param c the c to set
	 */
	public static void setContext(Context c) {
		AppData.c = c;
	}
	/**
	 * @return the reportsList
	 */
	public static ArrayList<ReportedArea> getReportsList() {
		return ReportsList;
	}
	/**
	 * @param reportsList the reportsList to set
	 */
	public static void setReportsList(ArrayList<ReportedArea> reportsList) {
		ReportsList = reportsList;
	}
	/**
	 * @return the currentLocation
	 */
	public static Location getCurrentLocation() {
		return currentLocation;
	}
	/**
	 * @param currentLocation the currentLocation to set
	 */
	public static void setCurrentLocation(Location currentLocation) {
		AppData.currentLocation = currentLocation;
	}
	
	public static int getEventIcon(String letter) {
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

	/**
	 * @return the filtersActivated
	 */
	public static boolean isFiltersActivated() {
		return filtersActivated;
	}

	/**
	 * @param filtersActivated the filtersActivated to set
	 */
	public static void setFiltersActivated(boolean filtersActivated) {
		AppData.filtersActivated = filtersActivated;
	}
	
}
