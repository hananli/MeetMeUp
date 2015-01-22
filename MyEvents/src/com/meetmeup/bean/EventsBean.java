package com.meetmeup.bean;

import java.util.ArrayList;
import java.util.Date;


//This class is used for holding Events data.
public class EventsBean {
	 
	private String event_id;
	private String event_title;
	private String event_date;
	private String event_description;
	private String event_createdby;
	private String event_Address;
	private String event_lat;
	private String event_eventLong;
	private String collect_money_from_participants;
	private String evetn_owner_id;
	private String min_participants;
	private String max_participants;
	
	public String getMin_participants() {
		return min_participants;
	}
	public void setMin_participants(String min_participants) {
		this.min_participants = min_participants;
	}
	public String getMax_participants() {
		return max_participants;
	}
	public void setMax_participants(String max_participants) {
		this.max_participants = max_participants;
	}
	ArrayList<ParticipantsBean> participantsList;
	
	public ArrayList<ParticipantsBean> getParticipantsList() {
		return participantsList;
	}
	public void setParticipantsList(ArrayList<ParticipantsBean> participantsList) {
		this.participantsList = participantsList;
	}
	public String getEvetn_owner_id() {
		return evetn_owner_id;
	}
	public void setEvetn_owner_id(String evetn_owner_id) {
		this.evetn_owner_id = evetn_owner_id;
	}
	public String getCollect_money_from_participants() {
		return collect_money_from_participants;
	}
	public void setCollect_money_from_participants(
			String collect_money_from_participants) {
		this.collect_money_from_participants = collect_money_from_participants;
	}
	private Date event_Date_Object;
	
	public Date getEvent_Date_Object() {
		return event_Date_Object;
	}
	public void setEvent_Date_Object(Date event_Date_Object) {
		this.event_Date_Object = event_Date_Object;
	}
	public String getEvent_Address() {
		return event_Address;
	}
	public void setEvent_Address(String event_Address) {
		this.event_Address = event_Address;
	}
	public String getEvent_lat() {
		return event_lat;
	}
	public void setEvent_lat(String event_lat) {
		this.event_lat = event_lat;
	}
	public String getEvent_eventLong() {
		return event_eventLong;
	}
	public void setEvent_eventLong(String event_eventLong) {
		this.event_eventLong = event_eventLong;
	}
	public String getEvent_createdby() {
		return event_createdby;
	}
	public void setEvent_createdby(String event_createdby) {
		this.event_createdby = event_createdby;
	}
	private String event_type;
	
	
	public String getEvent_description() {
		return event_description;
	}
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	private String event_time;
	private String event_owner_name;
	private String event_owner_id;
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getEvent_title() {
		return event_title;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	public String getEvent_owner_name() {
		return event_owner_name;
	}
	public void setEvent_owner_name(String event_owner_name) {
		this.event_owner_name = event_owner_name;
	}
	public String getEvent_owner_id() {
		return event_owner_id;
	}
	public void setEvent_owner_id(String event_owner_id) {
		this.event_owner_id = event_owner_id;
	}
	

}
