package com.ade.cityville;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class CityEventActivity extends Activity {
	private CityEvent aCityEvent;
	private int eventPostion;
	private TabHost thMain;
	private TextView tvName, tvCost, tvAge;
	private ImageView ivIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_event);
		
		ivIcon = (ImageView) findViewById(R.id.imageViewCEIcon);
		tvName = (TextView) findViewById(R.id.textViewCEName);
		tvCost = (TextView) findViewById(R.id.textViewCECost);
		tvAge = (TextView) findViewById(R.id.textViewCEAge);
		
		Intent mIntent = getIntent();
		aCityEvent = (CityEvent) mIntent.getParcelableExtra("THE-CITY-EVENT");
		//eventPostion = mIntent.getIntExtra("CITY_EVENT_POSITION", 0);
		
		if (aCityEvent != null){
			String image = aCityEvent.getImg();
			if (image.equalsIgnoreCase("") || image.equalsIgnoreCase(" ") || image == null){
				ivIcon.setImageResource(AppData.getEventIcon(aCityEvent.getName().substring(0, 1).toLowerCase()));
	        }else{
	        	ivIcon.setImageURI(Uri.parse(getString(R.string.image_server_address) + image));
	        }
			tvName.setText(aCityEvent.getName());
			tvCost.setText("Cost: "+ aCityEvent.getCost());
			tvAge.setText("Age: "+ aCityEvent.getAge() + "+");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_event, menu);
		return true;
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
}
