/**
 * 
 */
package com.ade.cityville;

import android.location.Location;

/**
 * @author alainedwards
 *
 */
public class ReportedArea {
	private double radius;
	private String id, type, address, longitude, latitude;
	private Location location;
	
	public ReportedArea(){}
	
	public ReportedArea(String atype, double aradius, String aadress, String along, String alat ){
		type = atype;
		radius = aradius;
		address = aadress;
		if(along != null && alat != null)
		{
			if ((!alat.equals("") && !alat.equals(" ")) && (!along.equals("") && !along.equals(" "))){
				location = new Location(atype);
				location.setLatitude(Double.parseDouble(alat));
				location.setLongitude(Double.parseDouble(along));
			}
		}
		else {
			location = AppData.getLatLongFromAddress(aadress);
			longitude = "" + location.getLatitude();
			latitude = "" + location.getLongitude();}
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		if (address != null){
			if (!address.equalsIgnoreCase(" ") && !address.equalsIgnoreCase(""))
			this.address = address;
			location = AppData.getLatLongFromAddress(address);
			longitude = "" + location.getLatitude();
			latitude = "" + location.getLongitude();
		}
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		
		
		if(longitude != null)
		{
			if (!longitude.equals("") && !longitude.equals(" ")){
				this.longitude = longitude;
				if (location != null){
					location.setLongitude(Double.parseDouble(longitude));
				}else{
					location = new Location(this.id);
					location.setLatitude(Double.parseDouble(latitude));
					location.setLongitude(Double.parseDouble(longitude));
				}
			}
		}	
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		if(latitude != null)
		{
			if (!latitude.equals("") && !latitude.equals(" ")){
				this.latitude = latitude;
				if (location != null){
					location.setLatitude(Double.parseDouble(latitude));
				}else{
					location = new Location(this.id);
					location.setLatitude(Double.parseDouble(latitude));
					location.setLongitude(Double.parseDouble(longitude));
				}
			}
		}
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
