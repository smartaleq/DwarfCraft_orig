package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.Material;


final class Effect {
	
	final private int id;
	
	//effect value descriptors
	final private double baseValue;
	final private double levelUpMultiplier;
	final private double noviceLevelUpMultiplier;
	final private double minValue;
	final private double maxValue;
	final private boolean floorResult;
	final private boolean hasException;
	final private double exceptionLow;
	final private double exceptionHigh;
	final private double exceptionValue;

	final private int elfEffectLevel;
	final private EffectType effectType;
	final private int initiatorId;
	final private int outputId;
	final private boolean toolRequired;
	final private int[] tools;
	
	protected int getElfEffectLevel() { return elfEffectLevel; }
	protected EffectType getEffectType() { return effectType; }
	protected int getInitiatorId() { return initiatorId; }
	protected int getOutputId() { return outputId; }
	protected boolean getToolRequired() { return toolRequired; }
	protected int[] getTools() { return tools; }
	
	protected int getId() { return id; }
	
	protected Effect(
			int id,
			double baseValue,
			double levelUpMultiplier,
			double noviceLevelUpMultiplier,
			double minValue,
			double maxValue,
			boolean floorResult,
			boolean hasException,
			double exceptionLow,
			double exceptionHigh,
			double exceptionValue,
			int elfEffectLevel,
			EffectType effectType,
			int initiatorId,
			int outputId,
			boolean toolRequired,
			int[] tools
			) 
	{
		this.id = id;
		this.baseValue = baseValue;
		this.levelUpMultiplier = levelUpMultiplier;
		this.noviceLevelUpMultiplier = noviceLevelUpMultiplier;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.floorResult = floorResult;
		this.hasException = hasException;
		this.exceptionLow = exceptionLow;
		this.exceptionHigh = exceptionHigh;
		this.exceptionValue = exceptionValue;
		this.elfEffectLevel = elfEffectLevel;
		this.effectType = effectType;
		this.initiatorId = initiatorId;
		this.outputId = outputId;
		this.toolRequired = toolRequired;
		this.tools = tools;
	}
	
	@Override
	public String toString(){
		return Integer.toString(id);
	}
	
	/**
	 * Returns an effect Amount for a particular Dwarf
	 * @param dwarf
	 * @return
	 */
	protected double getEffectAmount(Dwarf dwarf){
		double effectAmount = baseValue;
		int skillLevel;
		skillLevel = dwarf.skillLevel(this.id/10);
		if (dwarf.isElf() || skillLevel == -1) skillLevel = this.elfEffectLevel;
		effectAmount += skillLevel * levelUpMultiplier;
		effectAmount += Math.min(skillLevel, 5) * noviceLevelUpMultiplier;
		if (floorResult) effectAmount = Math.floor(effectAmount);
		effectAmount = Math.min(effectAmount, maxValue);
		effectAmount = Math.max(effectAmount, minValue);
		if (hasException && skillLevel <= exceptionHigh && skillLevel >= exceptionLow) effectAmount = exceptionValue;
		if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: GetEffectAmount Id: " + id +
				" level checked: "+ skillLevel+
				" base: " + baseValue +
				" LevelUp multi:  " + levelUpMultiplier+ 
				" Novice:  " + noviceLevelUpMultiplier+ 
				" Max:  " + maxValue+ 
				" Min: " + minValue+ 
				" Exception: " +hasException + 
				" ExcLow: " + exceptionLow+ 
				" ExcHigh: " + exceptionHigh+ 
				" Excvalue:  " + exceptionValue);
		return effectAmount;
	}
	
	private String effectLevelColor(int skillLevel){
		if (skillLevel > elfEffectLevel) return "&a";
		else if (skillLevel == elfEffectLevel) return "&e";
		else return "&c";
	}
	
	/**
	 * Tool to string parser for effect descriptions
	 * @return
	 */
	private String toolType(){
		for (int toolId: tools){
			if (toolId == 267) return "swords";
			if (toolId == 292) return "hoes";
			if (toolId == 258) return "axes";
			if (toolId == 270) return "pickaxes";			
			if (toolId == 257) return "most picks";
			if (toolId == 278) return "high picks";
			if (toolId == 256) return "shovels";
		}
		return "any tool";
	}
		
	/**
	 * General description of a benefit including minimum and maximum benefit
	 * @return
	 */
	protected String describeGeneral(){
		String description;
		String initiator = Material.getMaterial(initiatorId).toString();
		if (initiator.equalsIgnoreCase("AIR")) initiator = "None";
		String output = Material.getMaterial(outputId).toString();
		if (output.equalsIgnoreCase("AIR")) output = "None";
		double effectAmountLow = getEffectAmount(0);
		double effectAmountHigh = getEffectAmount(30);
		double elfAmount = getEffectAmount(elfEffectLevel);
		String toolType = toolType();
		description = String.format(
			"Effect Block Trigger: %s Block Output: %s . " +
			"Effect value ranges from %.2f - %.2f for levels 0 to 30. " +
			"Elves have the effect %.2f , as if they were level %d . " +
			"Tools affected: %s. " + (toolRequired ? "Tool needed." : "Tool not needed."), 
			initiator, output,
			effectAmountLow, effectAmountHigh,
			elfAmount, elfEffectLevel,
			toolType);
		
		return description;
	}
	
