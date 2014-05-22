package com.zgb.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

public class TicketResultActivity extends ActionBarActivity{

	private List<String> trainCodeList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_result);
		//Bundle info = this.getIntent().getExtras();
		//List<HashMap<String, Object>> result = ()info.getString("result");
		
		//Log.i("inquiry :", result);
		trainCodeList = new ArrayList<String>();
		
		//List<HashMap<String, Object>> listMap = getListMaps(result);
		List<HashMap<String, Object>> listMap = CommonIntentData.getInstance().trainInfoList;
		Log.i("trainInfo :", listMap.get(0).get("trainInfo").toString());
		TicketListAdapter listAdapter = new TicketListAdapter(this, listMap);
		ListView listview = (ListView) findViewById(R.id.resultList);
		listview.setAdapter(listAdapter);
	}

}
