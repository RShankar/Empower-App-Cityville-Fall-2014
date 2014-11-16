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
	private String address;
	private String longitude, latitude;
	private String type;
	private Location location;
	
	public ReportedArea(String atype, double aradius, String aadress, String along, String alat ){
		type = atype;
		radius = aradius;
		address = aadress;
		longitude = along;
		latitude = alat;
		location = new Location(atype);
		location.setLatitude(Double.parseDouble(alat));
		location.setLongitude(Double.parseDouble(along));
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
		this.address = address;
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
		this.longitude = longitude;
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
		this.latitude = latitude;
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
}
