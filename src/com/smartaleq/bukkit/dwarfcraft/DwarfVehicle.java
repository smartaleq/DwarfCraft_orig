package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;
import org.bukkit.Location;

public class DwarfVehicle {
	Vehicle vehicle;
	Vector velocity;
	Location location;
	
	DwarfVehicle (Vehicle vehicle) {
		this.vehicle = vehicle;
		this.velocity = vehicle.getVelocity().clone();
		this.location =vehicle.getLocation().clone();
	}
	
}