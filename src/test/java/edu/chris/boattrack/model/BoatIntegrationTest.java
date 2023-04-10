package edu.chris.boattrack.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.repository.BoatJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoatIntegrationTest {

	@Autowired
	BoatJpaRepository bjr;

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate = null;

	private static final int SN_HULL_BASE_VALUE = 700;
	private static final int CG_HULL_BASE_VALUE = 50;
	private static final int DD_HULL_BASE_VALUE = 960;

	/*
	 * method to create 3 SSN subs - Nuclear powered attack submarine
	 */
	private void createSubTestRecords() {
		for (int i = SN_HULL_BASE_VALUE; i < SN_HULL_BASE_VALUE + 3; i++) {
			BoatId boatId = new BoatId("SSN", ((Integer) i).toString());
			Boat boat = new Boat(boatId, 80000 + i * 100);
			bjr.saveAndFlush(boat);
			/*
			 * 80000 + 700*100 = 80000 + 70000 = 150000 150100 150200
			 */
		}
	}

	/*
	 * method to create guided missile cruisers (CGs)
	 */
	private void createCgTestRecords() {
		for (int i = CG_HULL_BASE_VALUE; i < CG_HULL_BASE_VALUE + 2; i++) {
			BoatId boatId = new BoatId("CG", ((Integer) i).toString());
			Boat boat = new Boat(boatId, 50000 + i * 100);
			bjr.saveAndFlush(boat);
			/*
			 * 50000 + 50*100 = 50000 + 5000 = 55000 55100
			 */
		}
	}


	/*
	 * method to create destroyers 
	 */
	private void createDdTestRecords() {
		for (int i = DD_HULL_BASE_VALUE; i < DD_HULL_BASE_VALUE + 1; i++) {
			BoatId boatId = new BoatId("DD", ((Integer) i).toString());
			Boat boat = new Boat(boatId, 10000 + i * 100);
			/*
			 * 10000 + 960*100 = 10000 + 96000 = 106000
			 */
			bjr.saveAndFlush(boat);
		}
	}
	
/*
 * method that rolls up the previous methods to create the SSN, CG, and DD boat(s) for testing purposes
 */
	private void createTestData() {
		createSubTestRecords();
		createCgTestRecords();
		createDdTestRecords();

	}

	@BeforeAll

	public static void init() {
		restTemplate = new RestTemplate();
		
	}

	/*
	 * Test the ability of the JPA integration to create, save, and retrieve data to/from the database
	 */
	@Test
	void testBoatPersistanceAndRetrieval() {
		createTestData();
		BoatId boatId = new BoatId("SSN", "700"); 
		Boat localBoat = new Boat(boatId, 150000); // create a boat with information that I KNOW exists

		Optional<Boat> optStoredBoat = bjr.findById(boatId); //optStoredBoat is the boat that exists in the database
		Boat retrievedBoat = null; 

		if (bjr.findById(boatId).isPresent()) {
			retrievedBoat = optStoredBoat.get();

		}
		assertEquals(localBoat, retrievedBoat, "Persisted boat matches original boat definition."); //checking if the boat created locally matches the boat information stored in the database

	}

	/*
	 * Find all the boats within a given ship class given data that is in the database
	 */
	@Test
	void testFindByBoatClassForExistingData() {
		createTestData();

		Optional<List<Boat>> expectedBoatList = bjr.findByBoatIdShipClass("SSN");

		assertEquals(3, expectedBoatList.get().size(), "Expected count matched received count.");
	}
	
	/*
	 * find all the boats within a given boat class if you pass a ship class that does NOT exist
	 */
	@Test
	void testFindByBoatClassForNoData() {
		/*
		 * What an ugly test!
		 * isPresent returns true because JPA returns an empty array
		 * inside of expectedBoatList.
		 * 
		 * Moral:  isPresent is not sufficient.  Check the size of the list
		 * to determine if data has been returned.
		 */
		createTestData();
		int expectedCount = 0;

		Optional<List<Boat>> expectedBoatList = bjr.findByBoatIdShipClass("x");
		if (expectedBoatList.isPresent() ) {
			expectedCount =  expectedBoatList.get().size();
		}
		assertEquals(0,expectedCount);
	}
	
	/*
	 * method that will test the ability to update the displacement of a boat. The specific boat should be found using BoatId
	 */
	@Test
	void testUpdateBoatByBoatId() { 
		
		createTestData();
		BoatId boatId = new BoatId("SSN", "700");
		Boat localBoat = new Boat(boatId, 0);
		bjr.saveAndFlush(localBoat);
		
		Boat expectedBoat = bjr.findById(boatId).get();
		
		assertEquals(expectedBoat.getDisplacement(), localBoat.getDisplacement());
		
	}
	
	/*
	 * Testing the ability of JPA BoatIntegration to delete a boat that exists in the database
	 */

	@Test
	void testDeleteBoatByBoatId() {
		createTestData();
		BoatId boatId = new BoatId("SSN", "700");
		bjr.findById(boatId);
		bjr.deleteById(boatId);
		
		Optional<Boat> expectedBoat = bjr.findById(boatId);
		assertFalse(expectedBoat.isPresent());
	}
	
	/*
	 * Testing the ability of JPA BoatIntegration to delete a boat that does NOT exist in the database
	 * 
	 *  This cannot be effectively tested as Spring forces a checked exception to an
	 * unchecked exception.  
	 * If one will call the delete function - one MUST check that the boatId exists prior to calling 
	 * the deleteById() function
	 */



}
