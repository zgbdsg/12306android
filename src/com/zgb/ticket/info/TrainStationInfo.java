package com.zgb.ticket.info;

import java.io.Serializable;

public class TrainStationInfo implements Serializable{

	/**
	 * @author zgb
	 */
	private static final long serialVersionUID = -1816714428638753866L;
	private String station_no;
	private String station;
	private String arrive_time;
	private String start_time;
	private String stopover_time;
	public String getStation_no() {
		return station_no;
	}
	public void setStation_no(String station_no) {
		this.station_no = station_no;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getStopover_time() {
		return stopover_time;
	}
	public void setStopover_time(String stopover_time) {
		this.stopover_time = stopover_time;
	}
	
}
