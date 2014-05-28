package com.zgb.ticket.info;

import java.io.Serializable;

public class TrainInfo implements Serializable {
	/**
	 * @author zgb
	 */
	private static final long serialVersionUID = 1010250520744443426L;
	String trainNum;
	String toStation;
	String fromStation;
	String arriveTime;
	String period;
	String ticketInfo;
	String ticketValue;
	String trainType;
	String startTime;
	
	public String getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}
	public String getToStation() {
		return toStation;
	}
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(String ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
	public String getTicketValue() {
		return ticketValue;
	}
	public void setTicketValue(String ticketValue) {
		this.ticketValue = ticketValue;
	}
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"  "+arriveTime+"  "+fromStation;
	}
}
