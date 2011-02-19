package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import com.smartaleq.bukkit.dwarfcraft.ui.Command;
import com.smartaleq.bukkit.dwarfcraft.ui.Messages;
import com.smartaleq.bukkit.dwarfcraft.ui.Out;


public class DCPlayerListener extends PlayerListener {
	private final DwarfCraft plugin;
	
	public DCPlayerListener(final DwarfCraft instance) {
		plugin = instance;
	}
   


	public void onPlayerCommand(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String[] fullPlayerInput = event.getMessage().split(" ");
		if (fullPlayerInput.length >= 1) {
			if (fullPlayerInput[0].equalsIgnoreCase("/dc")){

				String[] playerInput = new String[10];
				for(int i=1; i < fullPlayerInput.length; i++){
					playerInput[i-1] = fullPlayerInput[i];
				}
				Command input = new Command(/*plugin,*/ player, playerInput);
				if (input.execute()) {
					event.setCancelled(true);
					return;
					//successful command
				}
				else {
					Out.error(player, Messages.ERRORBADINPUT);
					return;
					//failed command don't cancel in case it was someone else's
				}
			}
		}
	}
	
	public void onPlayerJoin(PlayerEvent event){
		Player player = event.getPlayer();
		Dwarf dwarf = Dwarf.find(player);
		if (dwarf == null) {
			dwarf = DataManager.createDwarf(player);
			dwarf.initializeNew();
		}
		if (!DataManager.getDwarfData(dwarf)) {
			DataManager.createDwarfData(dwarf);
		}
		Out.welcome(plugin.getServer(), dwarf);
	}
	
    /**
     * Called when a player uses an item
     *
     * @param event Relevant event details
     */
    public void onPlayerItem(PlayerItemEvent event) {
    }
}