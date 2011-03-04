package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.craftbukkit.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import redecouverte.npcspawner.NpcEntityTargetEvent;
import redecouverte.npcspawner.NpcEntityTargetEvent.NpcTargetReason;


public class DCEntityListener extends EntityListener {


	public DCEntityListener(final DwarfCraft plugin) {
	}

	public void onEntityDamage(EntityDamageEvent event){
		if (event instanceof EntityDamageByEntityEvent){
		   	if (event.getEntity() instanceof HumanEntity ) {
	    		if (checkDwarfTrainer((EntityDamageByEntityEvent)event)){ // pulling this out so I don't muck up this code.    		
	    			event.setCancelled(true);
	    			return;
	    		}
	    	}
		}
		if(event.isCancelled()) return;
		if(
			event.getCause() == DamageCause.BLOCK_EXPLOSION ||
			event.getCause() == DamageCause.ENTITY_EXPLOSION || 
			event.getCause() == DamageCause.FALL || 
			event.getCause() == DamageCause.SUFFOCATION || 
			event.getCause() == DamageCause.FIRE || 
			event.getCause() == DamageCause.FIRE_TICK){
			if (DwarfCraft.debugMessagesThreshold < -1) System.out.println("Debug Message: Damage Event: environment");//spammy message
			onEntityDamagedByEnvirons(event);
				
		}
		else if (event instanceof EntityDamageByProjectileEvent){
			EntityDamageByProjectileEvent sub = (EntityDamageByProjectileEvent) event;
			if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: Damage Event: projectile");
			onEntityDamageByProjectile(sub);
		}
		else if (event instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent sub = (EntityDamageByEntityEvent) event;
			if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: Damage Event: entity by entity");
			onEntityAttack(sub);
		}
		else return;
	}
	
