package runingtracking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import runingtracking.domain.Runway;
import runingtracking.service.RunningTrackService;

@RestController
@RequestMapping("/run")
public class RunwayResource {
	
	@Autowired
	private RunningTrackService service;
	
	@PostMapping("/{id}")
	public ResponseEntity<Runway> add(@RequestBody Runway run, @PathVariable String id){
		Runway res = service.add(run, id);
		if(res == null)
			return ResponseEntity.badRequest().build();
		else
			return ResponseEntity.accepted().body(res);
	}
}
