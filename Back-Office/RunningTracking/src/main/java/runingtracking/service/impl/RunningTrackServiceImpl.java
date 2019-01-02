package runingtracking.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.domain.Runway;
import runingtracking.repository.RunningTrackRepository;
import runingtracking.service.RunningTrackService;
import runingtracking.service.utils.HashProvider;
import runingtracking.domain.User;

@Service
public class RunningTrackServiceImpl implements RunningTrackService {

	@Autowired 
	private RunningTrackRepository repository;
	@Autowired
	private HashProvider hashProvider;
	private Logger log = LoggerFactory.getLogger(RunningTrackServiceImpl.class);
	
	@Override
	public List<RunningTrack> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public boolean addPosition(Position p, String id) {
		Optional<RunningTrack> op = repository.findById(id);
		if(op.get() == null)
			return false;
		
		RunningTrack r = op.get();
		Runway runway = r.getLast();
		
		if(runway != null && runway.getFinishTime() == null)
			runway.addPosition(p);
		else {
			Runway newRun = new Runway();
			newRun.setStartTime(p.getTime());
			newRun.addPosition(p);
			r.addRunway(newRun);
		}
			
		
		return repository.save(r) != null;
	}

	@Override
	public Optional<List<Runway>> findById(String id) {
		Optional<RunningTrack> op = repository.findById(id);
		List<Runway> l = null;
		
		if(op.isPresent())
			l = op.get().getRunways();
		
		return Optional.ofNullable(l);
	}

	@Override
	public RunningTrack addUser(User user) {
		if(user.getLogin() == null || user.getPassword() == null)
			return null;
		
		RunningTrack r = findUserByLogin(user.getLogin());
		if(r != null)
			return null;
		
		user.setPassword(hashProvider.hash(user.getPassword()));
		RunningTrack toAdd = new RunningTrack(user);
		return repository.save(toAdd);
	}

	@Override
	@Transactional
	public Runway finishRun(Position p, String id, long finishTime) {
		Optional<RunningTrack> op = repository.findById(id);
		if(!op.isPresent())
			return null;
		
		RunningTrack run = op.get();
		if(run.getLast() == null)
			return null;
		
		run.getLast().setFinishTime(finishTime);
		run.getLast().addPosition(p);
		run = repository.save(run);
		
		return run.getLast();
	}

	@Override
	public RunningTrack findUserByLogin(String login) {
		return repository.findByLogin(login);
	}

	@Override
	public RunningTrack login(User user) {
		RunningTrack run = findUserByLogin(user.getLogin());
		log.info(user.getPassword());
		if(run != null) {
			
			if(hashProvider.check(user.getPassword(), run.getUser().getPassword()))
				return run;
			else
				return null;
		}
		return null;
	}

	@Override
	public Runway add(Runway run, String id) {
		Optional<RunningTrack> op = repository.findById(id);
		if(!op.isPresent())
			return null;
		
		RunningTrack r = op.get();
		
		r.addRunway(run);
		r = repository.save(r);
		
		if(r.getLast() != run)
			throw new IllegalStateException("Data not consistent");
		
		return r.getLast();
	}


}
