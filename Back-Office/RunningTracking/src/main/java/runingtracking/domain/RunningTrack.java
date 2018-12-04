package runingtracking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import runingtracking.rest.Views;

@Document(collection="runningTracks")
@JsonInclude(value=Include.NON_NULL)
public class RunningTrack {
	
	@Id
	@JsonView(value=Views.UserView.class)
	private String id;
	
	@JsonView(value=Views.UserView.class)
	private User user;
	
	private List<Position> positions;
	
	public RunningTrack() {}

	public RunningTrack(User u) {
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
	
	public String toString() {
		String positionsStr = this.positions.parallelStream().map(Position::toString).collect(Collectors.joining("\n"));
		
		return String.format("RunningTrack { id: %s, User : %s } \n Tracks: ***** \n %s", 
							  id,user.toString(), positionsStr);
	}
}
