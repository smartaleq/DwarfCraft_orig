package com.smartaleq.bukkit.dwarfcraft;

public class Race {
	
	private String name;
	
	
	public Race(String name){
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
