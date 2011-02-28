package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.Material;

public class Skill implements Cloneable{
	
	public final int id;
	public final String displayName;
	public int level;
	public final List <Effect> effects;
	public final Material TrainingItem1Mat;
	public final double TrainingItem1BaseCost;
	public final int TrainingItem1MaxAmount;
	public final Material TrainingItem2Mat;
	public final double TrainingItem2BaseCost;
	public final int TrainingItem2MaxAmount;
	public final Material TrainingItem3Mat;
	public final double TrainingItem3BaseCost;
	public final int TrainingItem3MaxAmount;
	private Material trainerHeldMaterial;
		
	public Skill(
			int id, 
			String displayName,
			int level,
			List <Effect> effects,
			Material 		TrainingItem1Mat 		,
			double 		TrainingItem1BaseCost 	,
			int 		TrainingItem1MaxAmount 	,
			Material 		TrainingItem2Mat 		,
			double 		TrainingItem2BaseCost 	,
			int 		TrainingItem2MaxAmount 	,
			Material 		TrainingItem3Mat  		,
			double 		TrainingItem3BaseCost 	,
			int			TrainingItem3MaxAmount	,
			Material trainerHeldMaterial ) 
	{
		this.id = id;
		this.displayName = displayName;
		this.TrainingItem1Mat  = TrainingItem1Mat ;
		this.TrainingItem2Mat  = TrainingItem2Mat ;
		this.TrainingItem3Mat = TrainingItem3Mat ;
		this.TrainingItem1BaseCost = TrainingItem1BaseCost;
		this.TrainingItem2BaseCost = TrainingItem2BaseCost;
		this.TrainingItem3BaseCost = TrainingItem3BaseCost;
		this.TrainingItem1MaxAmount = TrainingItem1MaxAmount;
		this.TrainingItem2MaxAmount = TrainingItem2MaxAmount;
		this.TrainingItem3MaxAmount = TrainingItem3MaxAmount;

		this.level = level;
		this.effects = effects;
		this.trainerHeldMaterial = trainerHeldMaterial;
	}
	
	
	
	/**
	 * My attempt at making a cloneable class.
	 * 
	 * Known issue: it does not clone the effects table or itemStack table. This is not a problem because effects are 100% final, and ItemStack is never modified.
	 */
	public Skill clone(){
		Skill newSkill = new Skill(this.id, this.displayName, this.level, this.effects,this.TrainingItem1Mat,this.TrainingItem1BaseCost ,this.TrainingItem1MaxAmount,this.TrainingItem2Mat ,this.TrainingItem2BaseCost,this.TrainingItem2MaxAmount ,this.TrainingItem3Mat  ,this.TrainingItem3BaseCost   ,this.TrainingItem3MaxAmount, this.trainerHeldMaterial);
		return newSkill;
	}
	
	public String toString(){
		return displayName.toUpperCase().replaceAll(" ", "_");
	}
	
	public Material getTrainerHeldMaterial() { return this.trainerHeldMaterial; } 
	public int getId() { return this.id; }
}
