package edu.chris.boattrack.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import edu.chris.boattrack.BoatTestData;
import edu.chris.boattrack.BoatTrackApplication;
import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;

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
		//http://localhost:8080/bt/v1/boat/cg-52
		log.info("port is: " + port);
		ResponseEntity <String> response = 
this.trt.getForEntity("http://localhost:" + port + TestEndpoints.NO_SUCH_BOAT_EP , String.class);
		String res=(response==null) ? "Response is null" : response.getBody(); //if response is null, return null; else String res gets response
		log.info(res);
		
		int expectedRC = 404;
		int actualRC = response.getStatusCode().value();
		assertEquals(expectedRC, actualRC);
	}
	/*
	 * Create test data for remaining tests
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BoatTestData tester = new BoatTestData();
		tester.createTestData();

	}
	

	@Test	
	void testNewBoat() throws URISyntaxException { 
		trt = new TestRestTemplate();
		final String baseUrl = "http://localhost:" + port + "/bt/v1/"; // base URL to issue the post
		URI uri = new URI(baseUrl);
		BoatId boatId = new BoatId("CGC", "777"); //create a boatId 
		Boat newBoat = new Boat(boatId, 777000); //instantiate a boat to post
		
		HttpHeaders headers = new HttpHeaders(); // Don't understand what I'm doing in this or the following line or what the arguments denote in headers.set
		headers.set("X-COM-PERSIST", "true");
		
		HttpEntity<Boat> request = new HttpEntity<>(newBoat, headers); //creates new HttpEntity with a body and headers
		log.info("The HttpEntity request is: " + request);
		
		
		ResponseEntity<String> result = this.trt.postForEntity(uri, request, String.class);
		log.info(result);
		log.info(result.getStatusCode().value());
		assertEquals(201, result.getStatusCode().value());
		
		//fail("Not yet implemented");
	}
	
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
