package runingtracking.repository;

import runingtracking.domain.Position;

public interface RunningTrackRepositoryCustom {
	public long pushPosition(Position p, String id);
}
