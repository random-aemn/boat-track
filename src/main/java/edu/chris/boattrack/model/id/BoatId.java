package edu.chris.boattrack.model.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class BoatId implements Serializable {
	@Column(nullable=false)
    private String shipClass;
	
	@Column(nullable=false)
    private String hullNumber;


    public BoatId(String shipClass, String hullNumber) {
        this.shipClass = shipClass;
        this.hullNumber = hullNumber;
    }


    /*
     *  getters, equals() and hashCode() methods created by Lombok @Data annotation
     */
}
