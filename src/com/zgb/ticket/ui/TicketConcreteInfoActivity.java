package com.zgb.ticket.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zgb.ticket.R;
import com.zgb.ticket.info.TrainStationInfo;
import com.zgb.ticket.other.ScollListView;
import com.zgb.ticket.util.TicketHttpClient;

public class TicketConcreteInfoActivity extends Activity{
	String train_no;
	String from_station_telecode;
	String to_station_telecode;
	String depart_date;
	
	ScollListView stationResultList;
	ScollListView seatresultList;
	
	AsyncHttpClient httpclient = TicketHttpClient.httpclient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_concrete_info);
		stationResultList =(ScollListView)findViewById(R.id.stationresultList);
	}

	
	public class StationListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public void getStationInfo() {
		
		StringBuffer loginurl = new StringBuffer("/otn/czxx/queryByTrainNo?");
		StringBuffer params = new StringBuffer();
		params.append("train_no="+train_no);
		params.append("&from_station_telecode="+from_station_telecode);
		params.append("&to_station_telecode="+to_station_telecode);
		params.append("&depart_date="+depart_date);
		
		loginurl.append(params);
		TicketHttpClient.get(this, loginurl.toString(),null,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String response) {
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] headers, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, headers, arg2, arg3);
			}
		});
	}
	
	public List<TrainStationInfo> getTrainStationList(String response) {
		List<TrainStationInfo> trainStationList = new ArrayList<TrainStationInfo>();
		return trainStationList;
	}
}
