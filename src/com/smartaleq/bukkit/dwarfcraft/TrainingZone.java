package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.World;
import org.bukkit.util.Vector;

public class TrainingZone {
	
	public Vector corner1;
	public Vector corner2;
	public School school;
	public World world;
	public String name;
	
	TrainingZone(
			Vector corner1,
			Vector corner2,
			School school,
			World world,
			String name){
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.school = school;
		this.world = world;
		this.name = name;
	}
		
}
