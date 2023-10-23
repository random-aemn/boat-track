package edu.chris.boattrack.controller.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.repository.BoatJpaRepository;
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "edu.chris.boattrack.controller.rest")
class BoatControllerIntegrationTest {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private BoatJpaRepository repository;


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
		repository.deleteAll();

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
		//use the repository method to clear the database
		repository.deleteAll();

		MvcResult result = mock.perform(get("/boatlist"))
		.andExpect(status().isOk())
		.andReturn();
		
		/*
		 * method will return an empty array
		 * test is checking for the JSON representation of an empty array
		 * which is a pair of brackets
		 */
		log.info(result.getResponse().getContentAsString());
		assertEquals("[]", result.getResponse().getContentAsString());
		

	}

	
	

	@Test
	void testNewBoatPost() throws Exception {
		repository.deleteAll();

		//user mapper to convert the boat object to JSON representation
		ObjectMapper mapper = new ObjectMapper();
		
		
		/* In order to hang on the the result of the "post" method,
		 * declare a variable of type MvcResult
		 */
		MvcResult result;
		
		/*
		 * creating the elements of the boat that will be posted
		 */
		BoatId boatId = new BoatId("CC", "4");
		Boat boat = new Boat(boatId,444);
		boat.setApbVersion(4);
		boat.setTiVersion(44);
		
		/*
		 * creating JSON string representation of the boat object
		 */
		String boatJson = mapper.writeValueAsString(boat);
		
		log.info("I'm in testNewBoatPost");
		log.info(boatJson);
		log.info("*****");

		/*
		 * using MockMVC to perform a post request
		 * passing the JSON string
		 * posting and accepting JSON
		 * expecting a successful post - which is a status code of 201 for "is created"
		 * return the MVC result into the variable 'result' to be interrogated for success/failure
		 */
	result = mock.perform(post("/bp")
		.content(boatJson)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andReturn();
		
	

	log.info(result.getResponse().getStatus());
/*
 * expected is an int representing the expected HTTP status code
 * asserting that I expect a 201 return code and using the native methods of "result" to validate
 */
	int expected = 201;
	assertEquals(expected, result.getResponse().getStatus());
		
	}
	/*
	 * method within the test class to create a boat
	 */
	private Boat createBoat(BoatId id, int displacement) {
		BoatId boatId = id;
		int boatDisplacement = displacement;
		Boat boat = new Boat(boatId, boatDisplacement);
		boat.setApbVersion(4);
		boat.setTiVersion(44);
		return boat;
	}
	
	@Test
	void testGetOne() throws Exception {

		
		/*
		 * execute a get request 
		 * check for http status code of 200
		 * Commented lines out below  but retained for reference
		 */
		repository.deleteAll();
		//createBoat();
		BoatId boatId = new BoatId("CC", "4");
		repository.save(createBoat(boatId, 500));
		
		//MvcResult result = 	
		mock.perform(get("/boat/CC-4"))
		.andExpect(status().isOk());
		//fail("Not yet implemented");
	}
	
	@Test
	void testGetAll() throws Exception {
		//clear the database
		repository.deleteAll();
		
		//create two new boats
		BoatId boatId = new BoatId("CC", "4");
		BoatId boatId2 = new BoatId("Dad", "1");
		Boat boat1 = createBoat(boatId2, 1978);
		Boat boat2 = createBoat(boatId, 2009);
		
		//save the created boats to the database
		repository.save(boat1);
		repository.save(boat2);
		
		//perform a mocked Get request and return the value
		MvcResult result = mock.perform(get("/boatlist"))
		.andExpect(status().isOk())
		.andReturn();
		
/*
 * pass the result of mock.perfom to a string
 */
		String jsonString = result.getResponse().getContentAsString();
		System.out.println(result.getResponse().getContentAsString());
		
		/*
		 * create an ObjectMapper
		 * create a typeFactory
		 * use those objects to create a list of boats
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		TypeFactory typeFactory = objectMapper.getTypeFactory();
		List<Boat> boatList = objectMapper.readValue(jsonString, 
				typeFactory.constructCollectionType(List.class, Boat.class));
		
		//System.out.println(boatList.size());
		int expected = 2;
		int actual = boatList.size();
		assertEquals(expected, actual);

	}

	@Test
	void testDeleteBoat() throws Exception {
		/*
		 * clear all
		 * add a boat using repository
		 * check the boat was added successfully - using repository
		 * try deleting a boat using this method
		 */
		repository.deleteAll();
		BoatId boatId = new BoatId("CC", "4");
		Boat boat = createBoat(boatId, 2009);
		repository.save(boat);
		/*
		 * findById -> if the 
		 */
		Optional<Boat> testBoat = repository.findById(boatId);
		log.info(testBoat);
		
		//MvcResult result = 
		mock.perform(delete("/boat/CC-4"));
		
		assertFalse(repository.findById(boatId).isPresent());

	}

}
