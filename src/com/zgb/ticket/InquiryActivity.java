package com.zgb.ticket;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Build;

public class InquiryActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inquiry);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inquiry, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_inquiry,
					container, false);
			return rootView;
		}
	}

	public void selectType(View view) {
		Button oneway = (Button)findViewById(R.id.onewaybutton);
		Button doubleway = (Button)findViewById(R.id.doublewaybutton);
		LinearLayout backpart = (LinearLayout)findViewById(R.id.backpart);
		
		if(view.getId() == R.id.onewaybutton){
			oneway.setBackgroundColor(Color.parseColor("#2292DD"));
			doubleway.setBackgroundColor(Color.parseColor("#808080"));
			backpart.setVisibility(8);
		}else{
			doubleway.setBackgroundColor(Color.parseColor("#2292DD"));
			oneway.setBackgroundColor(Color.parseColor("#808080"));
			backpart.setVisibility(0);
		}
		
	}
}
