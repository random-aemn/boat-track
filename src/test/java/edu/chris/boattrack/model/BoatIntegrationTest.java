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
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoatIntegrationTest {
	
	@Autowired 
	BoatJpaRepository bjr;
	
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";


    private static RestTemplate restTemplate = null;
    
    private static final int SN_HULL_BASE_VALUE=700;
    private static final int CG_HULL_BASE_VALUE=50;
    private static final int DD_HULL_BASE_VALUE=960;
    
    private  void createSubTestRecords() {
    	for (int i=SN_HULL_BASE_VALUE; i<SN_HULL_BASE_VALUE+3; i++) {
    		BoatId boatId = new BoatId("SSN", ((Integer)i).toString());
    		Boat boat = new Boat(boatId, 80000 + i*100);
    		bjr.save(boat);
    		/*
    		 * 80000 + 700*100 = 80000 + 70000 = 150000
    		 * 150100
    		 * 150200 
    		 */
    	}
    }
    
    private  void createCgTestRecords() {
    	for (int i=CG_HULL_BASE_VALUE; i<CG_HULL_BASE_VALUE+2; i++) {
    		BoatId boatId = new BoatId("CG", ((Integer)i).toString());
    		Boat boat = new Boat(boatId, 50000 + i*100);
    		bjr.save(boat);
    		/*
    		 * 50000 + 50*100 = 50000 + 5000 = 55000 
    		 * 55100
    		 */
    	}
    }
    
    private  void createDdTestRecords() {
    	for (int i=DD_HULL_BASE_VALUE; i<DD_HULL_BASE_VALUE+1; i++) {
    		BoatId boatId = new BoatId("DD", ((Integer)i).toString());
    		Boat boat = new Boat(boatId, 10000 + i*100);
    		/*
    		 * 10000 + 960*100 = 10000 + 96000 = 106000 
    		 */
    		bjr.save(boat);
    	}
    }
    
    private void createTestData() {
    	createSubTestRecords();
    	createCgTestRecords();
    	createDdTestRecords();
    	
    }

    @BeforeAll
    
    public static void init() {
        restTemplate = new RestTemplate();
    }
    
	@Test
	@Transactional
	void testBoatPersistanceAndRetrieval() {
		createTestData();
		BoatId boatId = new BoatId("SSN", "700");
		Boat boat = new Boat(boatId, 150000);
		
		Optional<Boat> optShadow = bjr.findById(boatId);
		Boat shadow = null;
		
		if (bjr.findById(boatId).isPresent()) {
			shadow = optShadow.get();

		}
		assertEquals(boat, shadow, "Persisted boat matches original boat definition.");

	}

}
