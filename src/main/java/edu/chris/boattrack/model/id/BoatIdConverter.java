package edu.chris.boattrack.model.id;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import edu.chris.boattrack.model.Boat;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


//@Converter
//public class BoatIdConverter implements AttributeConverter<BoatId, String> { //Boat or BoatId
//
//    @Override
//    public String convertToDatabaseColumn(BoatId id) { // Boat or BoatId?
//        // your conversion to String logic
//    	
//		return "String";
//    } 
//
//    @Override
//    public BoatId convertToEntityAttribute(String id) {
//        // your conversion to YourEmbeddedPrimaryKeyClass logic
//    	
//    	String[] parts = id.split("-");
//    	return new BoatId(parts[0], parts[1]);
//	//	return null;
//    	
//    }
//}


@Converter
class BoatIdConverter implements BackendIdConverter {

  @Override
  public Serializable fromRequestId(String id, Class<?> entityType) {

    // Make sure you validate the input

    String[] parts = id.split("-");
    return new BoatId(parts[0], parts[1]);
  }

  @Override
  public String toRequestId(Serializable source, Class<?> entityType) {

	  BoatId id = (BoatId) source;
    return String.format("%s-%s", id.getClass(),id.getHullNumber());
  }

  @Override
  public boolean supports(Class<?> type) {
    return BoatId.class.equals(type);
  }
}
