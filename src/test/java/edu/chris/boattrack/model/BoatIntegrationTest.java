package edu.chris.boattrack.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.repository.BoatJpaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoatIntegrationTest {
	
	@Autowired 
	BoatJpaRepository bjr;
	
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";


    private static RestTemplate restTemplate = null;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }
	@Test
	void testBoatPersistanceAndRetrieval() {
		boolean testResult = false;
		BoatId boatId = new BoatId("SSN", "701");
		Boat boat = new Boat(boatId, 80000);
		bjr.save(boat);
		
		Optional<Boat> optShadow = bjr.findById(boatId);
		Boat shadow = null;
		
		if (bjr.findById(boatId).isPresent()) {
			shadow = optShadow.get();
			if (shadow.getBoatId() == boatId) {
				testResult = true;
			}
		}
		assertEquals(boat, shadow, "Persisted boat matches original boat definition.");

	}

}
