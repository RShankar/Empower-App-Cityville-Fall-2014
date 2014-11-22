package com.ade.cityville.Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.ade.cityville.AppData;
import com.ade.cityville.CityEvent;
import com.ade.cityville.R;
import com.ade.cityville.ReportedArea;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class RServer extends AsyncTask<Void, Void, String>{
	private static URL url;
	private static Context c; 
	static int TIMEOUT_MILLISEC = 30000;
	
	
   
    //
    HttpParams p = new BasicHttpParams();
	
	public static void setContext(Context ac){
		c = ac;
	}
	
	public static String sendCMD(String cmd) throws ClientProtocolException, IOException, JSONException{
		if (c == null){return null;} //TODO: FIX
		HttpParams httpParams = new BasicHttpParams();
		 HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
		 HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		 HttpParams p = new BasicHttpParams();
		 p.setParameter("cmd", cmd);
		 HttpClient httpclient = new DefaultHttpClient(p);
	     HttpPost httppost = new HttpPost(c.getString(R.string.db_address));
	     
	     List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                 2);
         nameValuePairs.add(new BasicNameValuePair("user", "1"));
         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         String responseBody = httpclient.execute(httppost,
                 responseHandler);
        return responseBody;

		/*System.setProperty("http.keepAlive", "false");
		url = new URL(c.getString(R.string.db_address));
		String data  = URLEncoder.encode("cmd", "UTF-8") 
				+ "=" + URLEncoder.encode(cmd, "UTF-8");
		URLConnection conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;
		httpConn.setAllowUserInteraction(false);
        httpConn.setInstanceFollowRedirects(true);
        httpConn.setRequestMethod("GET");
        httpConn.setDoOutput(true);
        httpConn.setRequestProperty("connection", "close");
        httpConn.connect(); 
        
		OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream()); 
		wr.write( data ); 
		BufferedReader reader = new BufferedReader(new 
		InputStreamReader(conn.getInputStream()));	
		
		StringBuffer sb = new StringBuffer("");
        String line="";
        while ((line = reader.readLine()) != null) {
           sb.append(line);
           break;
         }
         reader.close();
         return sb.toString();*/
	}
	
	public static ArrayList<CityEvent> getAllEvents() throws NumberFormatException, ParseException, IOException, JSONException{
		if (c == null){return null;}
		final ArrayList<CityEvent> list = new ArrayList<CityEvent>();
		/*String serverResponse = sendCMD("le");
		if (serverResponse != null && !(serverResponse.equalsIgnoreCase("")) && !(serverResponse.equalsIgnoreCase(" ")) && serverResponse.length() > 0){
			String[] events = serverResponse.split("+|+");
			if (events != null && events.length > 0 && events[0] != "" && events[0] != " "){
				for(String e: events){
					String[] details = e.split("-|-");
					if (details.length > 10){
						list.add(new CityEvent(Integer.parseInt(details[0]),details[1],details[2],details[3],details[4],details[5],Float.parseFloat(details[6]),Double.parseDouble(details[7]),Integer.parseInt(details[8]),details[9],details[10],details[11]));
					}else if (details.length <= 1){
						//This is the end of the list.
					}else{Log.e("Server - List events error", "events details has a length <= 1");}
				}
			}
		}*/
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("CityEvent");
		
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects,
					com.parse.ParseException e) {
				for(ParseObject object: objects){
					CityEvent ce = new CityEvent();
					ce.setName(object.getString("name"));
					ce.setAddress(object.getString("address"));
					ce.setAge(object.getInt("age"));
					ce.setId(object.getObjectId());
					ce.setCost(object.getDouble("cost"));
					ce.setDate(object.getString("date"));
					ce.setTime(object.getString("time"));
					ce.setPhonenumber(object.getString("phonenumber"));
					ce.setRating(object.getDouble("rating"));
					ce.setDescription(object.getString("description"));
					ce.setImg(object.getString("img"));
					ce.setTags(object.getString("tags"));
					list.add(ce);
				}
			}
		});
		
		if (list.size() < 1){
			return AppData.getCityEventsList();
		}
		
		return list;
	}
	
	public static ArrayList<ReportedArea> getAllReportedAreas(){
		final  ArrayList<ReportedArea> list = new  ArrayList<ReportedArea>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ReportedArea");
		
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects,
					com.parse.ParseException e) {
				for(ParseObject object: objects){
					ReportedArea ra = new ReportedArea();
					ra.setType(object.getString("type"));
					ra.setId(object.getObjectId());
					ra.setAddress(object.getString("address"));
					ra.setRadius(object.getDouble("radius"));
					ra.setLatitude(object.getString("latitude"));
					ra.setLongitude(object.getString("longitude"));
					list.add(ra);
				}
			}
		});
		
		return list;
	}
	public static Bitmap getImg(String aurl){
		try {
	        URL url = new URL(aurl);
	        HttpGet httpRequest = null;

	        httpRequest = new HttpGet(url.toURI());

	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response = (HttpResponse) httpclient
	                .execute(httpRequest);

	        HttpEntity entity = response.getEntity();
	        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
	        InputStream input = b_entity.getContent();

	        Bitmap bitmap = BitmapFactory.decodeStream(input);

	        return bitmap;
	    } catch (Exception e) {
	    	Log.e("Get Image From Server", e.toString());
	    }
		return null;
	}

	@Override
	protected String doInBackground(Void... params) {
		
		return null;
	}
}
