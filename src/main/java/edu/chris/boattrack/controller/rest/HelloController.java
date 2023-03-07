package edu.chris.boattrack.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
public class HelloController {
 
    @GetMapping("/hello")
    public JSONObject hello() {
       // return "Hello World RESTful with Spring Boot";
    	
JSONObject obj = new JSONObject();
		
		obj.put("name", "Pankaj Kumar");
		obj.put("age", new Integer(32));

		JSONArray cities = new JSONArray();
		cities.add("New York");
		cities.add("Bangalore");
		cities.add("San Francisco");

		obj.put("cities", cities);

    	return obj;
//    	JSONObject new_obj = (JSONObject) arr.get(i);

    }  


@GetMapping("/hw")

public JSONArray helloTwo() {
    // return "Hello World RESTful with Spring Boot";
 	JSONArray outer = new JSONArray();
JSONObject obj = new JSONObject();
obj = new JSONObject();

obj.put("k", "v");
outer.add(obj);
obj = new JSONObject();
		obj.put("msg", "hello");
		outer.add(obj);
	
 	return outer;
// 	JSONObject new_obj = (JSONObject) arr.get(i);

 }  
}