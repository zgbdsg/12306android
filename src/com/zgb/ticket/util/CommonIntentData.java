package com.zgb.ticket.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonIntentData {
	private static CommonIntentData commonData = new CommonIntentData();
	public  List<HashMap<String, Object>> trainInfoList;
	
	private CommonIntentData(){}
	
	
	public static CommonIntentData getInstance(){
		return commonData;
	}
	public void setList(List<HashMap<String, Object>> srcList) {
		trainInfoList = new ArrayList<HashMap<String,Object>>(srcList);
	}
	
	public List<HashMap<String, Object>> getList() {
		return trainInfoList;
	}
}
