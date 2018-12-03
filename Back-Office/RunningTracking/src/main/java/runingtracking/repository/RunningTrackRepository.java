package runingtracking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import runingtracking.domain.RunningTrack;

@RepositoryRestResource(path="runningTrack")
public interface RunningTrackRepository extends MongoRepository<RunningTrack, String> {
	
}
