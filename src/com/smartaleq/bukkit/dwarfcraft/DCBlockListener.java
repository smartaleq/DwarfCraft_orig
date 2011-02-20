package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.inventory.ItemStack;



/**
 * This watches for broken blocks and reacts
 * 
 */
public class DCBlockListener extends BlockListener {
	 
	public DCBlockListener(final DwarfCraft plugin) {
	}
 
  /**
   * onBlockDamage used to accelerate how quickly blocks are destroyed. setDamage() not implemented yet
   */
	/*  public void onBlockDamage(BlockDamageEvent event) {
    	if (DwarfCraft.disableEffects) return;
    //General information
    	Player player = event.getPlayer();
    	Dwarf dwarf = Dwarf.find(player);
    	List<Skill> skills = dwarf.skills;
    	
   	//Effect Specific information
    	ItemStack tool = player.getItemInHand();
    	int toolId = -1;
    	if (tool!=null) {
    		toolId = tool.getTypeId();   	
    	}
    	boolean correctTool = false;
    	int materialId = event.getBlock().getTypeId();	
    	
//    	event.setDamageLevel(event.getDamageLevel() + effectAmount);
//		event.setCancelled(true);
    }
    */

    /**
     * Called when a player right clicks a block, used for hoe-ing grass.
     *
     * @param event Relevant event details
     */
    public void onBlockRightClick(BlockRightClickEvent event) {
    	if (DwarfCraft.disableEffects) return;
     //General information
    	Player player = event.getPlayer();
    	Dwarf dwarf = Dwarf.find(player);
    	List<Skill> skills = dwarf.skills;
    	
   	//Effect Specific information
    	ItemStack tool = player.getItemInHand();
    	int toolId = -1;
    	short durability = 0;
    	if (tool!=null) {
    		toolId = tool.getTypeId();  
    		durability = tool.getDurability(); 	
    	}
    	Block block = event.getBlock();
    	Location loc = block.getLocation();
    	int materialId = event.getBlock().getTypeId();
    	boolean durabilityChange = false;
    	boolean blockDropChange = false;
    	
    	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.PLOWDURABILITY){
    				for(int id:e.tools){
    					if(id == toolId && (materialId == 3 || materialId == 2)) {
		    				double effectAmount = e.getEffectAmount(s.level);
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a hoe - old:"+durability);
		    				tool.setDurability((short) (durability + Util.randomAmount(effectAmount)));
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a hoe - new:"+tool.getDurability());
		    				block.setTypeId(60);
		    				durabilityChange = true;
		    			}
    				}
    			}
				if(e.effectType == EffectType.PLOW){
					for(int id:e.tools){
						if(id == toolId && materialId == 3){
		    				Util.dropBlockEffect(loc, e, e.getEffectAmount(s.level), true);
			    			blockDropChange = true;
						}
					}
    			}
    		}
    	}
    	if (durabilityChange || blockDropChange) {
    		((Cancellable) event).setCancelled(true);
    	}
    }
    	

    /**
     * Called when a block is destroyed by a player.
     *
     * @param event Relevant event details
     */
    public void onBlockBreak(BlockBreakEvent event) {
    	if (DwarfCraft.disableEffects) return;
    //General information
    	Player player = event.getPlayer();
    	Dwarf dwarf = Dwarf.find(player);
    	List<Skill> skills = dwarf.skills;
    	
   	//Effect Specific information
    	ItemStack tool = player.getItemInHand();
    	int toolId = -1;
    	short damage = 0;
    	if (tool!=null) {
    		toolId = tool.getTypeId();  
    		damage = tool.getDurability(); 	
    	}
    	boolean correctTool = false;
    	Block block = event.getBlock();
    	Location loc = block.getLocation();
    	int materialId = event.getBlock().getTypeId();
    	
    //Logic vars, would be better with methods, but kept ugly for code simplicity
    	boolean durabilityChange = false;
    	boolean blockDropChange = false;
    	    	
   	//Check if durability change happens
    	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.TOOLDURABILITY){
	    			for(int id:e.tools){
		    			if(id == toolId) {
		    				double effectAmount = e.getEffectAmount(s.level);
		    				tool.setDurability((short) (damage + Util.randomAmount(effectAmount)));
		    				durabilityChange = true;
		    			}
		    		}
    			}
     //Check if blockdrop change happens  	
    			if(e.effectType == EffectType.BLOCKDROP && e.initiatorId == materialId){
    				correctTool = false;
	    			for(int id:e.tools)	if(id == toolId)correctTool = true;
		    		if(correctTool || e.allowFist){
		    			Util.dropBlockEffect(loc, e, e.getEffectAmount(s.level), true);
		    			blockDropChange = true;
	    			}
	   			}
    		}
    	}
    	
    	if (durabilityChange || blockDropChange) {
    		block.setTypeId(0);
    		event.setCancelled(true);
    	}
    }



}
