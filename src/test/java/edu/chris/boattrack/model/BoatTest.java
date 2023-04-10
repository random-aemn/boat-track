package edu.chris.boattrack.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.chris.boattrack.model.id.BoatId;

import static org.junit.jupiter.api.Assertions.assertEquals;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.DisplayName;
	import org.junit.jupiter.api.RepeatedTest;

	class BoatTest {


	    Boat boat;
	    BoatId boatId;

	    @BeforeEach                                         
	    void setUp() {
	    	boatId = new BoatId("SSN", "500"); 	//SSN-500
	    	boat = new Boat(boatId, 80000);		// displacement 80,000

	    }

	    @Test                                               
	    @DisplayName("Verify boat creation")   
	    void testInstantiation() {
	    	String expectedShipClass = boat.getBoatId().getShipClass();
	    	String expectedHullNumber = boat.getBoatId().getHullNumber();
	    	String expectedId = expectedShipClass + "-" + expectedHullNumber;
	        assertEquals("SSN-500", expectedId,     
	                "Ship id should be SSN-500");  
	    }
	    
	    /*
	     * Test ability to change displacement
	     */
//	    @Test                                               
//	    @DisplayName("Displacement change test")   
//	    void testChangeDisplacement() {
//	    }
//	    

//	    @RepeatedTest(5)                                    
//	    @DisplayName("Ensure correct handling of zero")
//	    void testMultiplyWithZero() {
//	        assertEquals(0, calculator.multiply(0, 5), "Multiple with zero should be zero");
//	        assertEquals(0, calculator.multiply(5, 0), "Multiple with zero should be zero");
//	    }
	}



