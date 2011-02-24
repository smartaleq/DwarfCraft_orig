package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class DCVehicleListener extends VehicleListener{
	
    public DCVehicleListener(final DwarfCraft plugin) {
    }
     
    /**
     * Called when a vehicle is damaged by the player.
     * 
     * @param event
     */
    public void onVehicleDamage(VehicleDamageEvent event) {
    	//if(event.getDamage()>event.getVehicle().getHealth()) dropstuff
    	event.getAttacker();
    }
    
//	public void onVehicleDestroyed(VehicleDestroyedEvent event)
    
    
    /**
     * Called when an vehicle moves.
     *
     * @param event
     */
    public void onVehicleMove(VehicleMoveEvent event) {
    }

}

