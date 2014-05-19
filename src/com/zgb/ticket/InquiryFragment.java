package com.zgb.ticket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.Header;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
	
	private String fromStationCode = "";
	private String toStationCode = "";
	
	private OnDateSetListener datelisten;
	private GetStationCodeTask getStationCode;
	private CustomProgressDialog onLoading;
	AsyncHttpClient httpclient = TicketHttpClient.httpclient;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		cal = Calendar.getInstance();
		//this.savedInstanceState = savedInstanceState;
		Log.i("fragment life ", "onCreateView");
		//Log.i("from activity saved ", savedInstanceState.toString());
		return inflater.inflate(R.layout.fragment_inquiry, container, false);  
	}
	
	@Override
	public void onResume(){
		super.onResume();
		getStationCode = new GetStationCodeTask();
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLoading.show();
				
				getStationCode.execute("");
				Log.e("begin search :", "!!!!!!!!!!");
				
			}
		});
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i("fragment life ", "onActivityCreated");
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
		
		fromPlace = (EditText)getActivity().findViewById(R.id.fromplace);
		fromPlace.setSelectAllOnFocus(true);
		toPlace = (EditText)getActivity().findViewById(R.id.toplace);
		toPlace.setSelectAllOnFocus(true);
		
		SharedPreferences sharedData = getActivity().getSharedPreferences("12306", 0);
		
		if(sharedData != null){
			departuretime.setText(sharedData.getString("departuretime",""));
			fromPlace.setText(sharedData.getString("fromPlace",""));
			toPlace.setText(sharedData.getString("toPlace",""));
		}
		
		onLoading = new CustomProgressDialog(getActivity());
		onLoading.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				httpclient.cancelRequests(getActivity(), true);
				getStationCode.cancel(true);
			}
		});
	}
	
	public void doSearchTicket(View view) {
	    // Do something in response to button

		Log.i("begin search ticket :", fromStationCode+"  "+toStationCode);
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
		params.append("&from_station="+fromStationCode);
		params.append("&to_station="+toStationCode);
		
		loginurl.append(params);
		TicketHttpClient.get(getActivity(), loginurl.toString(),null,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String response) {
				onLoading.dismiss();
				Editor shareDataEditor = getActivity().getSharedPreferences("12306", 0).edit();
				shareDataEditor.putString("departuretime", departuretime.getText().toString());
				shareDataEditor.putString("fromPlace", fromPlace.getText().toString());
				shareDataEditor.putString("toPlace", toPlace.getText().toString());
				shareDataEditor.commit();
				
				Intent intent = new Intent(getActivity().getApplicationContext(),TicketResultActivity.class);
				intent.putExtra("result", response);
				startActivity(intent);
            }

			
			@Override
			public void onFailure(int arg0, Header[] headers, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, headers, arg2, arg3);
				onLoading.dismiss();
			}

			
		});

	}
	

	public class GetStationCodeTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String fromStationName = fromPlace.getText().toString();
			String toStationName = toPlace.getText().toString();
			fromStationCode = "";
			toStationCode = "";
			Log.i("search station :", fromStationName+"  "+toStationName);
			
			try {
				InputStreamReader inputReader = new InputStreamReader(getActivity().getAssets().open("stations.txt"));
				BufferedReader bufReader = new BufferedReader(inputReader);
				String line = bufReader.readLine();
				while (line.length() > 0){
					String[] datas = line.split(" ");
					//Log.i("Compare to ", datas[0]);
					if(datas[0].equals(fromStationName)){
						fromStationCode = datas[1];
					}else if(datas[0].equals(toStationName)){
						toStationCode = datas[1];
					}
					
					if(fromStationCode.length() >0 && toStationCode.length()>0)
						break;
					line = bufReader.readLine();
				}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("search result :", fromStationCode+"  "+toStationCode);
			if(!(fromStationCode.length() >0 && toStationCode.length()>0))
				return false;
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//onLoading.dismiss();
			
			if(!(fromStationCode.length() >0 && toStationCode.length()>0))
				return;
			else
				doSearchTicket(null);
		}
		
		
		
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
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("fragment life ", "onDestroy");
	}



	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.i("fragment life ", "onDetach");
	}



	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("fragment life ", "onPause");
	}



	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("fragment life ", "onStop");
	}
}
