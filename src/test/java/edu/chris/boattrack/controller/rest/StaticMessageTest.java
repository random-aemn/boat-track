package edu.chris.boattrack.controller.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.chris.boattrack.BoatTrackApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BoatTrackApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaticMessageTest {
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	/**
	 * Invoke "/bt/v1/boat-test-message" which is processed by BoatController.testMessage
	 * The invoked endpoint should return a static method with a varying time stamp 
	 * This test will verify the msg portion of the JSON payload; it will ignore the time portion of the JSON payload
	 * 
	 * @throws JSONException
	 */
	public void testBasicMessage() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(TestEndpoints.STATIC_EP),
				HttpMethod.GET, entity, String.class);

		String expected = "{\"msg\":\"boattestmsg success\",\"time\":\"2023-05-24T01:09:11.032025800Z\"}";

		JSONAssert.assertEquals(expected, response.getBody().toString(),
				new CustomComparator(JSONCompareMode.STRICT, Customization.customization("time", //  this CustomComparator will cause the "time" field to be ignored
						new ValueMatcher() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true; // in your case just ignore the values and return true
							}
						})));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	
	}
}