package edu.chris.boattrack.controller.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;
import java.io.Writer;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;
@SpringBootTest
@AutoConfigureMockMvc
class BoatControllerIntegrationTest {
	
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private MockMvc mock; 
	
	@Test
	void testTestMessage() throws Exception {
		/* Create a Jackson JSON ObjectMapper
		 * to allow serialization of a JSON string into a class
		 */
		ObjectMapper mapper = new ObjectMapper();


		/* In order to hang on the the result of the "get" method,
		 * declare a variable of type MvcResult
		 */
		MvcResult result;
		
		/*
		 * execute a get request 
		 * check for http status code of 200
		 * save http response in result
		 * 
		 */
		result = mock.perform(get("/boat-test-message"))
		.andExpect(status().isOk())
		.andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		log.info("*****");
		log.info(response.getContentAsString());
		log.info("*****");
		/*
		 * serialize JSON response body as a TestMessageResponse
		 * verify content of "msg" field
		 * verify existence of time field 
		 */
		TestMessageResponse trm = mapper.readValue(response.getContentAsString(), TestMessageResponse.class);
		
		assert(trm.getMsg().equals("boattestmsg success"));
		assert(trm.getTime()!= null);

	}
	
	@Test
	void testGetOneExpectNoDataFound() throws Exception {
		

		/* 
		 * No explicit JSON ObjectMapper should be needed because this "get"
		 * method will return the JSON representation of a model that has been
		 * designed in the directory structure
		 */
		
		
		/*
		 * execute a get request 
		 * check for http status code of 404
		 * Commented lines out below  but retained for reference
		 */
		
		//MvcResult result = 
		mock.perform(get("/boat/xx-00"))
		.andExpect(status().isNotFound());
	//	.andReturn();
		
	//	assertEquals(404, result.getResponse().getStatus());		
	
	}

	
	@Test
	void testGetAllExpectNoDataFound() throws Exception {
		
		MvcResult result = mock.perform(get("/boatlist"))
		.andExpect(status().isOk())
		.andReturn();
		
		log.info(result.getResponse().getContentAsString());
		assertEquals("[]", result.getResponse().getContentAsString());

		//assertEquals(404, result.getResponse().getStatus());
	}

	
	

	@Test
	void testNewBoatPost() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		
		
		/* In order to hang on the the result of the "post" method,
		 * declare a variable of type MvcResult
		 */
		MvcResult result;
		
		BoatId boatId = new BoatId("CC", "4");
		Boat boat = new Boat(boatId,444);
		boat.setApbVersion(4);
		boat.setTiVersion(44);
		
		String boatJson = mapper.writeValueAsString(boat);
				
		/*
		 * Boat boat = new Boat()
		 */
		//result = 
		mock.perform(post("/bp")
		.content(boatJson)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.andExpect(status().isCreated()));
		//.andReturn());
		
		
//        mock.perform(post("/api/employee")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJson(emp)))
//                .andExpect(status().isOk());
		fail("Not yet implemented");
	}
	
	@Test
	void testGetOne() {
		fail("Not yet implemented");
	}
	
	@Test
	void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteBoat() {
		fail("Not yet implemented");
	}

}
