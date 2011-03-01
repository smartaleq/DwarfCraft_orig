package com.smartaleq.bukkit.dwarfcraft;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.World;
import com.smartaleq.bukkit.dwarfcraft.ui.Out;

import redecouverte.npcspawner.*;

public class DwarfTrainer {
	// public static BasicHumanNpcList HumanNpcList;
	private BasicHumanNpc basicHumanNpc;
	private Integer skillId;
	private Integer maxSkill;
	private boolean greeter;
	private String messageId;
	private World world;
	private ItemStack itemStack;	
	private double x, y, z;
	private float yaw, pitch;
	String basicNpcUniqueId, basicNpcName;
	
	// only used by DB
	// schema (world,uniqueId,name,skill,maxSkill,material,isGreeter,messageId,x,y,z,yaw,pitch)
	public DwarfTrainer (World newWorld, String newUniqueId, String newName, Integer newSkillId, Integer newMaxSkill, Material newMaterial, boolean newIsGreeter, String newMessageId, double newX, double newY, double newZ, Float newYaw, Float newPitch ) {
		skillId = newSkillId;
		maxSkill = newMaxSkill;
		greeter = newIsGreeter;
		messageId = newMessageId;
		x = newX;
		y = newY;
		z = newZ;
		yaw = newYaw;
		pitch = newPitch;
		basicNpcUniqueId = newUniqueId;
		basicNpcName = newName;
		basicHumanNpc = NpcSpawner.SpawnBasicHumanNpc(
				newUniqueId, 
				newName, 
				newWorld,
				newX, 
				newY, 
				newZ, 
				newYaw, 
				newPitch);
		
		Material material = newMaterial;
		assert (material != null);
		if ( material != Material.AIR ) {
			itemStack = new ItemStack(material);
			itemStack.setAmount(1);
			itemStack.setDurability((short)0);
			basicHumanNpc.getBukkitEntity().setItemInHand(itemStack);
		}
	}

	// constructor only for *trainers*
	public DwarfTrainer (Player player, String uniqueId, String name, Integer skillId, Integer maxSkill, String messageId, boolean isGreeter) {
		this.skillId = skillId; 
		this.maxSkill = maxSkill;
		this.messageId = messageId;
		greeter = isGreeter;
		world = player.getWorld();
		x = player.getLocation().getX();
		y = player.getLocation().getY();
		z = player.getLocation().getZ();
		yaw = player.getLocation().getYaw();
		pitch = player.getLocation().getPitch();
		basicNpcUniqueId = uniqueId;
		basicNpcName = name;

		basicHumanNpc = NpcSpawner.SpawnBasicHumanNpc(
				uniqueId, 
				name, 
				player.getWorld(), 
				player.getLocation().getX(), 
				player.getLocation().getY(), 
				player.getLocation().getZ(), 
				player.getLocation().getYaw(), 
				player.getLocation().getPitch());
		
		Material material;
		if ( greeter )
			material = Material.AIR;
		else
			material = Dwarf.find(player).getSkill(skillId).getTrainerHeldMaterial();
		assert (material != null);
		if ( material != Material.AIR ) {
			ItemStack itemStack = new ItemStack(material);
			itemStack.setAmount(1);
			itemStack.setDurability((short)0);
			basicHumanNpc.getBukkitEntity().setItemInHand(itemStack);
		}
	}
	
	public boolean equals(Object that) {
		if ( this == that ) {
			return true;
		}
		else if ( that instanceof HumanEntity ) {
			if ( basicHumanNpc.getBukkitEntity().getEntityId() == ((HumanEntity)that).getEntityId()) {
				return true;
			}
		}
		return false;
	}
	
	public String getUniqueId() { return basicHumanNpc.getUniqueId(); }
	public String getName() { return basicHumanNpc.getName(); }
	public BasicHumanNpc getBasicHumanNpc() { return basicHumanNpc; }
	public Integer getSkillTrained() { return skillId; }
	public World getWorld() { return world; }
	public Integer getMaxSkill() { return maxSkill; }
	public boolean isGreeter() { return greeter; }
	public int getMaterial() { if (itemStack != null) return itemStack.getTypeId(); else return (int)(Material.AIR.getId()); }
	public String getMessageId() { return messageId; }
	
