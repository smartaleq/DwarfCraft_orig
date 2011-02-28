package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
/*	public void onBlockDamage(BlockDamageEvent event) {
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
    	if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: damage level = " + event.getDamageLevel() +
    			" damage amount = " + event.getDamageLevel());
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
//    	Block blockAbove = block.getRelative(0,1,0);
    	Location loc = block.getLocation();
    	Material material = event.getBlock().getType();
//    	boolean durabilityChange = false;
//    	boolean blockDropChange = false;
//    	if(tool.getType()==Material.SEEDS && block.getType() == Material.SOIL && blockAbove.getType()==Material.AIR){
//    		blockAbove.setType(Material.CROPS);
//    		if (tool.getAmount()==1) player.getInventory().removeItem(tool);
//    		else tool.setAmount(tool.getAmount()-1);
//    	}
    	
    	
    	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.PLOWDURABILITY){
    				for(int id:e.tools){
    					if(id == toolId && (material == Material.DIRT || material == Material.GRASS)) {
		    				double effectAmount = e.getEffectAmount(dwarf);
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a hoe - old:"+durability);
		    				tool.setDurability((short) (durability + Util.randomAmount(effectAmount)));
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a hoe - new:"+tool.getDurability());
		    				Util.toolChecker(player);
		    				block.setTypeId(60);
//		    				durabilityChange = true;
		    			}
    				}
    			}
				if(e.effectType == EffectType.PLOW){
					for(int id:e.tools){
						if(id == toolId && material == Material.GRASS){
		    				Util.dropBlockEffect(loc, e, e.getEffectAmount(dwarf), true, (byte) 0);
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: hoed some ground:"+e.getEffectAmount(dwarf));
//			    			blockDropChange = true;
						}
					}
    			}
    		}
    	}
    }
    	

    /**
     * Called when a block is destroyed by a player.
     *
     * @param event Relevant event details
     */
    public void onBlockBreak(BlockBreakEvent event) {
    	if (DwarfCraft.disableEffects) return;
    	if (event.isCancelled()) return;
    	if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: on block break called");
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
    	boolean correctTool = false;
    	Block block = event.getBlock();
    	Location loc = block.getLocation();
    	int materialId = event.getBlock().getTypeId();
    	byte meta = block.getData();
    	
    //Logic vars, would be better with methods, but kept ugly for code simplicity
    	boolean durabilityChange = false;
    	boolean blockDropChange = false;
    	    	
   	//Check if durability change happens
    	
    	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.TOOLDURABILITY && durability != -1){
	    			for(int id:e.tools){
		    			if(id == toolId) {
		    				double effectAmount = e.getEffectAmount(dwarf);
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a tool - old:"+durability);
		    				tool.setDurability((short) (durability + Util.randomAmount(effectAmount)));
		    				if (DwarfCraft.debugMessagesThreshold < 3) System.out.println("Debug Message: affected durability of a tool - new:"+tool.getDurability());
		    				Util.toolChecker(player);
		    				durabilityChange = true;
		    			}
		    		}
    			}
     //Check if blockdrop change happens  	
    			if(e.effectType == EffectType.BLOCKDROP && e.initiatorId == materialId){
    				correctTool = false;
	    			for(int id:e.tools)	if(id == toolId)correctTool = true;
	    			//Crops special line:
	    			if (e.initiatorId == 59) if(meta != 7) continue;
		    		if (DwarfCraft.debugMessagesThreshold < 4) System.out.println("Debug Message: Effect:" +e.id + " tool: " + toolId+" and toolRequired:"+e.toolRequired );
		    		if(correctTool || !e.toolRequired){
		    			Util.dropBlockEffect(loc, e, e.getEffectAmount(dwarf), true, (byte) 0);
		    			blockDropChange = true;
	    			}
	   			}
    		}
    	}
    	
    	if (blockDropChange) {
    		block.setTypeId(0);
    		event.setCancelled(true);
    	}
    }



}
