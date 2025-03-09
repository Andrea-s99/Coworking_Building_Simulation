package coworking.agents;

import org.locationtech.jts.geom.Coordinate;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Service {

	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	
	private  Coordinate location;
	
	private String name; //Nome del servizio
	
	private String city; //Nome della città
	
	private int id;
	
	private double earn;
	
	public Service(int id, String name, String name_city, Coordinate coord) {
		this.id = id;
		this.name = name;
		this.city = name_city;
		this.location = coord;
		this.earn = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public String getCity() {
		return city;
	}
	
	public  Coordinate getLocation() {
        return location;
    }
	
	public double getEarnings() {
		return this.earn;
	}
	
	public void setEarnings(double new_earn) {
		this.earn = new_earn;
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		
	    setEarnings(getEarnings() + 0.05);
	
	}
	
}
