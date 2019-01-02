package runingtracking.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import runingtracking.rest.Views;

@JsonInclude(value=Include.NON_NULL)
@JsonView(value=Views.Runways.class)
public class Position {
	
	private double longitude;
	private double latitude;
	private long time;
	
	public Position() {};
	
	public Position(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
		this.time = System.currentTimeMillis();
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public String toString() {
		return String.format("Position : { [ latitude : %s, longitude : %s ], time : %s }", 
							latitude, longitude, time);
	}
}
