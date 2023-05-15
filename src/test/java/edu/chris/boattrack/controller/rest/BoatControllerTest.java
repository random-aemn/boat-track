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
import edu.chris.boattrack.model.Boat;
@SpringBootTest
class BoatControllerTest {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private BoatController controller;
	
	@Value(value="${local.server.port}")
	private int port;

	//@Autowired
	private TestRestTemplate restTemplate;
	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
	


	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		restTemplate = new TestRestTemplate();
		//http://localhost:8080/boattrack/v0.0/boat/cg-52
		log.info("port is: " + port);
		String response = 
this.restTemplate.getForObject("http://localhost:" + port + "/boattrack/v0.0/boat/SSN-700" , String.class);
		String res=(response==null) ? "Response is null" : response;
		log.info(res);
		fail("Not yet implemented");
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BoatTestData tester = new BoatTestData();
		tester.createTestData();

	}

//	@Test
//	void testGetAll() {
//		BoatController controller = new BoatController(null);
//		
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testNewBoat() {
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
