package edu.chris.boattrack.repository;

import org.springframework.data.repository.CrudRepository;

import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;

public interface BoatJpaRepository extends CrudRepository <Boat, BoatId>{

}

