package runingtracking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import runingtracking.rest.Views;

@JsonInclude(value=Include.NON_NULL)
@JsonView(value=Views.Runways.class)
public class Runway {
	
	private List<Position> positions;
	private Long startTime;
	private Long finishTime;
	
	public Runway() {
		super();
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}

	public void addPosition(Position p) {
		if(positions == null) 
			positions = new ArrayList<>();
		
		
		this.positions.add(p);
	}
	
	@Override
	public String toString() {
		String str = this.positions.parallelStream().map(Position::toString).collect(Collectors.joining("\n"));
		
		return String.format("{startTime: %s, finishTime: %s} Positions:\n", 
				startTime, finishTime, str);
	}
}
