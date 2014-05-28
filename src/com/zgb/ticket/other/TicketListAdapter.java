package com.zgb.ticket.other;

import java.util.HashMap;
import java.util.List;

import com.zgb.ticket.R;
import com.zgb.ticket.info.TrainInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TicketListAdapter extends BaseAdapter{
	
	private  class  ViewHolder {
		TextView trainNum;
		TextView toStation;
		TextView fromStation;
		TextView arriveTime;
		TextView period;
		TextView ticketInfo;
		TextView ticketValue;
		TextView trainType;
	}
	
	private Context context; 
	private LayoutInflater layoutInflater;    
	private List<HashMap<String, Object>> list;
	private  ViewHolder holder;
	
	public TicketListAdapter(Context context, List<HashMap<String, Object>> list)
	{
		this.context = context;        
		layoutInflater = LayoutInflater.from(context);        
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("list size :", ""+this.list.size() );
		return this.list.size() ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//super.getDropDownView(position, convertView, parent);
		Log.i("asdfasdf", "getView");
		if(convertView != null){
			holder = (ViewHolder)convertView.getTag();
		}else {
			convertView = layoutInflater.inflate(R.layout.view_ticket_item, null);
			holder = new ViewHolder();
			
			holder.arriveTime = (TextView)convertView.findViewById(R.id.finishTime);
			holder.fromStation = (TextView)convertView.findViewById(R.id.fromStation);
			holder.period = (TextView)convertView.findViewById(R.id.period);
			holder.ticketInfo = (TextView)convertView.findViewById(R.id.ticketInfo);
			holder.ticketValue = (TextView)convertView.findViewById(R.id.moneyValue);
			holder.toStation = (TextView)convertView.findViewById(R.id.toStation);
			holder.trainNum = (TextView)convertView.findViewById(R.id.trainNum);
			holder.trainType = (TextView)convertView.findViewById(R.id.trainType);
			
			convertView.setTag(holder);
			Log.i("convertview", convertView.toString());
		}
		 TrainInfo trainInfo = (TrainInfo) list.get(position).get("trainInfo");
		 Log.i("test adapter:", trainInfo.toString());
		 holder.arriveTime.setText(trainInfo.getArriveTime());
		 holder.fromStation.setText(trainInfo.getFromStation());
		 holder.period.setText(trainInfo.getPeriod());
		 holder.ticketInfo.setText(trainInfo.getTicketValue());
		 holder.ticketValue.setText(trainInfo.getTicketValue());
		 holder.toStation.setText(trainInfo.getToStation());
		 holder.trainNum.setText(trainInfo.getTrainNum());
		 holder.trainType.setText(trainInfo.getTrainType());
		
		return convertView;
	}

}
