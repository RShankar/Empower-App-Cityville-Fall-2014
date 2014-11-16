/**
 * 
 */
package com.ade.cityville.adapters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.ade.cityville.R;

/**
 * @author Operator
 *
 */
public class NDExpandableListAdapterR extends BaseExpandableListAdapter{
	private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
 
    public NDExpandableListAdapterR(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (groupPosition == 0) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_home_r_item1, null);
        } else  if (groupPosition == 1) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_home_r_item2, null);
        }
        
        if (groupPosition == 0){
        	CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBoxEventFilter);
        	cb.setText(childText);
        }
        else if (groupPosition == 1){
        	Spinner spin = (Spinner) convertView.findViewById(R.id.spinnerAGE);
        	
        	if (spin == null || spin.getAdapter() == null){
        		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this._context, android.R.layout.simple_spinner_item,  this._context.getResources().getStringArray(R.array.age_restrictions)); //selected item will look like a spinner set from XML
        		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spin.setAdapter(spinnerArrayAdapter);
        		spin.setOnItemSelectedListener(new OnItemSelectedListener() {
        		    @Override
        		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		    	//For future
        		    }

        		    @Override
        		    public void onNothingSelected(AdapterView<?> parentView) {
        		        // For future use
        		    }

        		});
        	}
        }
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	if ((this._listDataChild.get(this._listDataHeader.get(groupPosition))) == null){return 0;}
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_home_r, null);
        }
        
        if ( getChildrenCount( groupPosition ) == 0 ) {
        	//convertView.indicator.setVisibility( View.INVISIBLE );
         } 
        else {
            //indicator.setVisibility( View.VISIBLE );
         }
 
        TextView lblListHeader = (TextView) convertView.findViewById(android.R.id.text1);
        ImageView headerImg = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        lblListHeader.setText(headerTitle);
        
        switch(headerTitle){
        	case "Home":
        			headerImg.setImageResource(R.drawable.androidhome);
        		break;
        	default:
        			headerImg.setVisibility(View.GONE);
        	break;
        }
 
        return convertView;
    }
    
    
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
