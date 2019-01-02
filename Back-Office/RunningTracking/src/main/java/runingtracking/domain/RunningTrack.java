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
	@JsonView(value= {Views.UserView.class, Views.Runways.class})
	private String id;
	
	@JsonView(value=Views.UserView.class)
	private User user;
	
	@JsonView(value=Views.Runways.class)
	private List<Runway> runways;
	
	public RunningTrack() {
		runways = new ArrayList<>();
	}

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
	
	public List<Runway> getRunways() {
		if(runways == null)
			runways = new ArrayList<>();
		return runways;
	}

	public Runway getLast() {
		if(runways.isEmpty())
			return null;
		else
			return runways.get(runways.size() - 1);
	}
	
	public void setRunways(List<Runway> runways) {
		this.runways = runways;
	}

	public void addRunway(Runway r) {
		if(runways == null)
			runways = new ArrayList<>();
		
		this.runways.add(r);
	}
	
	public String toString() {
		String str = this.runways.parallelStream().map(Runway::toString).collect(Collectors.joining("\n"));
		
		return String.format("RunningTrack { id: %s, User : %s } \n Tracks: ***** \n %s", 
							  id,user.toString(), str);
	}
}
