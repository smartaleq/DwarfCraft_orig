package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;
import org.bukkit.Location;

public class DwarfVehicle {
	private Vehicle vehicle;
	private Vector velocity;
	private Location location;
	
	DwarfVehicle (Vehicle vehicle) {
		this.vehicle = vehicle;
		this.velocity = vehicle.getVelocity().clone();
		this.location =vehicle.getLocation().clone();
	}
	
	protected Vehicle getVehicle() { return vehicle; }
	protected Vector getVelocity() { return velocity; }
	protected Location getLocation() { return location; }
	
	protected boolean equals(Vehicle that) {
		if ( that == vehicle )
			return true;
		else
			return false;
	}
	
}