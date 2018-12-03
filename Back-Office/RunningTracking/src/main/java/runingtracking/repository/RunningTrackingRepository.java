package runingtracking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import runingtracking.domain.RunningTracking;

@RepositoryRestResource(path="runningTracking")
public interface RunningTrackingRepository extends MongoRepository<RunningTracking, String> {
	
}
