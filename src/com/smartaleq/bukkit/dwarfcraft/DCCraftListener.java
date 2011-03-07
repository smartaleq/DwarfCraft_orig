package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;

class DCCraftListener extends BlockListener {
	private final DwarfCraft plugin;

	protected DCCraftListener(DwarfCraft plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockInteract(BlockInteractEvent event) {
		if (event.getBlock().getType() == Material.WORKBENCH
				&& event.isPlayer()) {
			// schedule a *syncronous* task
			plugin.getServer()
					.getScheduler()
					.scheduleSyncDelayedTask(
							plugin,
							new DCCraftSchedule(plugin, plugin.getDataManager()
									.find(((Player) (event.getEntity())))), 5);
		}
	}
}