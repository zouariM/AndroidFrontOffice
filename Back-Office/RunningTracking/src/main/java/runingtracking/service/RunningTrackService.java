package runingtracking.service;

import java.util.List;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;

public interface RunningTrackService {
	public List<RunningTrack> findAll();
	public boolean addPosition(Position p, String id);
	public RunningTrack findById(String id);
}
