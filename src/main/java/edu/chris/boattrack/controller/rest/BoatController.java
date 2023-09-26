 package edu.chris.boattrack.controller.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.chris.boattrack.model.Boat;
import edu.chris.boattrack.model.id.BoatId;
import edu.chris.boattrack.repository.BoatJpaRepository;
import edu.chris.boattrack.exception.BoatNotFoundException;

@RestController
class BoatController {
	
	private final Log log = LogFactory.getLog(getClass());

	private final BoatJpaRepository repository;

	BoatController(BoatJpaRepository repository) {
		this.repository = repository;
	}

/*
 * Get all boats in the repository
 */
  @GetMapping("/boatlist")
  List<Boat> getAll() {
    return repository.findAll();
  }
  
  /*
   * Get a specific boat from the repository using the boatId. 
   * Example: http://localhost:8080/bt/v1/poc/xx-00
   */
  @GetMapping("/boat/{id}")
  Boat getOne(@PathVariable BoatId id) {
	  log.info("I got a make believe boat " + id.toString());
	  
    return repository.findById(id).orElseThrow(() -> new BoatNotFoundException(id));
  }
  
  /*
   * Method just returns the time to validate that 
   * the @GetMapping function works as intended.
   */
//  @GetMapping(path = "/boattestmsg", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(path="/boat-test-message")
  Map testMessage() {
	  Instant now = Instant.now();
	  Map<String, String> map = new HashMap<String, String>();
	  map.put("msg", "boattestmsg success");
	  map.put("time", now.toString());
	  return map;
  }
  
  /*
   * This GetMapping works and will take the strings from the URL, parse them into 
   * variables, and allow us to build a BoatId from those strings
   */
   @GetMapping(value = "/poc/{firstKey}-{secondKey}", produces = APPLICATION_JSON_VALUE)
  // public String fred (@PathVariable String firstKey, @PathVariable String secondKey) {
   public BoatId fred (@PathVariable String firstKey, @PathVariable String secondKey) {
 	  log.info("initiated getMapping");
 	 log.info("firstKey is: " + firstKey);
 	 log.info("secondKey is: " + secondKey);
 	 BoatId boatId = new BoatId(firstKey, secondKey);
 	 //displacement, apbVersion, tiVersion
 	 
 	 log.info("boatId is: " + boatId);
 	  //return String.format("firstKey = %s secondKey =%s", firstKey, secondKey);
 	  return boatId;
   }
   
   @GetMapping(value = "/bst/", produces = APPLICATION_JSON_VALUE)
   public Boat bst () {
 	  log.info("initiated getMapping");
 	 BoatId boatId = new BoatId("xx", "00");
 	 log.info("boatId is: " + boatId);
 	 Boat boat = new Boat(boatId, 99);
 	 log.info("new BOAT is: " + boat);
 	 log.info("boat displacement is: " + boat.getDisplacement());
 	 boat.setApbVersion(666);
 	 log.info("boat apbVersion is: " + boat.getApbVersion());
 	 boat.setTiVersion(777);
 	 log.info("boat tiVersion is: " + boat.getTiVersion());
 	 //displacement, apbVersion, tiVersion
 	 
 	 return boat;
   }
   
  
  /*
   * HTTP Put AND Post methods. @RequestMapping annotation
   * narrows down the HTTP methods for which this method will be invoked. 
   * 
   * if I perform a PUT request and the boat does not exist, the boat will be created
   * and added to the database - is that desired behavior?
   */

  @RequestMapping(path = "/bp", method={RequestMethod.POST,RequestMethod.PUT})
  ResponseEntity<Boat> newBoatPost(@RequestBody Boat newBoat) {
	  String existence = (newBoat == null)? "i am null" : "I am not null";
	  log.info(existence);
	  
	  /*
	   *Spring checks for a empty payload and will automatically generate a 400 error
	   *therefore error trapping isn't required to check for a null boat 
	   */
	  
	  log.info("Hi from BoatPost");
	  log.info("string newBoat is: " + newBoat);
	  log.info("BoatId is: " + newBoat.getBoatId());
	  log.info("Displacement is: " + newBoat.getDisplacement());
	  log.info("TiVersion is: " + newBoat.getTiVersion());
	  log.info("apbVersion is: " + newBoat.getApbVersion());
	 
	  repository.save(newBoat);
	 return new ResponseEntity<Boat>(newBoat, HttpStatus.CREATED);
  }

/*
 * Spring's native behavior uses the Post method to EITHER create a record
 * in the database or update a record that currently exists.  The Put method will
 * therefore just call the Post method due to Spring's built in behavior
 */

/*
 * The database is case sensitive - if I have a boatId of CC-100 and cc-100
 * they are unique boats in the database.  The answer is to apply a toUpper function in the java code
 * before the SQL is issued
 */
  @DeleteMapping("/boat/{id}") 
  void deleteBoat(@PathVariable BoatId id) {
    repository.deleteById(id);
  }
  
  
}