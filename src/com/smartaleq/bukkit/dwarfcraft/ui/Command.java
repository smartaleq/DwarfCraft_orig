package com.smartaleq.bukkit.dwarfcraft.ui;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.smartaleq.bukkit.dwarfcraft.DataManager;
import com.smartaleq.bukkit.dwarfcraft.Dwarf;
import com.smartaleq.bukkit.dwarfcraft.DwarfCraft;
import com.smartaleq.bukkit.dwarfcraft.Effect;
import com.smartaleq.bukkit.dwarfcraft.School;
import com.smartaleq.bukkit.dwarfcraft.Skill;
import com.smartaleq.bukkit.dwarfcraft.ui.Out;

public class Command {
	
	Player player;
	String[] playerInput = new String[10];
//	private final DwarfCraft plugin;
	
	public Command(/*DwarfCraft instance,*/ Player player, String[] playerInput){
//		this.plugin = instance;
		this.player = player;
		this.playerInput = playerInput;
	}
	
	public Player getPlayer(String playerName){
//		Player[] players = plugin.getServer().getOnlinePlayers();
//        for (Player player : players) {
//            if (player.getName().equalsIgnoreCase(playerName)) return player;
//        }
        return null;
	}	
	
	public boolean execute(){
		if (DwarfCraft.debugMessages) System.out.println("Debug Message: started execute");
		if (playerInput[0].equalsIgnoreCase("help")) return help();
		if (playerInput[0].equalsIgnoreCase("?")) return help();
		if (playerInput[0].equalsIgnoreCase("info")) return info();
		if (playerInput[0].equalsIgnoreCase("commands")) return commands(1);
		if (playerInput[0].equalsIgnoreCase("commands2")) return commands(2);
		if (playerInput[0].equalsIgnoreCase("tutorial")) return tutorial(1);
		if (playerInput[0].equalsIgnoreCase("tutorial2")) return tutorial(2);
		if (playerInput[0].equalsIgnoreCase("tutorial3")) return tutorial(3);
		if (playerInput[0].equalsIgnoreCase("tutorial4")) return tutorial(4);
		if (playerInput[0].equalsIgnoreCase("tutorial5")) return tutorial(5);

		if (playerInput[0].equalsIgnoreCase("skillsheet")) return skillSheet();
		if (playerInput[0].equalsIgnoreCase("skillinfo")) return skillInfo();
		if (playerInput[0].equalsIgnoreCase("effectinfo")) return effectInfo();
		
		if (playerInput[0].equalsIgnoreCase("train")) return train();
		if (playerInput[0].equalsIgnoreCase("setskill")) return setSkill();
		
		if (playerInput[0].equalsIgnoreCase("MAKEMEADWARF")) return makeMeADwarf(false);
		if (playerInput[0].equalsIgnoreCase("REALLYMAKEMEADWARF"))return makeMeADwarf(true);
		if (playerInput[0].equalsIgnoreCase("MAKEMEANELF")) return makeMeAnElf(false);
		if (playerInput[0].equalsIgnoreCase("REALLYMAKEMEANELF")) return makeMeAnElf(true);
		
		if (playerInput[0].equalsIgnoreCase("SCHOOLLIST")) return schoolList();
		if (playerInput[0].equalsIgnoreCase("SCHOOLINFO")) return schoolInfo();
		if (playerInput[0].equalsIgnoreCase("HERE")) return here();
		if (playerInput[0].equalsIgnoreCase("CREATESCHOOL")) return createSchool();
				
		return false;
	}
	
	/**
	 * Sends detailed help text from command help listing or general help text with no argument
	 */
	public boolean help() {
		if (DwarfCraft.debugMessages) System.out.println("Debug Message: started help");
		if (playerInput[1] == null)	return Out.generalHelp(player);
		if (DwarfCraft.debugMessages) System.out.println("Debug Message: started help2");
		for (CommandInfo c: CommandInfo.values()){
			if (DwarfCraft.debugMessages) System.out.println("Debug Message: started help3");
			if (playerInput[1].equalsIgnoreCase(c.toString())){		
				if (DwarfCraft.debugMessages) System.out.println("Debug Message: started help4");
				//this is a command
				return Out.commandHelp(player, c);
			}
		}
		//this is not a command
		Out.error(player, Messages.ERRORBADINPUT);
		return false;
	}
	
	private boolean info() {
		return Out.info(player);
	}

	private boolean commands(int i) {
		return Out.commandList(player, i);
	}
	private boolean tutorial(int i) {
		return Out.tutorial(player, i);
	}

	/**
	 * Parses input for a player name, or uses the command issuer if blank
	 */
	private boolean skillSheet() {
		if (DwarfCraft.debugMessages) System.out.println("Debug Message: starting skillsheet");
		Dwarf target = (Dwarf)getPlayer(playerInput[1]);
		if(target == null) {
			target = Dwarf.find(player);
			
		}
		if (DwarfCraft.debugMessages) System.out.println("Debug Message: skillsheet target =" + target.player.getName());
		return Out.printSkillSheet(target, player);
	}

	/**
	 * Parses input and passes skill to output
	 */
	private boolean skillInfo() {
		if (playerInput[2] != null) playerInput[1] = playerInput[1].concat(" " + playerInput[2]);
		Skill skill = (Dwarf.find(player)).getSkill(playerInput[1]);
		if (skill == null) return false;
		return Out.printSkillInfo(player, skill);
	}

