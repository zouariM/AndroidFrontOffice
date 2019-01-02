package runingtracking.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.domain.Runway;
import runingtracking.service.RunningTrackService;

@RestController
@RequestMapping("/runningTracks")
public class RunningTrackResource {
	
	@Autowired
	private RunningTrackService runningTrackService;
	private Logger logger = LoggerFactory.getLogger(RunningTrackResource.class);
	
	@GetMapping
	public List<RunningTrack> findAll(){
		return runningTrackService.findAll();
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> addPosition(@RequestBody Position p, @PathVariable String id) {
		logger.info("Add position : " +p.toString());
		boolean res = runningTrackService.addPosition(p, id);
		
		if(res)
			return ResponseEntity.accepted().build();
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/finish/{id}/{finishTime}")
	public ResponseEntity<Runway> finishRun(@RequestBody Position p, @PathVariable String id, @PathVariable long finishTime){
		Runway res = runningTrackService.finishRun(p, id, finishTime);
		
		if(res != null)
			return ResponseEntity.accepted().body(res);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
	@JsonView(value=Views.Runways.class)
	public ResponseEntity<List<Runway>> findById(@PathVariable String id) {
		Optional<List<Runway>> op = runningTrackService.findById(id);
		if(op.isPresent())
			return ResponseEntity.ok(op.get());
		
		else 
			return ResponseEntity.notFound().build();
	}
}
