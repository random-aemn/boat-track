package edu.chris.boattrack.exception;

import edu.chris.boattrack.model.id.BoatId;

public class BoatNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoatNotFoundException(BoatId id) {
	    super("Could not find boat " + id);
	  }
	}