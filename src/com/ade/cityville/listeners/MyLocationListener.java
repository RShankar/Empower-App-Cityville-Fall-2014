/**
 * 
 */
package com.ade.cityville.listeners;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.ade.cityville.AppData;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * @author Alain Edwards
 *
 */
public class MyLocationListener implements LocationListener{
	private String TAG = "My Location Listener";
	private String longitude,latitude,cityName;
	private static Context c;
	
	public MyLocationListener(Context ac){
		c = ac;
	}

	@Override  
    public void onLocationChanged(Location nloc) {
		final Location loc = nloc;
		new Thread(new Runnable() {

            @Override
            public void run() {
         
            if(AppData.getCurrentLocation() == null){AppData.setCurrentLocation(loc);}
            else if (isBetterLocation(AppData.getCurrentLocation(),loc)){
            	AppData.setCurrentLocation(loc);
            }
		
        longitude = "Longitude: " +loc.getLongitude();    
        Log.i(TAG, longitude);  
        latitude = "Latitude: " +loc.getLatitude();  
        Log.i(TAG, latitude);  
        
	/*----------to get City-Name from coordinates ------------- */  
	  cityName=null;                
	  Geocoder gcd = new Geocoder(c,Locale.getDefault());               
	  List<Address>  addresses;    
	  try {    
		  addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);    
		  if (addresses.size() > 0)    
		     System.out.println(addresses.get(0).getLocality());    
		     cityName=addresses.get(0).getLocality();    
	    } catch (IOException e) {              
	    e.printStackTrace();    
	  }   
	        
	  String result = longitude+"\n"+latitude + "\n\nMy Currrent City is: "+cityName;  
            }}).start();
    }  

    @Override  
    public void onProviderDisabled(String provider) {  
                 
    }  

    @Override  
    public void onProviderEnabled(String provider) {  
                  
    }  

    @Override  
    public void onStatusChanged(String provider,int status, Bundle extras) {  
                   
    }
    
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /** Determines whether one Location reading is better than the current Location fix
      * @param location  The new Location that you want to evaluate
      * @param currentBestLocation  The current Location fix, to which you want to compare the new one
      */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }
        if (location == null){
        	return false;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }

	/**
	 * @return the c context
	 */
	public static Context getContext() {
		return c;
	}

	/**
	 * @param c the context to set
	 */
	public static void setContext(Context ac) {
		MyLocationListener.c = ac;
	} 
}
