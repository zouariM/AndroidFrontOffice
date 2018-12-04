package runingtracking.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.repository.RunningTrackRepository;
import runingtracking.service.RunningTrackService;
import runingtracking.domain.User;

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
		long nb = repository.pushPosition(p, id);
		
		return nb > 0;
	}

	@Override
	public Optional<RunningTrack> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public String addUser(User user) {
		RunningTrack toAdd = new RunningTrack(user);
		return repository.save(toAdd).getId();
	}

}
