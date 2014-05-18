package com.zgb.ticket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

public class InquiryFragment extends Fragment{
	
	private Button btnOneway;
	private Button btnDoubleway;
	private Button btnSearch;
	
	private CheckBox checkall;
	private CheckBox gdz;
	private CheckBox justz;
	private CheckBox justt;
	private CheckBox justk;
	private CheckBox otherCheck;
	
	private EditText departuretime;
	private EditText fromPlace;
	private EditText toPlace;
	private Calendar cal;
	private OnDateSetListener datelisten;
	private Bundle savedInstanceState;
	
	AsyncHttpClient httpclient = TicketHttpClient.httpclient;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		cal = Calendar.getInstance();
		//this.savedInstanceState = savedInstanceState;
		return inflater.inflate(R.layout.fragment_inquiry, container, false);  
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		btnOneway = (Button)getActivity().findViewById(R.id.onewaybutton);
		btnOneway.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectType(v);
			}
		});
		btnDoubleway = (Button)getActivity().findViewById(R.id.doublewaybutton);
		btnDoubleway.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectType(v);
			}
		});
		
		datelisten = new  OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				cal.set(year, monthOfYear, dayOfMonth);
				updatecalendar(departuretime);
			}
		};
		
		departuretime = (EditText)getActivity().findViewById(R.id.departuretime);

		departuretime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(getActivity(),datelisten ,
						cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
			}
		});

		initCheckBox();
		
		btnSearch = (Button)getActivity().findViewById(R.id.btnInquiry);
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doSearchTicket(v);
			}
		});
		
		if(savedInstanceState != null){
			departuretime.setText(savedInstanceState.getString("departuretime"));
			fromPlace.setText(savedInstanceState.getString("fromplace"));
			toPlace.setText(savedInstanceState.getString("toplace"));
		}
	}
	
	
	public void doSearchTicket(View view) {
	    // Do something in response to button
		
		StringBuffer loginurl = new StringBuffer("/otn/lcxxcx/query?");
		//String loginurl = "/otn/lcxxcx/query?purpose_codes=ADULT&queryDate=2014-05-16&from_station=XAY&to_station=NJH";
		//RequestParams params = new RequestParams();
		//params.add("purpose_codes", "ADULT");
		//params.add("queryDate", departuretime.getText().toString());
		//params.add("from_station", "XAY");
		//params.add("to_station", "NJH");
		
		StringBuffer params = new StringBuffer();
		params.append("purpose_codes=ADULT");
		params.append("&queryDate="+departuretime.getText().toString());
		params.append("&from_station=XAY");
		params.append("&to_station=NJH");
		
		loginurl.append(params);
		TicketHttpClient.get(loginurl.toString(),null,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String response) {
				Intent intent = new Intent(getActivity().getApplicationContext(),InquiryActivity.class);
				intent.putExtra("result", response);
				startActivity(intent);
            }

			
			@Override
			public void onFailure(int arg0, Header[] headers, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, headers, arg2, arg3);
			}

			
		});

	}
	
	
	private void initCheckBox() {
		// TODO Auto-generated method stub
		checkall = (CheckBox)getActivity().findViewById(R.id.chooseall);
		gdz = (CheckBox)getActivity().findViewById(R.id.gdz);
		justz = (CheckBox)getActivity().findViewById(R.id.justz);
		justt = (CheckBox)getActivity().findViewById(R.id.justt);
		justk = (CheckBox)getActivity().findViewById(R.id.justk);
		otherCheck = (CheckBox)getActivity().findViewById(R.id.othertype);
		
		checkBoxsListener cbListener = new checkBoxsListener(checkall, gdz, justz, justt, justk, otherCheck);
		
		checkall.setOnCheckedChangeListener(cbListener);
		gdz.setOnCheckedChangeListener(cbListener);
		justz.setOnCheckedChangeListener(cbListener);
		justt.setOnCheckedChangeListener(cbListener);
		justk.setOnCheckedChangeListener(cbListener);
		otherCheck.setOnCheckedChangeListener(cbListener);
	}

	public void updatecalendar(EditText e) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		e.setText(df.format(cal.getTime()));
	}
	
	
	public void selectType(View view) {

		LinearLayout backpart = (LinearLayout)getActivity().findViewById(R.id.backpart);
		
		if(view.getId() == R.id.onewaybutton){
			btnOneway.setBackgroundColor(Color.parseColor("#2292DD"));
			btnDoubleway.setBackgroundColor(Color.parseColor("#808080"));
			backpart.setVisibility(8);
		}else{
			btnDoubleway.setBackgroundColor(Color.parseColor("#2292DD"));
			btnOneway.setBackgroundColor(Color.parseColor("#808080"));
			backpart.setVisibility(0);
		}
		
	}
	
	public static class checkBoxsListener implements OnCheckedChangeListener{

		private CheckBox checkall;
		private CheckBox gdz;
		private CheckBox justz;
		private CheckBox justt;
		private CheckBox justk;
		private CheckBox otherCheck;
		
		
		public checkBoxsListener(CheckBox checkall, CheckBox gdz,
				CheckBox justz, CheckBox justt, CheckBox justk,
				CheckBox otherCheck) {
			super();
			this.checkall = checkall;
			this.gdz = gdz;
			this.justz = justz;
			this.justt = justt;
			this.justk = justk;
			this.otherCheck = otherCheck;
		}


		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			//Log.i("checkbox :", ""+buttonView.getText()+"  "+isChecked+"  ");
			
			if(buttonView.getText().equals("È«²¿")){
				checkall.setChecked(isChecked);
				if(checkall.isChecked()) {
					gdz.setChecked(true);
					justz.setChecked(true);
					justt.setChecked(true);
					justk.setChecked(true);
					otherCheck.setChecked(true);
				}
				return;
			}else{
				buttonView.setChecked(isChecked);
				
				if(gdz.isChecked()&justz.isChecked()&justt.isChecked()&justk.isChecked()&otherCheck.isChecked()){
					checkall.setChecked(true);
					return;
				}else{
					checkall.setChecked(false);
				}
			}
		}
		
	}
}
