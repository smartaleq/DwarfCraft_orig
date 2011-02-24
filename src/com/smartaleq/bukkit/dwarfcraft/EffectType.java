package com.smartaleq.bukkit.dwarfcraft;

public enum EffectType {
//IMPLEMENTATION PRIORITY ORDER
	BLOCKDROP, 	
	MOBDROP,
	SWORDDURABILITY,
	PVPDAMAGE, 
	PVEDAMAGE, 	
	EXPLOSIONDAMAGE,
	FIREDAMAGE,
	FALLDAMAGE, 
	PLOWDURABILITY,
	TOOLDURABILITY,
	EAT,
	CRAFT, 		// TODO ??
	ARMORHIT, 	// TODO onEntityDamage
	PLOW,
	DIGTIME,	// TODO ??
	BOWATTACK, 
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
