package edu.chris.boattrack.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import edu.chris.boattrack.BoatTestData;
import edu.chris.boattrack.BoatTrackApplication;
import edu.chris.boattrack.model.Boat;
@SpringBootTest (classes = BoatTrackApplication.class, 
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoatControllerTest {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private BoatController controller;
	
	@Value(value="${local.server.port}")
	private int port;

	//@Autowired
	private TestRestTemplate trt;
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
	


	@Test
	public void getNonExistentBoat() throws Exception {
		trt = new TestRestTemplate();
		//http://localhost:8080/boattrack/v0.0/boat/cg-52
		log.info("port is: " + port);
		ResponseEntity <String> response = 
this.trt.getForEntity("http://localhost:" + port + TestEndpoints.NO_SUCH_BOAT_EP , String.class);
		String res=(response==null) ? "Response is null" : response.getBody(); //if response is null, return null; else String res gets response
		log.info(res);
		fail("Not yet implemented");
		/*
		 * assert that I expect a 404 error - compare against the return code
		 * 
		 */
		int expectedRC = 404;
		int actualRC = response.getStatusCode().value();
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BoatTestData tester = new BoatTestData();
		tester.createTestData();

	}
	
	/*
	 * THE NEXT TEST TO WRITE
	 */
//	@Test
//	void testNewBoat() { 
//		fail("Not yet implemented");
//	}
	
//	@Test
//	void testGetAll() {
//		
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetOne() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testReplaceBoat() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteBoat() {
//		fail("Not yet implemented");
//	}

}