/**
 * Used for getting the effect amount at a particular skill level.
 * Where possible use getEffectAmount(Dwarf), which checks for Dwarf vs. Elf.
 */
	private double getEffectAmount(int skillLevel) {
		double effectAmount = baseValue;		
		effectAmount += skillLevel * levelUpMultiplier;
		effectAmount += Math.min(skillLevel, 5) * noviceLevelUpMultiplier;
		if (floorResult) effectAmount = Math.floor(effectAmount);
		effectAmount = Math.min(effectAmount, maxValue);
		effectAmount = Math.max(effectAmount, minValue);
		if (hasException && skillLevel <= exceptionHigh && skillLevel >= exceptionLow) effectAmount = exceptionValue;
		if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: GetEffectAmount Id: " + id +
				" base: " + baseValue +
				" LevelUp multi:  " + levelUpMultiplier+ 
				" Novice:  " + noviceLevelUpMultiplier+ 
				" Max:  " + maxValue+ 
				" Min: " + minValue+ 
				" Exception: " +hasException + 
				" ExcLow: " + exceptionLow+ 
				" ExcHigh: " + exceptionHigh+ 
				" Excvalue:  " + exceptionValue);
		return effectAmount;
	}

	/**
	 * Description of a skills effect at a given level
	 * @param dwarf
	 * @return
	 */
	protected String describeLevel(Dwarf dwarf){
		if (dwarf == null) return "Failed"; //TODO add failure code
		String description = "no skill description";
		// Variables used in skill descriptions
		String initiator = Material.getMaterial(initiatorId).toString();
		String output = Material.getMaterial(outputId).toString();
		double effectAmount = getEffectAmount(dwarf);
		double elfAmount = getEffectAmount(elfEffectLevel);
		boolean moreThanOne = (effectAmount > 1);
		String effectLevelColor = effectLevelColor(dwarf.getSkill(this).getLevel());
		String toolType = toolType();
		
		if (effectType.equals(EffectType.ARMORHIT)){
			if (moreThanOne){description = String.format("&6When attacked your &2%s&6 takes %s%.2f &cMore &6damage",
						initiator,
						effectLevelColor,
						effectAmount);
			}
			else {description = String.format("&6When attacked your &2%s&6 takes %s%.2f &aLess &6 damage",
					initiator,
					effectLevelColor,
					effectAmount);
			}
		}
		else if (effectType.equals(EffectType.BLOCKDROP)){
			description = String.format("&6Break a &2%s &6 and %s%.2f &2%s&6 are created",
				initiator,
				effectLevelColor,
				effectAmount,
				output );
		}
		else if (effectType.equals(EffectType.BOWATTACK)){
			description = String.format("&6Your Arrows do %s%.0f &6hp damage (half hearts)",
				effectLevelColor,
				effectAmount);
		}
		else if (effectType.equals(EffectType.CITIZENBLOCKS)){
			description = String.format("&6You contribute %s%.2f &6to max town size",
				effectLevelColor,
				effectAmount );
		}
		else if (effectType.equals(EffectType.CRAFT)){
			description = String.format("&6You craft %s%.0f &2%s instead of &e%.0f",
				effectLevelColor,
				effectAmount,
				output,
				elfAmount);
		}
		else if (effectType.equals(EffectType.DIGTIME)){
			description = String.format("&a%.0f%%&6 of the time &2%s &6break &2%s &6instantly ",
					effectAmount*100,
					toolType,
					Material.getMaterial(this.initiatorId).toString()
					);
		}
		else if (effectType.equals(EffectType.TOOLDURABILITY)){
			description = String.format("&6Using &2%s &6removes about %s%.2f &6durability",
					toolType,
					effectLevelColor,
					effectAmount);
		}
		else if (effectType.equals(EffectType.EAT)){
			description = String.format("&6You gain %s%.2f hearts (not &e%.2f) when you eat &2%s",
					effectLevelColor,
					effectAmount,
					elfAmount,
					initiator);
		}
		else if (effectType.equals(EffectType.EXPLOSIONDAMAGE)){
			if (moreThanOne){description = String.format("&6You take %s%d%% more &6damage from explosions",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
			}
			else {description = String.format("&6You take %s%d%% less &6damage from explosions",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
			}
		}
		else if (effectType.equals(EffectType.FALLDAMAGE)){
			if (moreThanOne){description = String.format("&6You take %s%d%% more &6damage from falling",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
			}
			else {description = String.format("&6You take %s%d%% less &6damage from falling",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
			}
		}
		else if (effectType.equals(EffectType.FALLTHRESHOLD)){
			description = String.format("&6You ",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
		}
		else if (effectType.equals(EffectType.FIREDAMAGE)){
			if (moreThanOne){description = String.format("&6You take %s%d%% more &6damage from fire",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
			}
			else {description = String.format("&6You take %s%d%% less &6damage from fire",
					effectLevelColor,
					(int)(effectAmount*100 - 100));
			}
		}
		else if (effectType.equals(EffectType.MOBDROP)){
			description = String.format("&6When you kill a mob that drops &2%s you get approx %s%.2f&6",
					output,
					effectLevelColor,
					effectAmount,
					output );
			//special zombie exception
			if(id == 850||id == 851){
				description = String.format("&6When you kill a zombie you get approx %s%.2f &2%s",
						effectLevelColor,
						effectAmount,
						output );
			}
		}
		else if (effectType.equals(EffectType.PLOW)){
			description = String.format("&6You gain %s%.2f seeds instead of &e%.2f when you plow grass",
					effectLevelColor,
					effectAmount,
					elfAmount);
		}
		else if (effectType.equals(EffectType.PVEDAMAGE)){
			description = String.format("&6You do %s%d&6%% of normal &2%s damage when fighting mobs",
				effectLevelColor,
				(int)(effectAmount*100),
				toolType);
		}
		else if (effectType.equals(EffectType.PVPDAMAGE)){
			description = String.format("&6You do %s%d&6%% of normal &2%s damage when fighting players",
				effectLevelColor,
				(int)(effectAmount*100),
				toolType);
		}				
		
		else if (effectType.equals(EffectType.TOWNBLOCKS)){
			description = String.format("&6As a town mayor your town can claim no more than %s%.2f &6blocks, or the sum of your residents' citizen skills",
				effectLevelColor,
				effectAmount );
		}
		else if (effectType.equals(EffectType.VEHICLEDROP)){
			description = String.format("&6When you break a boat &6approx. %s%s%.2f &2%s&6 are created",
				initiator,
				effectLevelColor,
				effectAmount,
				output );
		}
		else if (effectType.equals(EffectType.VEHICLEMOVE)){
			description = String.format("&6Your boat travels %s%d%% faster than normal",
				effectLevelColor,
				(int)(effectAmount*100 - 100));
		}
		else if (effectType.equals(EffectType.SPECIAL)){
			description = String.format("special");
		}
		else {description = "This Effect is not yet implemented: " + effectType.toString();}
		
		return description;
	}

	
}
