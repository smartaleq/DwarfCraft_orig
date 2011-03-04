package com.smartaleq.bukkit.dwarfcraft.crafting;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;

import com.smartaleq.bukkit.dwarfcraft.DwarfCraft;

/**
 * Listener
 * 
 * @author fullwall
 * @author smartaleq stole a bunch of code and stripped it down
 */
public class CraftListener extends BlockListener {
	
	public static DwarfCraft plugin;
	public static ArrayList<Thread> runningThreads = new ArrayList<Thread>();

	public CraftListener(DwarfCraft plugin) {
		CraftListener.plugin = plugin;
	}

	public void onBlockInteract(BlockInteractEvent e) {
		if (e.getBlock().getTypeId() == 58 && e.isPlayer()) {
			// start thread
			Thread newThread = new Thread(
					new WorkThread((Player) e.getEntity(), e.getBlock()));
			newThread.start();
			runningThreads.add(newThread);
		}
	}

}