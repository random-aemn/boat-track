package edu.chris.boattrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;

public interface BoatJpaRepository extends JpaRepository <Boat, BoatId>{
	
	Optional<List<Boat>> findByBoatIdShipClass (String shipClass);

}

