package runingtracking.rest;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import runingtracking.domain.RunningTrack;
import runingtracking.domain.User;
import runingtracking.service.RunningTrackService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private RunningTrackService service;
	
	private Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@GetMapping
	@JsonView(value=Views.UserView.class)
	public List<RunningTrack> findAll(){
		return service.findAll();
	}
	
	@PostMapping
	@JsonView(value=Views.UserView.class)
	public ResponseEntity<Entry<String, String>> add(@RequestBody User toAdd) {
		logger.info("Add user : " + toAdd.toString());
		String id = service.addUser(toAdd);
		
		Entry<String, String> res = new AbstractMap.SimpleEntry<String, String>("id", id);
		return new ResponseEntity<Entry<String, String>>(res, HttpStatus.CREATED);
	}
	
}
