package edu.chris.boattrack.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import edu.chris.boattrack.BoatTestData;
import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.repository.BoatJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoatIntegrationTest {

	@Autowired
	BoatJpaRepository bjrTwo;

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	BoatTestData tester = new BoatTestData();
	
	
	private static RestTemplate restTemplate = null;

	@BeforeAll

	public static void init() {
		restTemplate = new RestTemplate();
		
	}
	@BeforeEach
	void manufactureData() {
		tester.createTestData();
		//bjrTwo.saveAllAndFlush(tester.createTestData()); // CHECK -- Not sure why all the tests are passing if I only save the test data to the database within this ONE test method
		bjrTwo.saveAll(tester.createTestData());
	}

	/*
	 * Test the ability of the JPA integration to create, save, and retrieve data to/from the database
	 */
	@Test
	void testBoatPersistanceAndRetrieval() {
		//BoatTestData tester = new BoatTestData();

		BoatId boatId = new BoatId("SSN", "700"); 
		Boat localBoat = new Boat(boatId, 150000); // create a boat with information that I KNOW exists

		Optional<Boat> optStoredBoat = bjrTwo.findById(boatId); //optStoredBoat is the boat that exists in the database
		Boat retrievedBoat = null; 

		if (bjrTwo.findById(boatId).isPresent()) {
			retrievedBoat = optStoredBoat.get();

		}
		assertEquals(localBoat, retrievedBoat, "Persisted boat matches original boat definition."); //checking if the boat created locally matches the boat information stored in the database

	}

	/*
	 * Find all the boats within a given ship class given data that is in the database
	 */
	@Test
	void testFindByBoatClassForExistingData() {
//		tester.createTestData();

		Optional<List<Boat>> expectedBoatList = bjrTwo.findByBoatIdShipClass("SSN");

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
//		tester.createTestData();
		int expectedCount = 0;

		Optional<List<Boat>> expectedBoatList = bjrTwo.findByBoatIdShipClass("x");
		if (expectedBoatList.isPresent() ) {
			expectedCount =  expectedBoatList.get().size();
		}
		assertEquals(0,expectedCount);
	}
	
	/*
	 * method that will test the ability to get the displacement of a boat. The specific boat should be found using BoatId
	 */
	@Test
	void testUpdateBoatByBoatId() { 
		
//		tester.createTestData();
		BoatId boatId = new BoatId("SSN", "700");
		Boat localBoat = new Boat(boatId, 0);
		bjrTwo.saveAndFlush(localBoat);
		
		Boat expectedBoat = bjrTwo.findById(boatId).get();
		
		assertEquals(expectedBoat.getDisplacement(), localBoat.getDisplacement());
		
	}
	
	/*
	 * Testing the ability of JPA BoatIntegration to delete a boat that exists in the database
	 */

	@Test
	void testDeleteBoatByBoatId() {
//		tester.createTestData();
		BoatId boatId = new BoatId("SSN", "700");
		bjrTwo.findById(boatId);
		bjrTwo.deleteById(boatId);
		
		Optional<Boat> expectedBoat = bjrTwo.findById(boatId);
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

	
	
	/*
	 * Test the ability of a boat to set the apbVersion, save it to the database, and retrieve it
	 */
	@Test
	void testUpdateApbVersion() {
//		tester.createTestData();
//****The code below causes an error where bytecode enhancement can't be done because of private constructors		
		
//		BoatId boatId = new BoatId("SSN", "700");
//		Boat testBoat = new Boat(boatId, 0);
//		testBoat.setApbVersion(22);
//		bjr.saveAndFlush(testBoat);
//		int testBoatApbVersion = testBoat.getApbVersion();
//		int databaseBoatApbVersion = bjr.getReferenceById(boatId).getApbVersion();//getById is deprecated
//		
//		assertEquals(testBoatApbVersion, databaseBoatApbVersion); 

		BoatId boatId = new BoatId("SSN", "700");
		Boat localBoat = new Boat(boatId, 0);
		localBoat.setApbVersion(22);
		bjrTwo.saveAndFlush(localBoat);
		
		Boat expectedBoat = bjrTwo.findById(boatId).get(); //getById is deprecated, so we use both find AND get methods
		
		assertEquals(expectedBoat.getApbVersion(), localBoat.getApbVersion());
	
	}
	
	
	/*
	 * Test the ability of a boat to set the tiVersion, save it to the database, and retrieve it
	 */
	@Test
	void testUpdateTiVersion() {
//		tester.createTestData();
		BoatId boatId = new BoatId("SSN", "700");
		Boat localBoat = new Boat(boatId, 0);
		localBoat.setTiVersion(87);;
		bjrTwo.saveAndFlush(localBoat);
		
		Boat expectedBoat = bjrTwo.findById(boatId).get(); //getById is deprecated, so we use both find AND get methods
		
		assertEquals(expectedBoat.getTiVersion(), localBoat.getTiVersion());
		
	}
	


}
