package com.tcs.poc.service;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.poc.controller.DiaryManagerController;
import com.tcs.poc.dto.Event;
import com.tcs.poc.dto.Resource;

@RestController
@CrossOrigin
public class DiaryManagerService {

	
	@Autowired
	@Qualifier("DiaryManagerController")
	protected DiaryManagerController DiaryManagerController;
	

	@GetMapping(value = "/v1/test", produces = {"application/JSON"})
	public ResponseEntity<String> testService () {

		return ResponseEntity.status(HttpStatus.OK).body("sample Service");
	}

	@GetMapping(value = "/v1/getResource", produces = {"application/JSON"})
	public ResponseEntity<Resource> getResource (@Valid @RequestParam("fileID") String fileID) {

		Resource resource = DiaryManagerController.getResourceforID(fileID);
		
		if (null != resource && null != resource.getId()) {
			resource.setEvents(DiaryManagerController.getEvents(resource.getId()));
		}
		
		HttpStatus httpStatus = (null != resource)? HttpStatus.OK: HttpStatus.NO_CONTENT;
		
		return ResponseEntity.status(httpStatus).body(resource);
	}
	
	@GetMapping(value = "/v1/getEvents", produces = {"application/JSON"})
	public ResponseEntity<Event[]> getEvents (@Valid @RequestParam("fileID") String fileID) {

		Event[] events = DiaryManagerController.getEvents(fileID);
		
		HttpStatus httpStatus = (null != events)? HttpStatus.OK: HttpStatus.NO_CONTENT;
		
		return ResponseEntity.status(httpStatus).body(events);
	}

}
