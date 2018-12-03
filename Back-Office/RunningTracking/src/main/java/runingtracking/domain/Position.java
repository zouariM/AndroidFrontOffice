package runingtracking.domain;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Position {
	
	@JsonIgnoreProperties({"x","y"})
	private GeoJsonPoint point;
	private long time;
	
	public Position() {};
	
	public Position(double lat, double lon) {
		this.point = new GeoJsonPoint(lat, lon);
		this.time = System.currentTimeMillis();
	}
	
	public Position(GeoJsonPoint point, long time) {
		super();
		this.point = point;
		this.time = time;
	}

	public GeoJsonPoint getPoint() {
		return point;
	}

	public void setPoint(GeoJsonPoint point) {
		this.point = point;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public String toString() {
		return String.format("Position : { [ x : %s, y : %s ], time : %s }", 
							point.getX(), point.getY(), time);
	}
}
