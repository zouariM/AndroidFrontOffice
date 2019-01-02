package runingtracking.service;

import java.util.List;
import java.util.Optional;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.domain.Runway;
import runingtracking.domain.User;

public interface RunningTrackService {
	public List<RunningTrack> findAll();
	public boolean addPosition(Position p, String id);
	public Optional<List<Runway>> findById(String id);
	public RunningTrack addUser(User user);
	public Runway finishRun(Position p, String  id, long finishTime);
	public RunningTrack findUserByLogin(String login);
	public RunningTrack login(User user);
	public Runway add(Runway run, String id);

}
