package com.ade.cityville;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


public class CityEvent implements Parcelable{
	private double cost;
	private double rating;
	String id;
	private int age;
	private String date, time, name, description, img, address, phonenumber, tags;
	private Calendar datentime;
	private Location location;
	
	public CityEvent(){
		
	}
	
	public CityEvent(String aid, String adate, String atime, String aname, 
			String alocation, String apn, double acost, double arating, int aage, 
			String adescription, String atags, String aimg) throws ParseException{
		
		id = aid;
		date = adate;
		time = atime;
		datentime = stringToCalendar(adate + "T" + atime, TimeZone.getTimeZone("GMT -4:00"));
		name = aname;
		address = alocation;
		phonenumber = apn;
		cost = acost;
		rating = arating;
		age = aage;
		description = adescription;
		tags = atags;
		img = aimg;
		
		location = AppData.getLatLongFromAddress(address);
		if (location == null)
		{location = new Location("error");}
	}
	
	
	public static Calendar stringToCalendar(String strDate, TimeZone timezone) throws ParseException {
        String FORMAT_DATETIME = "MM/dd/yyyy'T'HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        sdf.setTimeZone(timezone);
        Date date = sdf.parse(strDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
	
	


	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}


	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}


	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}


	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
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


	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}


	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
		if (time != null){
		try {
			datentime = stringToCalendar(getDate() + "T" + getTime(), TimeZone.getTimeZone("GMT -4:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
	}


	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}


	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
		if (date != null){
		try {
			datentime = stringToCalendar(getDate() + "T" + time, TimeZone.getTimeZone("GMT -4:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}


	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}


	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}


	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}


	/**
	 * @return the location
	 */
	public String getAddress() {
		
		location = AppData.getLatLongFromAddress(address);
		if (location == null)
		{location = new Location("error");}
		
		return address;
	}


	/**
	 * @param location the location to set
	 */
	public void setAddress(String aaddress) {
		this.address = aaddress;
		location = AppData.getLatLongFromAddress(address);
		if (location == null)
		{location = new Location("error");}
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
	 * @return the phonenumber
	 */
	public String getPhonenumber() {
		return phonenumber;
	}


	/**
	 * @param phonenumber the phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}


	/**
	 * @return the datentime
	 */
	public Calendar getDatentime() {
		return datentime;
	}


	/**
	 * @param datentime the datentime to set
	 */
	public void setDatentime(Calendar datentime) {
		this.datentime = datentime;
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(id);
		dest.writeString(date);
		dest.writeString(time);
		dest.writeString(description);
		dest.writeString(address);
		dest.writeString(phonenumber);
		dest.writeString(img);
		dest.writeDouble(rating);
		dest.writeDouble(cost);
		dest.writeInt(age);
		
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<CityEvent> CREATOR = new Parcelable.Creator<CityEvent>() {
        public CityEvent createFromParcel(Parcel in) {
            return new CityEvent(in);
        }

        public CityEvent[] newArray(int size) {
            return new CityEvent[size];
        }
    };
    
 // example constructor that takes a Parcel and gives you an object populated with it's values
    private CityEvent(Parcel in) {
        name = in.readString();
        id = in.readString();
        date = in.readString();
		time = in.readString();
		description = in.readString();
		address = in.readString();
		phonenumber = in.readString();
		img = in.readString();
		rating = in.readDouble();
		cost = in.readFloat();
		age = in.readInt();
         
    }

}
