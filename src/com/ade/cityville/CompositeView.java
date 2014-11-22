/**
 * 
 */
package com.ade.cityville;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author alainedwards
 *
 */
public class CompositeView extends LinearLayout{
	private ImageView ivIcon;
	private TextView tvTitle, tvDatenTime;
	private LinearLayout llDetails;

	public CompositeView(Context context) {
		super(context);
		llDetails = new LinearLayout(context);
		ivIcon = new ImageView(context);
		tvTitle = new TextView(context);
		tvDatenTime = new TextView(context);
		
		LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.setMargins(0, 0, 0, 5);
		LayoutParams detailsParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		detailsParams.setMargins(10, 0, 0, 0);
		
		
		llDetails.setOrientation(VERTICAL);
		//llDetails.setLayoutParams(detailsParams);
		ivIcon.setAdjustViewBounds(true);
		ivIcon.setMaxHeight(55);
		ivIcon.setMaxWidth(55);
		tvTitle.setTextColor(getResources().getColor(android.R.color.black));
		tvDatenTime.setTextColor(getResources().getColor(android.R.color.black));
		tvTitle.setLayoutParams(textParams);
		//tvDatenTime.setLayoutParams(textParams);
		
		// Custom font
		//final Typeface dincond = Typeface.createFromAsset(context.getAssets(),"DINCond-Regular.otf");
		//tvTitle.setTypeface(dincond);
		//tvDatenTime.setTypeface(dincond);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvDatenTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		
		llDetails.addView(tvTitle);
		llDetails.addView(tvDatenTime);
		
		this.setGravity(Gravity.CENTER);
		this.setClickable(true);
		this.setOrientation(HORIZONTAL);
		
		this.addView(ivIcon);
		this.addView(llDetails);
	}

	/**
	 * @return the ivIcon
	 */
	public ImageView getIvIcon() {
		return ivIcon;
	}

	/**
	 * @param ivIcon the ivIcon to set
	 */
	public void setIvIcon(ImageView ivIcon) {
		this.ivIcon = ivIcon;
	}

	/**
	 * @return the tvTitle
	 */
	public TextView getTvTitle() {
		return tvTitle;
	}

	/**
	 * @param tvTitle the tvTitle to set
	 */
	public void setTvTitle(TextView tvTitle) {
		this.tvTitle = tvTitle;
	}

	/**
	 * @return the tvDatenTime
	 */
	public TextView getTvDatenTime() {
		return tvDatenTime;
	}

	/**
	 * @param tvDatenTime the tvDatenTime to set
	 */
	public void setTvDatenTime(TextView tvDatenTime) {
		this.tvDatenTime = tvDatenTime;
	}
	
	public void setTitle(String aTitle){
		this.tvTitle.setText(aTitle);
	}
	
	public String getTitle(){
		return (String) this.tvTitle.getText();
	}
	
	public void setDatenTime(String aDatanTime){
		this.tvDatenTime.setText(aDatanTime);
	}
	
	public String getDatenTime(){
		return (String) this.tvDatenTime.getText();
	}
}
