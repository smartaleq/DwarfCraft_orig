package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Util {
	
	static int randomAmount(double input){
		double rand = Math.random();
		if (rand>input%1) return (int) Math.floor(input);
		else return (int) Math.ceil(input);
	}
	
    /**
     * Drops blocks at a block based on a specific effect(and level)
     * @param block Block being destroyed
     * @param e Effect causing a block to drop
     * @param effectAmount Double number of blocks to drop
     * @param drop item naturally or not
     */
	public static void dropBlockEffect(Location loc, Effect e, double effectAmount, boolean dropNaturally) {
		ItemStack item = new ItemStack(e.outputId, Util.randomAmount(effectAmount));
		if (item.getAmount() == 0){
			if (DwarfCraft.debugMessagesThreshold < 6) System.out.println("Debug: dropped " + item.toString());
			return;
		}
		if(dropNaturally) loc.getWorld().dropItemNaturally(loc, item);
		else loc.getWorld().dropItem(loc, item);
		if (DwarfCraft.debugMessagesThreshold < 5) System.out.println("Debug: dropped " + item.toString());	
	}
	
	public static boolean toolChecker(Player player){
		Inventory inv = player.getInventory();
		ItemStack[] contents = inv.getContents();
		boolean removedSomething = false;
		for (ItemStack item:contents){
			int damage = item.getDurability();
			int maxDamage = item.getType().getMaxDurability();
			if (damage>maxDamage) {
				item.setAmount(0);
				removedSomething = true;
			}
		}
		inv.setContents(contents);
		return removedSomething;
	}

	public static String sanitize(String name) {
		//this is shitty sanitation, i know i should use prepared statements and other things but i can't be arsed to care about someone doing sql injection into a simple game plugin database, honestly.
		String newname = name.replaceAll("[^\\w]", "");
		return newname;
	}

}
