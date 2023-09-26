package edu.chris.boattrack.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.model.id.converter.StringToBoatIdConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity

/*
 * @NoArgsConstructor annotation at the class level. 
 * JPA requires that entities have a no-arguments constructor, so Lombok’s 
 * @NoArgsConstructor does that for you. 
 * You don’t want to be able to use it, though, so you make it 
 * private by setting the access attribute to AccessLevel.PRIVATE. 
 * And because there are final properties that must be set, 
 * you also set the force attribute to true, which results in the 
 * Lombok-generated constructor setting them to null.

	You also add a @RequiredArgsConstructor. The @Data implicitly adds a required 
	arguments constructor, but when a @NoArgsConstructor is used, 
	that constructor gets removed. 
	An explicit @RequiredArgsConstructor ensures that you’ll still 
	have a required arguments constructor in addition to the 
	private no-arguments constructor.
 */
public class Boat {
	
	//@Convert(converter = BoatIdConverter.class)
	@EmbeddedId
	//@JsonDeserialize(converter= StringToBoatIdConverter.class) Trying to figure out how to get JSON to map
	private final BoatId boatId;
	private final int displacement;
	private int apbVersion;
	private int tiVersion;
	

	

	//Added a constructor that takes two arguments
	
//	public Boat(BoatId boatId, int displacement) {
//		//@EmbeddedId
//		this.boatId = boatId;
//		this.displacement = displacement;
//	}
}

