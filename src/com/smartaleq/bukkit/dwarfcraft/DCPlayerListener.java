package com.smartaleq.bukkit.dwarfcraft;

import java.util.HashMap;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class DCPlayerListener extends PlayerListener {
	private final DwarfCraft plugin;

	protected DCPlayerListener(final DwarfCraft plugin) {
		this.plugin = plugin;
	}

	/**
	 * Reads player input and checks for a /dc command this functionality may
	 * soon be obsolete and use the Command class from bukkit
	 */

	/**
	 * Called when a player uses an item Eating food will cause extra health or
	 * less health to be gained
	 * 
	 * @param event
	 *            Relevant event details
	 */
	@Override
	public void onPlayerItem(PlayerItemEvent event) {
		// General information
		Player player = event.getPlayer();
		DCPlayer dCPlayer = plugin.getDataManager().find(player);
		HashMap<Integer, Skill> skills = dCPlayer.getSkills();
		boolean hadEffect = false;
		// Effect Specific information
		ItemStack item = player.getItemInHand();
		int itemId = -1;
		if (item != null) {
			itemId = item.getTypeId();
		}

		for (Skill s : skills.values()) {
			for (Effect e : s.getEffects()) {
				if (e.getEffectType() == EffectType.EAT
						&& e.getInitiatorId() == itemId) {
					if (DwarfCraft.debugMessagesThreshold < 8)
						System.out.println("DC8: ate food:"
								+ item.getType().toString() + " for "
								+ e.getEffectAmount(dCPlayer));
					player.setHealth(Math.min((int) (player.getHealth() + Util.randomAmount(e
							.getEffectAmount(dCPlayer))), 20));
					player.getInventory().removeItem(item);
					hadEffect = true;

				}
			}
		}
		
		if (hadEffect)
			event.setCancelled(true);
	}

	/**
	 * When a player joins the server this initialized their data from the
	 * database or creates new info for them.
	 * 
	 * also broadcasts a welcome "player" message
	 */
	@Override
	public void onPlayerJoin(PlayerEvent event) {
		Player player = event.getPlayer();
		DCPlayer dCPlayer = plugin.getDataManager().find(player);
		if (dCPlayer == null) {
			dCPlayer = plugin.getDataManager().createDwarf(player);
		}
		if (!plugin.getDataManager().getDwarfData(dCPlayer)) {
			plugin.getDataManager().createDwarfData(dCPlayer);
		}
		plugin.getOut().welcome(plugin.getServer(), dCPlayer);
	}

//	public void onPlayerQuit(PlayerEvent event) {
//		Dwarf dwarf = plugin.getDataManager().find(event.getPlayer());
//		plugin.getDataManager().Remove(dwarf);
//	}
	
}