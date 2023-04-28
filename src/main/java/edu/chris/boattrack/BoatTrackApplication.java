package edu.chris.boattrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class BoatTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoatTrackApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

}
