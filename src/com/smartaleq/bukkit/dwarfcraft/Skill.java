package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.inventory.ItemStack;

public class Skill {
	
	public int id;
	public String displayName;
	public School school;
	public int level;
	public Effect[] effects;
	public ItemStack[] trainingCost;
	public double noviceIncrement;
	public double masterMultiplier;
		
	public Skill(
			int id, 
			String displayName, 
			School school,
			int level,
			Effect[] effects,
			ItemStack[] trainingCost,
			double noviceIncrement,
			double masterMultiplier
				) 
	{
		this.id = id;
		this.displayName = displayName;
		this.school = school;
		this.level = level;
		this.effects = effects;
		this.trainingCost = trainingCost;
		this.noviceIncrement = noviceIncrement;
		this.masterMultiplier = masterMultiplier;
	}
	
	public String toString(){
		return displayName.toUpperCase().replaceAll(" ", "_");
	}

	public double baseTrainingMultiplier() {
		double multiplier = Math.ceil(Math.min(((double)level + 1), 5) * noviceIncrement);
		if (level >= 5){
			multiplier *= Math.pow(masterMultiplier, level-5);
		}
		return multiplier;
	}
	
}
