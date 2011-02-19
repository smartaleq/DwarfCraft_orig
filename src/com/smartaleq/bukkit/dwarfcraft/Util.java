package com.smartaleq.bukkit.dwarfcraft;

public class Util {
	
	static Dwarf mostRecentAttacker;
	
	static int randomAmount(double input){
		double rand = Math.random();
		if (rand>input%1) return (int) Math.floor(input);
		else return (int) Math.ceil(input);
	}
}
