package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Skill implements Cloneable{
	
	public final int id;
	public final String displayName;
	public final School school;
	public int level;
	public final List <Effect> effects;
	public final List <ItemStack> trainingCost;
	public final double noviceIncrement;
	public final double masterMultiplier;
		
	public Skill(
			int id, 
			String displayName, 
			School school,
			int level,
			List <Effect> effects,
			List <ItemStack> trainingCost,
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
	
	/**
	 * My attempt at making a cloneable class.
	 * 
	 * Known issue: it does not clone the effects table or itemStack table. This is not a problem because effects are 100% final, and ItemStack is never modified.
	 */
	public Skill clone(){
		Skill newSkill = new Skill(this.id, this.displayName, this.school, this.level, this.effects, this.trainingCost, this.noviceIncrement, this.masterMultiplier);
		return newSkill;
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
