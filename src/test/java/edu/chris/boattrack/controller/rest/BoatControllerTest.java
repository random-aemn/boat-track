package edu.chris.boattrack.controller.rest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.chris.boattrack.BoatTestData;

class BoatControllerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BoatTestData tester = new BoatTestData();
		tester.createTestData();
	}

	@Test
	void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	void testNewBoat() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOne() {
		fail("Not yet implemented");
	}

	@Test
	void testReplaceBoat() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteBoat() {
		fail("Not yet implemented");
	}

}
