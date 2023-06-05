package rest.integration;

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
public class FredTest {
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testBasicMessage() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/boattrack/v0.0/boat-test-message"),
				HttpMethod.GET, entity, String.class);

//        String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
//        String expected = "{msg=boattestmsg success, time=2023-05-24T15:52:28.291356800Z}";
		String expected = "{\"msg\":\"boattestmsg success\",\"time\":\"2023-05-24T01:09:11.032025800Z\"}";

		JSONAssert.assertEquals(expected, response.getBody().toString(),
				new CustomComparator(JSONCompareMode.STRICT, Customization.customization("time", // json path you want
																									// to customize
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