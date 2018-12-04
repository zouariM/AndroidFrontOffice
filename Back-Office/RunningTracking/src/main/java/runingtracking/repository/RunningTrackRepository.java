package runingtracking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import runingtracking.domain.RunningTrack;

public interface RunningTrackRepository extends MongoRepository<RunningTrack, String> {
	
}
