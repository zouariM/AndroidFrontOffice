package runingtracking.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import runingtracking.domain.RunningTrack;
import runingtracking.domain.User;
import runingtracking.repository.RunningTrackRepository;
import runingtracking.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RunningTrackRepository repository;
	
	@Override
	public List<User> findAll() {
		return repository.findAll().parallelStream().map(elt -> elt.getUser()).collect(Collectors.toList());
	}

	@Override
	public User add(User user) {
		RunningTrack track = new RunningTrack(user);
		return repository.save(track).getUser();
	}

	@Override
	public User findById(String id) {
		Optional<RunningTrack> elt = repository.findById(id);
		if(elt.isPresent())
			return elt.get().getUser();
		else
			return null;
	}

}
