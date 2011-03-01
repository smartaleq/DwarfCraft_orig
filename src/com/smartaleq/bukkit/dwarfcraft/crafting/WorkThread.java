package com.smartaleq.bukkit.dwarfcraft.crafting;

import net.minecraft.server.ContainerWorkbench;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.InventoryCraftResult;
import net.minecraft.server.ItemStack;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.smartaleq.bukkit.dwarfcraft.Dwarf;
import com.smartaleq.bukkit.dwarfcraft.Effect;
import com.smartaleq.bukkit.dwarfcraft.EffectType;
import com.smartaleq.bukkit.dwarfcraft.Skill;

public class WorkThread implements Runnable {
	private CraftPlayer craftPlayer;
	private EntityPlayer entityPlayer;

	public WorkThread(Player p) {
		this.craftPlayer = (CraftPlayer) p;
		this.entityPlayer = craftPlayer.getHandle();

		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			kill();
		}

	}

	@SuppressWarnings("unused")
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			if (entityPlayer == null
					|| entityPlayer.activeContainer == entityPlayer.defaultContainer) {
				kill();
			}
			ContainerWorkbench containerBench = null;
			try {
				containerBench = (ContainerWorkbench) entityPlayer.activeContainer;
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("about to kill thread");
				kill();
			}

			ItemStack result = ((InventoryCraftResult) containerBench.b)
					.getContents()[0];
			// ((ContainerWorkbench) ep.activeContainer).b.getContents()[0];
			boolean a = false;
			if (CraftResults.getInstance().getResult(containerBench.a) != null) {
				ItemStack outputStack = CraftResults.getInstance().getResult(
						containerBench.a);
				if (outputStack != null) {
					//New DwarfCraft Code
					Dwarf dwarf = Dwarf.find((Player) entityPlayer);
					int materialId = outputStack.id;
					for (Skill s:dwarf.skills){
						for (Effect e:s.effects){
							if(e.effectType == EffectType.CRAFT && materialId == e.outputId){
								outputStack.count = (int) e.getEffectAmount(dwarf);
								//need code to check max stack size and if amount created > max stack size drop all count above 1 to ground/inventory.
								//I'm not sure what the server ItemStack method is for is.getMaxStackSize()
								
							}
						}
					}
					containerBench.b.a(0, outputStack); // i believe this sets the output stack on the crafting bench. this will not update client side.
				}
			}
			try {
				Thread.sleep(100);
				if (!craftPlayer.isOnline())
					kill();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void kill() {
		CraftListener.runningThreads.remove(this);
		Thread kill = Thread.currentThread();
		kill.interrupt();
	}
}
