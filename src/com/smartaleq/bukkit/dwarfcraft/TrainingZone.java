package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.World;
import org.bukkit.util.Vector;

public class TrainingZone {
	
	public Vector lowCorner;
	public Vector highCorner;
	public School school;
	public World world;
	public String name;
	
	TrainingZone(
			Vector lowCorner,
			Vector highCorner,
			School school,
			World world,
			String name){
		this.lowCorner = lowCorner;
		this.highCorner = highCorner;
		this.school = school;
		this.world = world;
		this.name = name;
	}
		
}
