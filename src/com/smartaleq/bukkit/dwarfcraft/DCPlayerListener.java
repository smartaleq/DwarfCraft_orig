package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



public class DCPlayerListener extends PlayerListener {
	private final DwarfCraft plugin;
	
	public DCPlayerListener(final DwarfCraft plugin) {
		this.plugin = plugin;
	}
   	
/**
 * Reads player input and checks for a /dc command
 * this functionality may soon be obsolete and use the Command class from bukkit
 */
	
	
	/**
	 * When a player joins the server this initialized their data
	 * from the database or creates new info for them.
	 * 
	 * also broadcasts a welcome "player" message
	 */
	public void onPlayerJoin(PlayerEvent event){
		Player player = event.getPlayer();
		Dwarf dwarf = plugin.getDataManager().find(player);
		if (dwarf == null) {
			dwarf = plugin.getDataManager().createDwarf(player);
		}
		if (!plugin.getDataManager().getDwarfData(dwarf)) {
			plugin.getDataManager().createDwarfData(dwarf);
		}
		plugin.getOut().welcome(plugin.getServer(), dwarf);
	}
	
    /**
     * Called when a player uses an item
     * Eating food will cause extra health or less health to be gained
     *
     * @param event Relevant event details
     */
    public void onPlayerItem(PlayerItemEvent event) {
    	 //General information
    	Player player = event.getPlayer();
    	Dwarf dwarf = plugin.getDataManager().find(player);
    	List<Skill> skills = dwarf.skills;
    	boolean hadEffect = false;
   	//Effect Specific information
    	ItemStack item = player.getItemInHand();
    	int itemId = -1;
    	if (item!=null) {
    		itemId = item.getTypeId();	
    	}
    	
    	for(Skill s: skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.EAT && e.initiatorId==itemId){
    				if (DwarfCraft.debugMessagesThreshold < 8) System.out.println("DC8: ate food:"+item.getType().toString()+" for "+e.getEffectAmount(dwarf));
    				player.setHealth((int) (player.getHealth()+e.getEffectAmount(dwarf)));
    				player.getInventory().removeItem(item);
    				hadEffect = true;
    				
				}
			}
		}
    	if(hadEffect) event.setCancelled(true);
    }
    
    public void onPlayerMove(PlayerMoveEvent event){
 //   	Block block = (Block)(event.getPlayer().getLocation().getBlock());

//    	block.
    	return;
    }
}