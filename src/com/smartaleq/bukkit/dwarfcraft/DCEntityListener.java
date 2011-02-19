package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

public class DCEntityListener extends EntityListener {


	public DCEntityListener(final DwarfCraft plugin) {
	}

	
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
    	if (DwarfCraft.disableEffects) return;
        //General information
       	if(!(event.getEntity() instanceof Player)) return;
       	Player player = (Player) event.getEntity();
       	Dwarf dwarf = Dwarf.find(player);
       	List<Skill> skills = dwarf.skills;
      	//Effect Specific information
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    	if (DwarfCraft.disableEffects) return;
    	Entity damager = event.getDamager();
    	LivingEntity victim = (LivingEntity) event.getEntity();
    	boolean isPVP = false;
    	Dwarf attacker = null;
    	if(victim instanceof Player) {
    		isPVP = true;
    	}
    	int damage = event.getDamage();
    	if(damager instanceof Player) attacker = Dwarf.find((Player)damager);
    	//EvP no effects, EvE no effects
    	else return;
    	int hp = ((LivingEntity) victim).getHealth();
    	Util.mostRecentAttacker = attacker;
    	
    	ItemStack tool = attacker.player.getItemInHand();
    	int toolId = -1;
    	short durability = 0;
    	if (tool!=null) {
    		toolId = tool.getTypeId();  
    		damage = tool.getDurability(); 	
    	}
    	boolean sword = false;
    	
    	List<Skill> skills = attacker.skills;
    	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.SWORDDURABILITY){
	    			for(int id:e.tools){
		    			if(id == toolId) {
		    				sword=true;
		    				double effectAmount = e.getEffectAmount(s.level);
		    				tool.setDurability((short) (durability + Util.randomAmount(effectAmount)));
		    				if (DwarfCraft.debugMessages) System.out.println("Debug Message: affected durability of a sword");
		    			}
		    		}
    			}
    			if(e.effectType == EffectType.PVEDAMAGE && !isPVP && sword){
    				if(hp <= 0) {event.setCancelled(true); return;}
    				damage = (int) Math.floor((e.getEffectAmount(s.level)) * damage);
    				if(damage > hp) damage = hp;
    				victim.setHealth(hp-damage+event.getDamage());
    				if (DwarfCraft.debugMessages) System.out.println("Debug Message: PVE "+attacker.player.getName()+" attacked " + victim.getClass().getName() +" for " + damage + " of "+ hp + " eventdmg:" + event.getDamage() );
    			}
    			if(e.effectType == EffectType.PVPDAMAGE && isPVP && sword){
    				if(hp <= 0) {event.setCancelled(true); return;}
    				damage = (int) Math.floor((e.getEffectAmount(s.level)) * damage);
    				if(damage > hp) victim.setHealth(event.getDamage());
    				else victim.setHealth(hp-damage+event.getDamage());
    				if (DwarfCraft.debugMessages) System.out.println("Debug Message: PVP "+attacker.player.getName()+" attacked " + ((Player) victim).getName() +" for " + damage + " of "+ hp + " eventdmg:" + event.getDamage() );
       			}
    		}
    	}    	
    }

    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
    }

    public void onEntityDeath(EntityDeathEvent event) {
    	List<ItemStack> items = event.getDrops();
    	LivingEntity deadthing = (LivingEntity) event.getEntity();
    	Dwarf killer = Util.mostRecentAttacker;
    	if (DwarfCraft.debugMessages) System.out.println("Debug Message: onDeath a " + deadthing.getClass().getName()+" died");
    }

}
