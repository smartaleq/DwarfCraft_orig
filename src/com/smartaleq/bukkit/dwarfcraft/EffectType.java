package com.smartaleq.bukkit.dwarfcraft;

public enum EffectType {
//IMPLEMENTATION PRIORITY ORDER
				//TODO UI and Commands
	BLOCKDROP, 	// TODO onBlockBreak
	MOBDROP,	// TODO onEntityDeath
	SWORDDURABILITY,// TODO onEntityDamageByEntity
	PVPDAMAGE, 	// TODO onEntityDamageByEntity
	PVEDAMAGE, 	// TODO onEntityDamageByEntity
	EXPLOSIONDAMAGE,//TODO onEntityDamage
	FIREDAMAGE, //TODO onEntityDamage
	FALLDAMAGE, // TODO onEntityDamageByBlock
	TOOLDURABILITY, // TODO onBlockBreak
	EAT,	 	// TODO onPlayerItem
	CRAFT, 		// TODO ??
	ARMORHIT, 	// TODO onEntityDamage
	PLOW,		// TODO onBlockRightClick
	DIGTIME,	// TODO ??
	BOWATTACK, 	// TODO onEntityDamageByProjectile
	VEHICLEDROP,// TODO onVehicleDamage - destroyed
	VEHICLEMOVE,// TODO onVehicleMove
	CITIZENBLOCKS,// TODO towny implementation
	TOWNBLOCKS, // TODO towny implementation
	SPECIAL; 	// TODO forges 
	
	public static EffectType getEffectType(String name){
		for(EffectType effectType:EffectType.values()){
			if(effectType.toString().equalsIgnoreCase(name)) return effectType;
		}
		return null;
	}
	
}
