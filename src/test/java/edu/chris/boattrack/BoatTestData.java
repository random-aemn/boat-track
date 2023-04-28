package edu.chris.boattrack;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.repository.BoatJpaRepository;

@Component
public class BoatTestData {
	
	@Autowired
	BoatJpaRepository bjr;
	
	
	private static final int SN_HULL_BASE_VALUE = 700;
	private static final int CG_HULL_BASE_VALUE = 50;
	private static final int DD_HULL_BASE_VALUE = 960;
	private static final int totalShipClasses = 6;

	/*
	 * method to create 3 SSN subs - Nuclear powered attack submarine
	 */
	
	//return a collection of Boats - then that will get passed to the BoatIntegrationTest -> and BoatIntegrationTest will push the data to the database
	private Boat[] createSubTestRecords() {
		Boat[] boatArray = new Boat[3];
		int counter = 0;
		for (int i = SN_HULL_BASE_VALUE; i < SN_HULL_BASE_VALUE + 3; i++) {
			BoatId boatId = new BoatId("SSN", ((Integer) i).toString());
			Boat boat = new Boat(boatId, 80000 + i * 100);
			boatArray[counter] = boat;
			counter++; 
			//bjr.saveAndFlush(boat);
			
			/*
			 * 80000 + 700*100 = 80000 + 70000 = 150000 150100 150200
			 */
		}
		return boatArray;
	}

	/*
	 * method to create guided missile cruisers (CGs)
	 */
	private Boat[] createCgTestRecords() {
		Boat[] boatArray = new Boat[2];
		int counter = 0;

		for (int i = CG_HULL_BASE_VALUE; i < CG_HULL_BASE_VALUE + 2; i++) {
			BoatId boatId = new BoatId("CG", ((Integer) i).toString());
			Boat boat = new Boat(boatId, 50000 + i * 100);
			//bjr.saveAndFlush(boat);
			boatArray[counter] =  boat;
			counter++;
			/*
			 * 50000 + 50*100 = 50000 + 5000 = 55000 55100
			 */
		}
		return boatArray;
	}


	/*
	 * method to create destroyers 
	 */
	private Boat[] createDdTestRecords() {
		Boat[] boatArray = new Boat[1];
		int counter = 0;
		for (int i = DD_HULL_BASE_VALUE; i < DD_HULL_BASE_VALUE + 1; i++) {
			BoatId boatId = new BoatId("DD", ((Integer) i).toString());
			Boat boat = new Boat(boatId, 10000 + i * 100);
			//bjr.saveAndFlush(boat);
			boatArray[counter] = boat;
			counter++;
			/*
			 * 10000 + 960*100 = 10000 + 96000 = 106000
			 */
		}
		return boatArray;
	}
	
/*
 * method that rolls up the previous methods to create the SSN, CG, and DD boat(s) for testing purposes
 */
	public  ArrayList<Boat> createTestData() {
		ArrayList <Boat> boatList = new ArrayList<>();
		//createSubTestRecords();
		for(Boat element : createSubTestRecords()) {
			boatList.add(element);
		}

		//createCgTestRecords();
		for(Boat element : createCgTestRecords()) {
			boatList.add(element);
		}
		
		//createDdTestRecords();
		for(Boat element : createDdTestRecords()) {
			boatList.add(element);
		}
		//System.out.println(boatList);
		return boatList;
	}
}
