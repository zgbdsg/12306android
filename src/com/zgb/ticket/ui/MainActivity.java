package com.zgb.ticket.ui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.zgb.ticket.R;
import com.zgb.ticket.R.id;
import com.zgb.ticket.R.layout;
import com.zgb.ticket.R.menu;
import com.zgb.ticket.R.string;
import com.zgb.ticket.other.TabFragmentPagerAdapter;
import com.zgb.ticket.util.TicketHttpClient;

public class MainActivity extends ActionBarActivity {

	AsyncHttpClient httpclient = TicketHttpClient.httpclient;
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	private ViewPager viewpaper;
	private TabFragmentPagerAdapter tabAdbter;
	private ActionBar actionbar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//getFragmentManager().beginTransaction().replace(R.id.container,new InquiryFragment()).commit();
		TicketHttpClient.init(this);
		
		
		/*initial view paper*/
		viewpaper = (ViewPager)findViewById(R.id.viewpaper);
		tabAdbter = new TabFragmentPagerAdapter(getSupportFragmentManager());
		viewpaper.setAdapter(tabAdbter);
		viewpaper.setOffscreenPageLimit(3);
		viewpaper.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionbar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		viewpaper.setCurrentItem(0);
		
		/*add tabs to the action bar*/
		actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		TabListener tablistener = new TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				Log.i("tab select :", ""+tab.getPosition());
				switch (tab.getPosition()) {
				case 0:
					viewpaper.setCurrentItem(0);
					//getFragmentManager().beginTransaction().replace(R.id.container,new InquiryFragment()).commit();
					break;
				case 1:
					viewpaper.setCurrentItem(1);
					break;
				case 2:
					viewpaper.setCurrentItem(2);
					//getFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment()).commit();
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		actionbar.addTab(actionbar.newTab().setText(R.string.searchtab).setTabListener(tablistener));
		actionbar.addTab(actionbar.newTab().setText(R.string.orderstab).setTabListener(tablistener));
		actionbar.addTab(actionbar.newTab().setText(R.string.usertab).setTabListener(tablistener));
		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		//return true;
		 // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
		//MenuItem add = menu.add(0, 1, 0, "Save");  
 
        //add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);  
	    //return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_search:
			
			return true;

		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}

