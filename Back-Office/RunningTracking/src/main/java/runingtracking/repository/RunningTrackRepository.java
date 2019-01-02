package runingtracking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import runingtracking.domain.RunningTrack;
import runingtracking.domain.User;

public interface RunningTrackRepository extends MongoRepository<RunningTrack, String>,
												RunningTrackRepositoryCustom
{	
	@Query("{'user.login':?0}")
	RunningTrack findByLogin(String login);
}
