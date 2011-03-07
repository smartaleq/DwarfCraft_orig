package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;
import org.bukkit.Location;

public class DwarfVehicle {
	private Vehicle vehicle;
	private Vector velocity;
	private Location location;

	protected DwarfVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.velocity = vehicle.getVelocity().clone();
		this.location = vehicle.getLocation().clone();
	}

	@Override
	public boolean equals(Object that) {
		if (that instanceof Vehicle) {
			if (that == vehicle)
				return true;
		}
		return false;
	}

	private Location getLocation() {
		return location;
	}

	private Vehicle getVehicle() {
		return vehicle;
	}

	private Vector getVelocity() {
		return velocity;
	}

}