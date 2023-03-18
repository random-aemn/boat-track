package edu.chris.boattrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;

public interface BoatJpaRepository extends JpaRepository <Boat, BoatId>{

}

