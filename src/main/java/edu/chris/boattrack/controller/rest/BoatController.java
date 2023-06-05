 package edu.chris.boattrack.controller.rest;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/boatlist")
  List<Boat> getAll() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]
  
//  @GetMapping(path = "/boattestmsg", produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(path="/boattrack/v0.0/boat-test-message")
//  /boattrack/v0.0/boat-test-message
  Map testMessage() {
	  Instant now = Instant.now();
	  Map<String, String> map = new HashMap<String, String>();
	  map.put("msg", "boattestmsg success");
	  map.put("time", now.toString());
	  return map;
  }

  @PostMapping("/boat")
  Boat newBoat(@RequestBody Boat newBoat) {
    return repository.save(newBoat);
  }

  // Single item
  
  @GetMapping("/boat/{id}")
  Boat getOne(@PathVariable BoatId id) {
	  log.info("I got a make believe boat " + id.toString());
	  
    return repository.findById(id).orElseThrow(() -> new BoatNotFoundException(id));
  }

  

  @PutMapping("/boat/{id}")
  Boat replaceBoat(@RequestBody Boat newBoat) {
    return repository.findById(newBoat.getBoatId())
      .map(boat -> {
    	  boat.setApbVersion(newBoat.getApbVersion());
    	  boat.setTiVersion(newBoat.getTiVersion());
        return repository.save(boat);
      })
      .orElseGet(() -> {
        return repository.save(newBoat);
      });
  }

  @DeleteMapping("/boat/{id}")
  void deleteBoat(@PathVariable BoatId id) {
;    repository.deleteById(id);
  }
}