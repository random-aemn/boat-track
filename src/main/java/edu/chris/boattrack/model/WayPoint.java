package edu.chris.boattrack.model;

import java.time.Instant;

import org.elasticsearch.geo.geometry.Point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
//import org.elasticsearch.common.geo.GeoPoint;

@Entity
public @Data class WayPoint {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Instant timestamp;
// come up with a class to represent a GeoPoint
	
	
	

}
