package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.Chunk;

public class DCWorldListener extends WorldListener {
	
	public void onChunkUnloaded(ChunkUnloadEvent event) {
		Chunk chunk = event.getChunk();
		event.setCancelled(DataManager.checkTrainersInChunk(chunk));
	}
}