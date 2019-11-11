package com.tcs.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tcs.azure.ms.colleague.ColleagueMSconfig;
import com.tcs.poc.dto.BusinessHours;
import com.tcs.poc.dto.Event;
import com.tcs.poc.dto.Resource;
import com.tcs.poc.hibernate.GenericDBController;
import com.tcs.poc.pojo.Events;

@Component ("DiaryManagerController")
public class DiaryManagerController {

	@Autowired
	@Qualifier("DBController")
	GenericDBController dbController;
	
	@Autowired
	ColleagueMSconfig config;

	public Resource getResourceforID (String fileID) {

		com.tcs.poc.pojo.Resource resource = new com.tcs.poc.pojo.Resource() ;
		resource.setFileID(fileID);
		List<com.tcs.poc.pojo.Resource> resources = null;

		try {
			resources = dbController.retrieveData(resource, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Resource retVal = mapToSingleResourceDTO (resources);
		return retVal;
	}

	public Event[] getEvents (String fileID) {

		Event retval[] = null;

		Events events = new Events();
		events.setFileID(fileID);

		List<Events> list = null;

		try {
			list = dbController.retrieveData(events, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		retval = mapToEventsDTO(list);

		return retval;
	}

	private Event[] mapToEventsDTO(List<Events> list) {
		Event[] retVal;
		if (null != list && list.size() > 0) {
			retVal= new Event[list.size()];
		}else {
			return null;
		}
		
		for (int i = 0; i < list.size(); i++) {
			retVal[i] = mapToEventDTO(list.get(i));
		}

		return retVal;
	}

	private Event mapToEventDTO(Events events) {
		
		Event event = new Event();
		
		event.setId(events.getId());
		event.setResourceId(events.getFileID());
		event.setStart(events.getStart());
		event.setEnd(events.getEnd());
		event.setTitle(events.getTitle());
		
		return event;
	}

	private Resource mapToSingleResourceDTO(List<com.tcs.poc.pojo.Resource> resources) {

		Resource retVal = new Resource();

		if (null != resources && resources.size() > 0) {

			retVal.setId(resources.get(0).getFileID());
			retVal.setTitle(resources.get(0).getName());
			retVal.setEventColor(resources.get(0).getEventColor());

			BusinessHours businessHours = new BusinessHours();
			businessHours.setStart(resources.get(0).getBhStart());
			businessHours.setEnd(resources.get(0).getBhEnd());

			retVal.setBusinessHours(businessHours);

		}
		return retVal;
	}

}
