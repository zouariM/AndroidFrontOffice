package runingtracking.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection="runningTracking")
@JsonInclude(value=Include.NON_NULL)
public class RunningTracking {
	
	@Id
	private String id;
	private User user;
	private List<Position> positions;
	
	public RunningTracking() {}

	public RunningTracking(User u) {
		this.user = u;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	public void addPosition(Position p) {
		if(positions == null)
			positions = new ArrayList<>();
		
		this.positions.add(p);
	}
}
