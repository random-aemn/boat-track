package edu.chris.boattrack;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class BoatTrackApplication {
	

	public static void main(String[] args) {
		Log log = LogFactory.getLog("BoatTrackApplication");
		SpringApplication.run(BoatTrackApplication.class, args);
		log.info("Spring version is: " + SpringVersion.getVersion());
	}

}