    public void onEntityDamagedByEnvirons(EntityDamageEvent event) {
    	if (DwarfCraft.disableEffects) return;
        //General information
       	if(!(event.getEntity() instanceof Player)) return;
       	Player player = (Player) event.getEntity();
       	Dwarf dwarf = Dwarf.find(player);
       	List<Skill> skills = dwarf.skills;
       	
       	int damage = event.getDamage();
       	int hp = player.getHealth();
       	
      	//Effect Specific information
       	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(	(e.effectType == EffectType.FALLDAMAGE && event.getCause() == DamageCause.FALL) ||
    				(e.effectType == EffectType.FALLDAMAGE && event.getCause() == DamageCause.SUFFOCATION) ||
					(e.effectType == EffectType.FIREDAMAGE && event.getCause() == DamageCause.FIRE) ||
					(e.effectType == EffectType.FIREDAMAGE && event.getCause() == DamageCause.FIRE_TICK) ||
					(e.effectType == EffectType.EXPLOSIONDAMAGE && event.getCause() == DamageCause.ENTITY_EXPLOSION) ||
					(e.effectType == EffectType.EXPLOSIONDAMAGE && event.getCause() == DamageCause.BLOCK_EXPLOSION)){
    				damage = (int) Math.floor((e.getEffectAmount(dwarf) * damage));
    				if (DwarfCraft.debugMessagesThreshold < 8) System.out.println("Debug Message: environment damage type:" + event.getCause() +
    						" base damage:"+event.getDamage()+ " new damage:" + damage+ " player HP before:" + hp+" effect called:"+e.id);
    				event.setDamage(damage);
    			}
    		}
    		for(Effect e:s.effects){
    			if( e.effectType == EffectType.FALLTHRESHOLD && event.getCause() == DamageCause.FALL){
    				if (event.getDamage()<=e.getEffectAmount(dwarf)) event.setCancelled(true);
    			}
    		}
       	}  	
    }

    public void onEntityAttack(EntityDamageByEntityEvent event) {
    	if (DwarfCraft.disableEffects) return;
    	Entity damager = event.getDamager();
    	LivingEntity victim;
    	if  (event.getEntity() instanceof LivingEntity) victim = (LivingEntity) event.getEntity();
    	else return;
    	boolean isPVP = false;
    	Dwarf attacker = null;
    	    	
    	if(victim instanceof Player) {
    		isPVP = true;
    	}
    	int damage = event.getDamage();
    	int hp = victim.getHealth();    	
    	if(damager instanceof Player) attacker = Dwarf.find((Player)damager);
    	//EvP no effects, EvE no effects
    	else {
    		if (DwarfCraft.debugMessagesThreshold < 4) System.out.println("Debug Message: EVP "+damager.getClass().getSimpleName() + " attacked "  + victim.getClass().getSimpleName() +" for " + damage + " of "+ hp);
    		return;
    	}
    	
    	ItemStack tool = attacker.player.getItemInHand();
    	int toolId = -1;
    	short durability = 0;
    	if (tool!=null) {
    		toolId = tool.getTypeId();  
    		durability = tool.getDurability(); 	
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
		    				double effectAmount = e.getEffectAmount(attacker);
		    				
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a sword - old:"+durability+" effect called:"+e.id);
		    				tool.setDurability((short) (durability + Util.randomAmount(effectAmount)));
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a sword - new:"+tool.getDurability());
		    				Util.toolChecker((Player) damager);
		    			}
		    		}
    			}
    			if(e.effectType == EffectType.PVEDAMAGE && !isPVP && sword){
    				if(hp <= 0) {event.setCancelled(true); return;}
    				damage = (int) Util.randomAmount((e.getEffectAmount(attacker)) * damage);
    				if(damage >= hp){
    					deadThingDrop(victim, attacker);
    				}
    				event.setDamage(damage);
    				if (DwarfCraft.debugMessagesThreshold < 7) System.out.println("Debug Message: PVE "+attacker.player.getName()+" attacked " + victim.getClass().getSimpleName() +" for " + e.getEffectAmount(attacker)+ "% of "+ event.getDamage()+" doing "+ damage + " dmg of "+ hp + "hp"+" effect called:"+e.id);
    			}
    			if(e.effectType == EffectType.PVPDAMAGE && isPVP && sword){
    				damage = (int) Util.randomAmount((e.getEffectAmount(attacker)) * damage);
    				event.setDamage(damage);
       				if (DwarfCraft.debugMessagesThreshold < 7) System.out.println("Debug Message: PVP "+attacker.player.getName()+" attacked " + ((Player) victim).getName() +" for " + e.getEffectAmount(attacker)+ "% of "+ event.getDamage()+" doing "+ damage + " dmg of "+ hp + "hp"+" effect called:"+e.id);
       			}
    		}
    	}    	
    }

    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
    	if (DwarfCraft.disableEffects) return;
    	if(!(event.getDamager() instanceof Player)) return;
    	Dwarf dwarf = Dwarf.find((Player) event.getDamager());
    	LivingEntity hitThing = ((LivingEntity) event.getEntity());
    	int hp = hitThing.getHealth();
    	double damage;
    	for(Skill s: dwarf.skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.BOWATTACK){
    				damage = event.getDamage()* e.getEffectAmount(dwarf);
    				if(hp <= 0) {event.setCancelled(true); return;}
    				damage = (int) Util.randomAmount((e.getEffectAmount(dwarf)));
    				if(damage >= hp) {
    					hitThing.setHealth(event.getDamage());
    					deadThingDrop(hitThing, dwarf);
    				}
    				else hitThing.setHealth((int) (hp-damage+event.getDamage()));
    				if (DwarfCraft.debugMessagesThreshold < 9) System.out.println("Debug Message: PVP "+dwarf.player.getName()+" shot " + hitThing.getClass().getSimpleName() +" for " + damage + " of "+ hp + " eventdmg:" + event.getDamage()+" effect called:"+e.id );

    			}
    		}
    	}
    }

    /**
     * Mobs that die from any means but sword/arrow attack drop _nothing_
     * this prevents monster farmers and makes hunter a more valuable skill
     * 
     * Drops in general are increased to balance this major nerf.
     */
    public void onEntityDeath(EntityDeathEvent event) {
    	if (DwarfCraft.disableEffects) return;
    	if (event.getEntity() instanceof Player) return;
    	List<ItemStack> items = event.getDrops();
    	int numbItems = items.size();
    	if (numbItems == 0) return;
    	for(int i=0; i<numbItems;i++){
    		items.remove(0);
    	}
    }
    
    public void deadThingDrop(LivingEntity deadThing, Dwarf killer){
    	if (deadThing instanceof CraftSheep)return;
    	for(Skill s: killer.skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.MOBDROP){
    				if(
    					(e.id == 810 && (deadThing instanceof CraftPig )) ||
	    				(e.id == 811 && (deadThing instanceof CraftCow )) ||
	    				(e.id == 820 && (deadThing instanceof CraftCreeper ))||
	    				(e.id == 823 && (deadThing instanceof CraftSpider ))||
	    				(e.id == 821 && (deadThing instanceof CraftSkeleton )) ||
	    				(e.id == 822 && (deadThing instanceof CraftSkeleton )) ||
	    				(e.id == 850 && (deadThing instanceof CraftZombie )) ||
	    				(e.id == 851 && (deadThing instanceof CraftZombie )) ||
		    			(e.id == 852 && (deadThing instanceof CraftChicken ))){
    					
    					if (DwarfCraft.debugMessagesThreshold < 5) System.out.println("Debug Message: killed a "+deadThing.getClass().getSimpleName() +" effect called:"+e.id );
    					Util.dropBlockEffect(deadThing.getLocation(), e, e.getEffectAmount(killer), false, (byte) 0);
//    				if (e.id == 812 && (deadThing instanceof CraftSheep )) {
//    					if (DwarfCraft.debugMessagesThreshold < 5) System.out.println("Debug Message: killed a "+deadThing.getClass().getSimpleName() +" effect called:"+e.id );
//    					Util.dropBlockEffect(deadThing.getLocation(), e, e.getEffectAmount(killer), false, (byte) 1);
//    				}
					}
	    		}
	    	}
	    }
	}
    
    public void onEntityTarget(EntityTargetEvent event) {
    	if (event instanceof NpcEntityTargetEvent) {
    		checkDwarfTrainer((NpcEntityTargetEvent) event);
    		event.setCancelled(true);
    	}
    	return;
    }
    
    public boolean checkDwarfTrainer(EntityDamageByEntityEvent event) {
    	// all we know right now is that event.entity instanceof HumanEntity
    	DwarfTrainer trainer = DataManager.getTrainer(event.getEntity());
   		if ( trainer != null ) {
   			if ( event.getDamager() instanceof Player ) {
   				// 	in business, left click
   				if ( trainer.isGreeter() ) {
   					trainer.printLeftClick((Player)(event.getDamager()));
   				}
   				else {
   					trainer.lookAt(event.getDamager());
   					trainer.printSkillInfo((Player)(event.getDamager()));
   				}
   			}
			return true;
   		} 
    	return false;
    }
    
    public boolean checkDwarfTrainer(NpcEntityTargetEvent event) { // will be used for right clicks, move, touch, etc
    	try {
    	   	DwarfTrainer trainer = DataManager.getTrainer(event.getEntity()); 
       		if ( trainer != null ) {
    			if ( event.getTarget() instanceof Player ) {
    				// in business
    				if ( event.getNpcReason() == NpcTargetReason.CLOSEST_PLAYER ) { 
    					// player is close
    					// doesn't seem to work except on spawn
    				} else if ( event.getNpcReason() == NpcTargetReason.NPC_RIGHTCLICKED ) {
    					// player right clicked
    	   				if ( trainer.isGreeter() ) {
    	   					trainer.printRightClick((Player)(event.getTarget()));
    	   				} 
    	   				else {
	    					trainer.lookAt(event.getTarget());
	    					trainer.getBasicHumanNpc().animateArmSwing();
	    					if ( Dwarf.find(((Player)event.getTarget())).getSkill(trainer.getSkillTrained()).level < trainer.getMaxSkill() )
	    						trainer.trainSkill((Player)event.getTarget());
	    					else
	    						// can't train error message
	    						;
    	   				}
    				} else if ( event.getNpcReason() == NpcTargetReason.NPC_BOUNCED ) {
    					// player collided with mob
    					// doesn't seem to work
    				}
    			}
				return true;
    		}
       	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
}
