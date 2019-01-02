package runingtracking.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PostMapping("/login")
	@JsonView(value=Views.UserView.class)
	public ResponseEntity<RunningTrack> login(@RequestBody User user) {
		RunningTrack run = service.login(user);
		if(run != null)
			return new ResponseEntity<RunningTrack>(run, HttpStatus.OK);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@JsonView(value=Views.UserView.class)
	public ResponseEntity<RunningTrack> add(@RequestBody User toAdd) {
		logger.info("Add user : " + toAdd.toString());
		RunningTrack user = service.addUser(toAdd);
		
		if(user != null)
			return new ResponseEntity<RunningTrack>(user, HttpStatus.CREATED);
		else
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
	}
	
	@GetMapping("/{login}")
	@JsonView(value=Views.UserView.class)
	public ResponseEntity<RunningTrack> findByLogin(@PathVariable String login){
		RunningTrack user = service.findUserByLogin(login);
		
		if(user == null)
			return new ResponseEntity<RunningTrack>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<RunningTrack>(user, HttpStatus.OK);
		
	}

}
