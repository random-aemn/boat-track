package edu.chris.boattrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;

public interface BoatJpaRepository extends JpaRepository <Boat, BoatId>{
	
	Optional<List<Boat>> findByBoatIdShipClass (String shipClass); //implementation provided by JPA Repository
/*
 * The method name, findByDeliveryZip(), makes it clear that this method should find all Order entities by matching their
deliveryZip property with the value passed in as a parameter to the method.
The findByDeliveryZip() method is simple enough, but Spring Data can handle
even more-interesting method names as well. Repository methods are composed of a
verb, an optional subject, the word By, and a predicate. In the case of findByDeliveryZip(), the verb is find and the predicate is DeliveryZip; the subject isn’t specified and is
implied to be an Order
 */
}

