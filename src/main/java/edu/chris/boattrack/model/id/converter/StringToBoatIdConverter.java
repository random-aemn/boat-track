package edu.chris.boattrack.model.id.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.chris.boattrack.model.id.BoatId;

//public class BoatIdToStringConverter implements Converter<BoatId, String> {
//	
//	public String convert(BoatId source) {
//		return String.valueOf(source);
//	}
//}
@Component
public class StringToBoatIdConverter implements Converter<String, BoatId> {
	
	public BoatId convert(String source) {
		 String[] parts = source.split("-");
		    return new BoatId(parts[0], parts[1]);
	
		
	}
}