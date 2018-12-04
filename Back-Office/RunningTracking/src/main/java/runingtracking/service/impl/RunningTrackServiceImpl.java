package runingtracking.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.repository.RunningTrackRepository;
import runingtracking.service.RunningTrackService;

@Service
public class RunningTrackServiceImpl implements RunningTrackService {

	@Autowired 
	private RunningTrackRepository repository;
	
	@Override
	public List<RunningTrack> findAll() {
		return repository.findAll();
	}

	@Override
	public boolean addPosition(Position p, String id) {
		Optional<RunningTrack> op = repository.findById(id);
		if(!op.isPresent())
			return false;
		
		RunningTrack r = op.get();
		r.addPosition(p);
		return repository.save(r).getPositions().contains(p);
		
	}

	@Override
	public RunningTrack findById(String id) {
		return repository.findById(id).orElse(null);
	}

}
