package runingtracking.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import runingtracking.domain.Position;
import runingtracking.domain.RunningTrack;
import runingtracking.repository.RunningTrackRepositoryCustom;

public class RunningTrackRepositoryCustomImpl implements RunningTrackRepositoryCustom {

	@Autowired
	private MongoTemplate template;
	
	@Override
	public long pushPosition(Position p, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update();
		
		update.push("positions", p);
		UpdateResult res = template.updateFirst(query, update, RunningTrack.class);
		return res.getModifiedCount();
		
	}

}
