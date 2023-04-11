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
	     * Test to validate ability of boat to set displacement cannot be run because displacement is final
	     * setters for variables with "final" modifier cannot be used
	     **/
	    
	    /*
	    @Test 
	    @DisplayName("Successfully set boat displacement")
	    void testSetDisplacement(){
	    	Boat testBoat = new Boat();   	
	    }
	    */

	    //Test ability for a boat to set apbVersion
	    @Test
	    @DisplayName("Verify setting of apbVersion")
	    void testSetApbVersion() {	    	
	    	boat.setApbVersion(12);
	    	assertEquals(12, boat.getApbVersion(), "ship apbVersion should be 12"); 
	
	    }
	    
	    //Test ability for a boat to set tiVersion
	    @Test
	    @DisplayName("verify setting of tiVersion")
	    void testSetTiVersion() {
	    	boat.setTiVersion(22);
	    	assertEquals(22, boat.getTiVersion(), "ship tiVersion should be 22");
	    }

//	    

//	    @RepeatedTest(5)                                    
//	    @DisplayName("Ensure correct handling of zero")
//	    void testMultiplyWithZero() {
//	        assertEquals(0, calculator.multiply(0, 5), "Multiple with zero should be zero");
//	        assertEquals(0, calculator.multiply(5, 0), "Multiple with zero should be zero");
//	    }
	}



