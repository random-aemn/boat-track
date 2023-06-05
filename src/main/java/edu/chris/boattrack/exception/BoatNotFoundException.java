package edu.chris.boattrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


import edu.chris.boattrack.model.id.BoatId;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BoatNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	
	public BoatNotFoundException(BoatId id) {
	    super("Could not find boat " + id);
	  }
	}