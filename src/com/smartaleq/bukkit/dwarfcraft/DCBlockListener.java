package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.inventory.ItemStack;



/**
 * This watches for broken blocks and reacts
 * 
 */
public class DCBlockListener extends BlockListener {
	 
	public DCBlockListener(final DwarfCraft plugin) {
	}
 
    public void onBlockDamage(BlockDamageEvent event) {
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

    /**
     * Called when a player right clicks a block
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
    	short damage = 0;
    	if (tool!=null) {
    		toolId = tool.getTypeId();  
    		damage = tool.getDurability(); 	
    	}
    	boolean correctTool = false;
    	Block block = event.getBlock();
    	int materialId = event.getBlock().getTypeId();
    	//if hoeing land do stuff
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
		    			dropBlockEffect(block, e, e.getEffectAmount(s.level));
	    			}
	   			}
    		}
    	}
    	
    	if (durabilityChange || blockDropChange) {
    		block.setTypeId(0);
    		event.setCancelled(true);
    	}
    }

    /**
     * Drops blocks at a block based on a specific effect(and level)
     * @param block Block being destroyed
     * @param e Effect causing a block to drop
     * @param effectAmount Double number of blocks to drop
     */
	private void dropBlockEffect(Block block, Effect e, double effectAmount) {
		Location loc = block.getLocation();
		ItemStack item = new ItemStack(e.outputId, Util.randomAmount(effectAmount));
		block.getWorld().dropItemNaturally(loc, item);
	}
}