	public Location getLocation() {
		return basicHumanNpc.getBukkitEntity().getLocation();
	}
	
	public void spawn() {
		basicHumanNpc = NpcSpawner.SpawnBasicHumanNpc(
				basicNpcUniqueId,
				basicNpcName, 
				world,
				x, 
				y, 
				z, 
				yaw, 
				pitch);
	}
	
	public void lookAt(Entity target) {
		assert(target != null);
		Location l;
		l = target.getLocation().clone();
		if ( target instanceof Player ) {
			l.setY(l.getY()+((Player) target).getEyeHeight());
		}
		this.lookAt(l);
		return;
	}
	
	public void lookAt(Location l) {
		assert(l != null);
		return;
	}
	
	public void printSkillInfo(Player player){
		Out.printSkillInfo(player, Dwarf.find(player).getSkill(this.skillId));
	}
	
	public void printLeftClick(Player player) {
		GreeterMessage msg = DataManager.getGreeterMessage(messageId);
		if ( msg != null ) {
			Out.sendMessage(player, msg.getLeftClickMessage());
		}
		else { 
			System.out.println("[DC] Error: Greeter " + basicHumanNpc.getUniqueId() + " has no left click message. Check your configuration file for message ID " + messageId);
		}
		return;
	}
	
	public void printRightClick(Player player) {
		GreeterMessage msg = DataManager.getGreeterMessage(messageId);
		if ( msg != null ) {
			Out.sendMessage(player, msg.getRightClickMessage());
		}
		return;
	}
	
	public void trainSkill(Player player){
		boolean soFarSoGood = true;
		Dwarf dwarf = (Dwarf.find(player));
		assert(dwarf != null);
		Skill skill = dwarf.getSkill(this.skillId);
		assert(skill != null);
		
		List <ItemStack> trainingCosts = dwarf.calculateTrainingCost(skill); 
		
		//Must be a dwarf, not an elf
		if (dwarf.isElf) {
			Out.sendMessage(dwarf, "&cYou are an &fElf &cnot a &9Dwarf&6!", "&6[Train &b"+skill.id+"&6] ");
			soFarSoGood = false;
		}
		else Out.sendMessage(dwarf, "&aYou are a &9Dwarf &aand can train skills.", "&6[Train &b"+skill.id+"&6] ");
		
		//Must have skill level between 0 and 29
		if ( skill.level >= 30) {
			Out.sendMessage(dwarf, "&cYour skill is max level (30)!", "&6[Train &b"+skill.id+"&6] ");
			soFarSoGood = false;
		}
		
		//Must have enough materials to train
		for (ItemStack itemStack: trainingCosts) {
			if(itemStack == null) continue;
			if(itemStack.getAmount() == 0) continue;
			if(dwarf.countItem(itemStack.getTypeId()) < itemStack.getAmount()) {
				Out.sendMessage(dwarf, "&cYou do not have the &2"+itemStack.getAmount() + " " + itemStack.getType()+ " &crequired", "&6[Train &b"+skill.id+"&6] ");
				soFarSoGood = false;
			}
			else Out.sendMessage(dwarf, "&aYou have the &2"+itemStack.getAmount() + " " + itemStack.getType()+ " &arequired", "&6[Train &b"+skill.id+"&6] ");

		}
		
		//If passed all the 'musts' successfully
		if(soFarSoGood){
			skill.level++;
			for (ItemStack itemStack: trainingCosts)
				dwarf.removeInventoryItems(itemStack.getTypeId(), itemStack.getAmount());
			Out.sendMessage(dwarf,"&6Training Successful!","&6[&b"+skill.id+"&6] ");
			DataManager.saveDwarfData(dwarf, player.getName());
			return;
		}
		else{
			return; //something else goes here
		}
	}
}