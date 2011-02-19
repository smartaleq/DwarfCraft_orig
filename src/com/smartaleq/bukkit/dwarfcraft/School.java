package com.smartaleq.bukkit.dwarfcraft;

public enum School {
	TOOL_USE (0),
	MINING (1),
	DIGGING (2),
	LUMBERJACK (3),
	FARMING (4),
	DECORATING (5),
	SPECIALIST (6),
	EXPLORATION (7),
	COMBAT (8),
	CIVIC (9);
	
	public int id;

	School(int id){
		this.id = id;
	}
	
	public static School getSchool(String name){
		for(School school:School.values()){
			if(school.toString().equalsIgnoreCase(name)) return school;
		}
		return null;
	}
	public String toString(){
		return this.name();
	}

	public static School getSchool(int id) {
		for ( School school: School.values()){
			if (school.id == id) return school;
		}
		return null;
	}
}
