package runingtracking.service;

import java.util.List;
import java.util.Optional;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.domain.User;

public interface RunningTrackService {
	public List<RunningTrack> findAll();
	public Optional<RunningTrack> addPosition(Position p, String id);
	public Optional<RunningTrack> findById(String id);
	public String addUser(User user);
}
