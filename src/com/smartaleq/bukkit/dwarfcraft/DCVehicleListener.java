package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;

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
    
    public void onVehicleEnter(VehicleEnterEvent event) {
    	DataManager.vehicleList.add(new DwarfVehicle(event.getVehicle()));
    	if (DwarfCraft.debugMessagesThreshold < 6) System.out.println("DC6:Added DwarfVehicle to vehicleList");
    }
       
    public void onVehicleExit(VehicleExitEvent event) {
    	for ( DwarfVehicle i : DataManager.vehicleList ) {
    		if ( i.vehicle.equals(event.getVehicle())) {
    			DataManager.vehicleList.remove(i);
    			if (DwarfCraft.debugMessagesThreshold < 5) System.out.println("DC5:Removed DwarfVehicle from vehicleList");
    		}
    	}
    }
    
//	public void onVehicleDestroyed(VehicleDestroyedEvent event)
    
    /**
     * Called when a vehicle moves.
     *
     * @param event
     */
    public void onVehicleMove(VehicleMoveEvent event) { 	
    	if ( event.getVehicle().getPassenger() == null ) return;
    	
    	Dwarf dwarf = Dwarf.find((Player)event.getVehicle().getPassenger()); // this will break when zombies ride boats.
     	double effectAmount = 1.0;
    	
    	for(Skill s: dwarf.skills){
    		if (s==null)continue;
    		for(Effect e:s.effects){
    			if (e==null) continue;
    			if(e.effectType == EffectType.VEHICLEMOVE){
    				effectAmount = e.getEffectAmount(dwarf);
    			}
    		}
    	}
    	
    	for ( DwarfVehicle i : DataManager.vehicleList ) {
    		if ( i.vehicle.equals(event.getVehicle())) {
    			Location location = new Location(event.getVehicle().getWorld(), 
    					event.getVehicle().getLocation().getX(),
    					event.getVehicle().getLocation().getY(),
    					event.getVehicle().getLocation().getZ());
    			location.setX(location.getX()+event.getVehicle().getVelocity().getX()*effectAmount);
    			location.setZ(location.getZ()+event.getVehicle().getVelocity().getZ()*effectAmount);
    			event.getVehicle().teleportTo(location);
    		}
    	}
    }
}

