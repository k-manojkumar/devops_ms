package com.tcs.poc.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCES")
public class Resource {

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "ID")
	private Integer id;
	
	@Column (name = "FILE_ID")
	private String fileID;
	
	@Column (name = "NAME")
	private String name;
	
	@Column(name = "BH_START")
	private String bhStart;
	
	@Column(name = "BH_END")
	private String bhEnd;
	
	@Column(name = "EVENT_COLOR")
	private String eventColor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBhStart() {
		return bhStart;
	}

	public void setBhStart(String bhStart) {
		this.bhStart = bhStart;
	}

	public String getBhEnd() {
		return bhEnd;
	}

	public void setBhEnd(String bhEnd) {
		this.bhEnd = bhEnd;
	}

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}

	
	
}
