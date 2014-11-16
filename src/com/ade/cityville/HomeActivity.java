package com.ade.cityville;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ade.cityville.adapters.*;

public class HomeActivity extends FragmentActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,NavigationDrawerFragmentR.NavigationDrawerCallbacks {

	MainCollectionPagerAdapter mCollectionPagerAdapter;
	ViewPager mViewPager;
	private static HomeGridFragment homeGridFragment;
	private static HomeListFragment homeListFragment;
	private static HomeMapFragment homeMapFragment;
	FragmentTransaction transaction;
	
	
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	private NavigationDrawerFragmentR mNavigationDrawerFragmentR;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		
		mNavigationDrawerFragmentR = (NavigationDrawerFragmentR) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer_right);
		
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		mNavigationDrawerFragmentR.setUp(R.id.navigation_drawer_right,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		homeGridFragment = new HomeGridFragment();
		homeListFragment = new HomeListFragment();
		homeMapFragment = new HomeMapFragment();
		
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, homeGridFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		//---------------Initialize variables for the Tabs/view pager------------------------
		/*final ActionBar actionBar = getActionBar();
		mCollectionPagerAdapter = new MainCollectionPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
     // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        	@Override
        	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        		mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Adds the Home tabs to the action as specified in the strings.xml array.
        for (String tabname: getResources().getStringArray(R.array.home_tabs)){
        	actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabname)
                            .setTabListener(tabListener));
        }
        
      
        /*for (int i = 0; i < 3; i++) {
        	actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }*/
        
      //-----------------------------------------End--------------------------------------------
		
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = new HomeGridFragment();
		switch(position) {
        case 0:
            fragment = new HomeGridFragment();
            break;
        case 1:
            fragment = new HomeGridFragment();
            break;
        case 2:
        	break;
		}
		fragmentManager
				.beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.home, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((HomeActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
	public void showGridView(){
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, homeGridFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
	}
	public void showListView(){
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, homeListFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	public void showMapView(){
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, homeMapFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
	}

}