	/**
	 * Parses input and passes effect to output
	 */
	private boolean effectInfo() {
		Effect effect = (Dwarf.find(player)).getEffect(Integer.parseInt(playerInput[1]));
		if(effect != null) return Out.effectInfo(player, effect);
		return false;
	}
	
	/**
	 * Parses input, checks each requirement, prints results
	 * If successful increases skill, removes items
	 * 
	 */
	private boolean train() {
		try{
			Dwarf dwarf = (Dwarf.find(player));
			Skill skill = dwarf.getSkill(playerInput[1]);
			ItemStack[] trainingCosts = dwarf.calculateTrainingCost(skill); 
			boolean canTrain = true;
			
			if (!dwarf.isElf){
				if (skill.level < 30) Out.sendMessage(dwarf, "&aYou are a &9Dwarf &a&& your skill is below 30", "&6[Train &b"+skill.id+"&6] ");
				else {Out.sendMessage(dwarf, "&cYou are a &9Dwarf &cbut your skill is max level!", "&6[Train &b"+skill.id+"&6] ");
					canTrain = false;}}
			else {Out.sendMessage(dwarf, "&cYou are an &fElf &cnot a &9Dwarf&6!", "&6[Train &b"+skill.id+"&6] ");
				return canTrain = false;}
			if (dwarf.isInZone(skill.school)) Out.sendMessage(dwarf, "&aYou are in a &1"+skill.school+" &atraining zone", "&6[Train &b"+skill.id+"&6] ");
			else {Out.sendMessage(dwarf, "&cYou are not in a &1"+skill.school+" &ctraining zone", "&6[Train &b"+skill.id+"&6] ");
				canTrain = false;}
			for (ItemStack itemStack: trainingCosts){
				if(itemStack == null) continue;
				if(dwarf.countItem(itemStack.getTypeId()) > itemStack.getAmount()) Out.sendMessage(dwarf, "&aYou have the &2"+itemStack.toString()+ " &arequired", "&6[Train &b"+skill.id+"&6] ");
				else{Out.sendMessage(dwarf, "&cYou do not have the &2"+itemStack.toString()+ " &crequired", "&6[Train &b"+skill.id+"&6] ");
					canTrain = false;}
			}
			if (canTrain){
				skill.level++;
				for (ItemStack itemStack: trainingCosts) dwarf.removeInventoryItems(itemStack.getTypeId(), itemStack.getAmount());
				Out.sendMessage(dwarf,"&6Training Successful");
				return true;
			}
			else {
				Out.sendMessage(dwarf,"&6Training Unsuccessful");
				return true;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Admin Command to change another players skills. Fails quietly
	 */
	private boolean setSkill() {
//		if (!player.hasPermission()) return false;
		try{
			Dwarf dwarf = (Dwarf) getPlayer(playerInput[1]);
			Skill skill = dwarf.getSkill(Integer.parseInt(playerInput[2]));
			int level = Integer.parseInt(playerInput[3]);
			skill.level = level;
			Out.sendMessage(new Player[]{(Player)dwarf, player}, 
					" Admin " + player +
					" changed Skill " + skill.displayName +
					" for player " + dwarf.player.getName() +
					" to level " + level);
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private boolean makeMeADwarf(boolean confirmed) {
		Dwarf dwarf = Dwarf.find(player);
		if (dwarf.isElf) return dwarf.makeDwarf(); 
		else if (confirmed) {
			Out.becameADwarf(player);
			return dwarf.makeDwarf();
		}
		else {
			Out.confirmBecomingDwarf(player);
			return true;
		}
	}
	
	private boolean makeMeAnElf(boolean confirmed) {
		Dwarf dwarf = Dwarf.find(player);		
		if (dwarf.isElf) {
			Out.alreadyAnElf(player);
			return true;
		}
		else if (confirmed) {
			Out.becameAnElf(player);
			return dwarf.makeElf();
		}
		else {
			Out.confirmBecomingAnElf(player);
			return true;
		}
	}
	
	private boolean schoolList() {
		return Out.schoolList(player);
	}
	
	private boolean schoolInfo() {
		Skill[] skills = new Skill[10];
		School school = School.getSchool(playerInput[1]);
		int i=0;
		for (Skill s: (Dwarf.find(player)).skills){
			if (s==null) continue;
			if(s.school == school) {
				skills[i]= s;
				i++;
			}
		}
		Out.schoolInfo(player, school, skills);
		return false;
	}
	
	private boolean here() {
		return Out.here(player,(Dwarf.find(player)).listZones());
	}
	
	private boolean createSchool() {
		try {
	//		if (!player.hasPermission()) return false;
			World world = player.getWorld();
			School school = School.getSchool(Integer.parseInt(playerInput[1]));
			double x1 = Double.parseDouble(playerInput[2]); 
			double x2 = Double.parseDouble(playerInput[3]); 
			double y1 = Double.parseDouble(playerInput[4]); 
			double y2 = Double.parseDouble(playerInput[5]);
			double z1 = Double.parseDouble(playerInput[6]); 
			double z2 = Double.parseDouble(playerInput[7]);
			String name = playerInput[8];
			return DataManager.addSchoolZone(new Vector(x1,y1,z1), new Vector(x2,y2,z2), world, school, name);
		} 
		catch (NumberFormatException e) {
			return false;
		} 		
	}



}
