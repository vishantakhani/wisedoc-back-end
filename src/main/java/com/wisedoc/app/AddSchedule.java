package com.wisedoc.app;

public class AddSchedule {
	private int hospitalId;

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private String date;
	private String time;
	private int scheduleId;

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int i) {
		this.scheduleId = i;
	}
}
