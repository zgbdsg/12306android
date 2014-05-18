package com.zgb.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TicketResultActivity extends ListActivity{

	private TextView trainNum;
	private TextView trainType;
	private TextView startTime;
	private TextView arriveTime;
	private TextView period;
	private TextView fromStation;
	private TextView toStation;
	private TextView ticketInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_result);
		Bundle info = this.getIntent().getExtras();
		String result = info.getString("result");
		
		List<HashMap<String, String>> listMap = getListMaps(result);
		
		SimpleAdapter dataAdapter = new SimpleAdapter(this, listMap, R.layout.view_ticket_item, 
				new String[]{"trainNum","startTime","fromStation","period","trainType","arriveTime","toStation","value","ticketInfo"}, 
				new int[]{R.id.trainNum,R.id.startTime,R.id.fromStation,R.id.period,R.id.trainType,R.id.finishTime,R.id.toStation,R.id.moneyValue,R.id.ticketInfo});
	
		setListAdapter(dataAdapter);
	}


	
	
	private List<HashMap<String, String>> getListMaps(String result){
		List<HashMap<String, String>> listmap = new ArrayList<HashMap<String,String>>(); 
		JSONObject jsonResult = JSON.parseObject(result);
		JSONObject jsonData = jsonResult.getJSONObject("data");
		JSONArray dataArrays = jsonData.getJSONArray("datas");
		
		for(int i=0;i<dataArrays.size();i ++){
			HashMap<String, String> map = new HashMap<String, String>();
			String train_num_code = dataArrays.getJSONObject(i).getString("station_train_code");
			map.put("trainNum", train_num_code);
			map.put("toStation", dataArrays.getJSONObject(i).getString("to_station_name"));
			map.put("fromStation", dataArrays.getJSONObject(i).getString("from_station_name"));
			map.put("startTime", dataArrays.getJSONObject(i).getString("start_time"));
			map.put("arriveTime", dataArrays.getJSONObject(i).getString("arrive_time"));
			map.put("period", dataArrays.getJSONObject(i).getString("lishi"));
			map.put("ticketInfo", "waiting ----");
			map.put("value", "waiting ----");
			String train_class = dataArrays.getJSONObject(i).getString("train_class_name");
			if(!train_class.equals("")) {
				map.put("trainType", train_class);
			}else{
				Log.i("trainType :", ""+train_num_code.charAt(0));
				switch (train_num_code.charAt(0)) {
				case 'K':
					map.put("trainType",getString(R.string.kname));
					break;
				case 'T':
					map.put("trainType",getString(R.string.tname));
					break;
				case 'Z':
					map.put("trainType",getString(R.string.zname));
					break;
				case 'L':
					map.put("trainType",getString(R.string.lname));
					break;
				case 'Y':
					map.put("trainType",getString(R.string.yname));
					break;
				default:
					map.put("trainType",getString(R.string.common));
					break;
				}
			}
			
			listmap.add(map);

		}
		return listmap;
	}

}
