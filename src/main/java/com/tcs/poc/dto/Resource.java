package com.tcs.poc.dto;

public class Resource {

	private String id;
	
	private String title;
	
	private String eventColor;
	
	private BusinessHours businessHours;
	
	private Event[] events;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}

	public BusinessHours getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(BusinessHours businessHours) {
		this.businessHours = businessHours;
	}

	public Event[] getEvents() {
		return events;
	}

	public void setEvents(Event[] events) {
		this.events = events;
	}
	
}
