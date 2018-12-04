package runingtracking.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import runingtracking.domain.User;
import runingtracking.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@GetMapping
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@PostMapping
	public User add(@RequestBody User user) {
		return userService.add(user);
	}
	
	@GetMapping("/{id}")
	public User find(@PathVariable String id) {
		return userService.findById(id);
	}
}
