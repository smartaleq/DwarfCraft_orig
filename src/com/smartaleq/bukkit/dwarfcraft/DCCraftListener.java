package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;

class DCCraftListener extends BlockListener {
	public final DwarfCraft plugin;

	DCCraftListener(DwarfCraft plugin) {
		this.plugin = plugin;
	}

	public void onBlockInteract(BlockInteractEvent event) {
		if ( event.getBlock().getType() == Material.WORKBENCH && event.isPlayer() ) {
			// schedule a *syncronous* task
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DCCraftSchedule(plugin, Dwarf.find(((Player)(event.getEntity())))), 5);
		}
	}
}