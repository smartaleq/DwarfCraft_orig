package com.smartaleq.bukkit.dwarfcraft.ui;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.smartaleq.bukkit.dwarfcraft.Dwarf;
import com.smartaleq.bukkit.dwarfcraft.DwarfCraft;
import com.smartaleq.bukkit.dwarfcraft.Effect;
import com.smartaleq.bukkit.dwarfcraft.Skill;
import com.smartaleq.bukkit.dwarfcraft.Util;

public class Out {
	
/*
 * Messaging Statics	
 */
	static final int lineLength = 332; //pixels
	static final int maxLines = 10;
	
/*
 *     Color Schema: 
 * §4 Dark Red 		- command name
 * §c Red			- "Bad"
 * §e Yellow		- "normal"
 * §6 Gold/orange	- DC general coloring
 * §2 Dark Green	- material/item
 * §a Green			- "Good"
 * §b Teal			- skill name/ID
 * §3 Dark Teal		- levels
 * §9 Blue			- dwarves
 * §d Pink			- Help/info
 * §5 Purple		- effect ID
 * §f White			-
 * §7 Gray			-
 * §8 Dark Gray		- zone 
 * §0 Black			-
 */
	
	
	public static boolean generalInfo(Player player) {
		Out.sendMessage(player, "&d" + Messages.GeneralInfo, "&6[&d?&6] ");
		return true;
	}
		
	public static boolean commandHelp(Player player, CommandInfo c) {
		Out.sendMessage(player, "&d" + c.helpText, "&6[&d?&6] " );
		return true;
	}
	
	public static boolean info(Player player) {
		Out.sendMessage(player, Messages.Fixed.INFO.message, "&6[&dInfo&6] ");
		return true;
	}
	
	public static boolean rules(Player player) {
		Out.sendMessage(player, Messages.ServerRules, "&6[&dRules&6] ");
		return true;
	}


	public static boolean commandList(Player player, int number) {
		if (number == 1) {
			Out.sendMessage(player, Messages.Fixed.COMMANDLIST1.message,"&6[&d?&6] " );
			return true;
		}
		if (number == 2){
			Out.sendMessage(player, Messages.Fixed.COMMANDLIST2.message,"&6[&d?&6] " );
			return true;
		}
		return false;
	}

	public static boolean tutorial(Player player, int i) {
		if (i == 1) Out.sendMessage(player, Messages.Fixed.TUTORIAL1.message,"&6[&dDC&6] ");
		else if (i == 2) Out.sendMessage(player, Messages.Fixed.TUTORIAL2.message,"&6[&dDC&6] ");
		else if (i == 3) Out.sendMessage(player, Messages.Fixed.TUTORIAL3.message,"&6[&dDC&6] ");
		else if (i == 4) Out.sendMessage(player, Messages.Fixed.TUTORIAL4.message,"&6[&dDC&6] ");
		else if (i == 5) Out.sendMessage(player, Messages.Fixed.TUTORIAL5.message,"&6[&dDC&6] ");
		else return false;
		return true;
	}
	
	public static boolean printSkillInfo(Player player, Skill skill){
		//general line
		Out.sendMessage(player, "&6  Skillinfo for &b"+skill.displayName+"&6 [&b"+skill.id+"&6] || Your level &3"+skill.level);
		//effects lines
		Out.sendMessage(player, "&6[&5EffectID&6]&f------&6[Effect]&f------" );
		for(Effect effect:skill.effects){
			if (effect != null)
				Out.sendMessage(player, effect.describeLevel(Dwarf.find(player)), "&6[&5"+effect.id+"&6] ");
		}
		//training lines	
		if (skill.level == 30) {
			Out.sendMessage(player, "&6---This skill is maximum level, no training available---");
			return true;
		}
		Out.sendMessage(player, "&6---Train costs for level &3"+(skill.level+1));
		List <ItemStack> costs = (Dwarf.find(player)).calculateTrainingCost(skill);
		for(ItemStack item:costs){
			if (item != null) Out.sendMessage(player, " &2" +item.getAmount() + " " + item.getType()+ "&6  --" , " &6-- ");
		}
		return true;
	}
	
	public static boolean printSkillSheet(Dwarf dwarf, Player viewer, String displayName) {
		try {
			String message1;
			String message2 = "";
			String prefix1 = "&6[&dSS&6] ";

			String prefix2 = "&6[&dSS&6] ";	
			message1 = ("&6Printing Skill Sheet for &9" + (displayName == null ? viewer.getName() : displayName) + " Dwarf &6Level is &3" + dwarf.getDwarfLevel());
			sendMessage(viewer, message1, prefix1);
			
			if(dwarf.isElf){
				message2 = ("&fElves &6don't have skills, numbskull");
				sendMessage(viewer, message2, prefix2);
				return true;
			}
			boolean odd = true;
			String untrainedSkills = "&6Untrained Skills: ";
			for (Skill s:dwarf.skills){	
				if(s == null) continue;
				if(s.level == 0) {
					untrainedSkills = untrainedSkills.concat("|&7" + s.displayName+"&6| ");
					continue;
				}
				odd = !odd;
				// the goal here is for every skill sheet line to be 60 characters long.
				// each skill should take 30 characters - no more, no less
				String interim;
				if ( s.level < 10)
					interim = String.format("&6[&30%d&6] &b%.18s", s.level, s.displayName);
				else 
					interim = String.format("&6[&3%d&6] &b%.18s", s.level, s.displayName);
				
				if (!odd) { 
					int interimLen = Util.msgLength(interim);
					int numSpaces = ((140 - interimLen) / 4) - 1;
					for ( int i = 0; i < numSpaces; i++ )
						interim = interim.concat(" ");
					interimLen = 140 - interimLen - numSpaces*4;
				// 	4 possible cases - need 4, 5, 6, or 7
					if ( interimLen == 4 )
						interim = interim.concat("&0.| &b");
					else if ( interimLen == 5)
						interim = interim.concat("&0'| &b");
					else if ( interimLen == 6)
						interim = interim.concat("&0 | &b");
					else if ( interimLen == 7)
						interim = interim.concat("&0'.| &b");
				}
					
				message2 = message2.concat(interim);
				if (odd) {
					sendMessage(viewer, message2, prefix2);
					message2 = "";
				}
				
			}
			if (!message2.equals(""))
				sendMessage(viewer, message2, prefix2);
			sendMessage(viewer, untrainedSkills, prefix2);
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}	
		
	public static boolean effectInfo(Player player, Effect effect) {
		Out.sendMessage(player, effect.describeLevel((Dwarf.find(player))), "&6[&5" +effect.id+"&6] ");
		Out.sendMessage(player, effect.describeGeneral(), "&6[&5" +effect.id+"&6] ");
		return true;
	}
		
	public static void becameDwarf(Player player) {
		sendMessage(player, Messages.Fixed.PRIMARYRACESUCCESS.message, "&6[DC] ");
	}
	public static void confirmBecomingDwarf(Player player) {
		sendMessage(player, Messages.Fixed.PRIMARYRACECONFIRM.message,"&6[DC] ");
	}
	public static void becameElf(Player player) {
		sendMessage(player, Messages.Fixed.SECONDARYRACESUCCESS.message, "&6[DC] ");
	}	
	public static void alreadyElf(Player player) {
		sendMessage(player, Messages.Fixed.SECONDARYRACEALREADY.message, "&6[DC] ");
	}
	public static void confirmBecomingElf(Player player) {
		sendMessage(player, Messages.Fixed.SECONDARYRACECONFIRM.message,"&6[DC] ");
	}
	
	/**
	 * Used to send messages to one player
	 */
	public static void sendMessage(Player player, String message){
		sendMessage(player, message, "");
	}
	
	/**
	 * Dwarf version
	 */
	public static void sendMessage(Dwarf dwarf, String message){
		sendMessage(dwarf.player, message);
	}

	/**
	 * Used to send messages to one player with a prefix
	 * @return 
	 */
	public static void sendMessage(Player player, String message, String prefix){
		message = parseColors(message);
		prefix = parseColors(prefix);
		messagePrinter(player, message, prefix);
	}
	
	/**
	 * Dwarf version
	 */
	public static void sendMessage(Dwarf dwarf, String message, String prefix){
		sendMessage(dwarf.player, message, prefix);
	}
	
	/**
	 * Used to send messages to many players
	 */
	public static void sendMessage(Player[] playerArray, String message){
		sendMessage(playerArray, message, "");
	}
	
	/**
	 * Used to send messages to many players with a prefix
	 */
	public static void sendMessage(Player[] playerArray, String message, String prefix ){
		for (Player p: playerArray) sendMessage(p, message, prefix);
	}
	
	/**
	 * Used to send messages to all players on a server
	 */
	public static void sendBroadcast(Server server, String message){
		sendBroadcast(server, message, "");
	}
	
	/**
	 * Used to send messages to all players on a server with a prefix TODO
	 */
	public static void sendBroadcast(Server server, String message, String prefix){
		Player[] playerArray = server.getOnlinePlayers();
		sendMessage(playerArray, message, prefix);
	}
	
	/**
	 * Removes carriage returns from strings and passes separate 
	 * @param player
	 * @param message
	 * @param prefix
	 */
	private static void messagePrinter(Player player, String message, String prefix){
		String[] lines = message.split("/n");
		String lastColor = "";
		for (String line:lines) lastColor = linePrinter(player, lastColor.concat(line), prefix);
	}
	
	/**
	 * Used to parse and send multiple line messages 
	 * Sends actual output commands
	 */
	private static String linePrinter(Player player, String message, String prefix){
		int messageSectionLength = lineLength - Util.msgLength(prefix);
		String currentLine = "";		
		String words[] = message.split(" ");
		String lastColor = "";
		int lineTotal = 0;
		for (String word: words){
			if (Util.msgLength(currentLine)+Util.msgLength(word) <= messageSectionLength){ 
				currentLine = currentLine.concat(word+" ");
			}
			else {
				player.sendMessage(prefix.concat(lastColor + currentLine).trim());
				lineTotal++;
				if (lineTotal >= maxLines) return lastColor;
				lastColor = lastColor(lastColor + currentLine);
				currentLine = word+ " ";
			}
		}
		player.sendMessage(prefix.concat(lastColor + currentLine).trim());
		lastColor = lastColor(lastColor + currentLine);
		return lastColor;
	}
	
	private static String lastColor(String currentLine) {
		String lastColor = "";
		int lastIndex = currentLine.lastIndexOf("§");
		if(lastIndex == currentLine.length()) return "§";
		if(lastIndex != -1){
			lastColor = currentLine.substring(lastIndex,lastIndex+2);
		};
		return lastColor;
	}

	/**
	 * dwarf version
	 */
	static void messagePrinter(Dwarf dwarf, String message, String prefix){
		messagePrinter(dwarf.player,message,prefix);
	}
	
	/** 
	 * Finds &0-F in a string and replaces it with the color symbol
	 */
	static String parseColors(String message){	
		if (message == null){
			if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: parsing null message!");
			return null;
		}
		if (DwarfCraft.debugMessagesThreshold < -1) System.out.println("Debug Message: parsing colors for: "+ message);
		for(int i =0; i<message.length();i++){
			try{
				if(message.charAt(i)=='&'){
					if (message.charAt(i+1)=='0') message = message.replace("&0", "§0");
					else if (message.charAt(i+1)=='1') message = message.replace("&1", "§1");
					else if (message.charAt(i+1)=='2') message = message.replace("&2", "§2");
					else if (message.charAt(i+1)=='3') message = message.replace("&3", "§3");			
					else if (message.charAt(i+1)=='4') message = message.replace("&4", "§4");
					else if (message.charAt(i+1)=='5') message = message.replace("&5", "§5");			
					else if (message.charAt(i+1)=='6') message = message.replace("&6", "§6");
					else if (message.charAt(i+1)=='7') message = message.replace("&7", "§7");			
					else if (message.charAt(i+1)=='8') message = message.replace("&8", "§8");
					else if (message.charAt(i+1)=='9') message = message.replace("&9", "§9");			
					else if (message.charAt(i+1)=='a') message = message.replace("&a", "§a");
					else if (message.charAt(i+1)=='b') message = message.replace("&b", "§b");
					else if (message.charAt(i+1)=='c') message = message.replace("&c", "§c");
					else if (message.charAt(i+1)=='d') message = message.replace("&d", "§d");
					else if (message.charAt(i+1)=='e') message = message.replace("&e", "§e");
					else if (message.charAt(i+1)=='f') message = message.replace("&f", "§f");
					else if (message.charAt(i+1)=='p') message = message.replace("&p", Messages.primaryRaceName);
					else if (message.charAt(i+1)=='q') message = message.replace("&q", Messages.primaryRacePlural);
					else if (message.charAt(i+1)=='s') message = message.replace("&s", Messages.secondaryRaceName);
					else if (message.charAt(i+1)=='t') message = message.replace("&t", Messages.secondaryRacePlural);
					else message = message.replaceFirst("&", " AND ");
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		message = message.replaceAll(" AND ", "&");
		return message;
	}

	/**
	 * Sends a welcome message based on race of player joining. Broadcasts to the whole server
	 * @param server
	 * @param dwarf
	 */
	public static void welcome(Server server, Dwarf dwarf) {
		try {
			String raceName = "";
			if(dwarf.isElf) raceName = "&f&s";
			else raceName = "&9&p";
			sendBroadcast(server, "&fWelcome, "+raceName+" &6"+dwarf.player.getName() ,"&6[DC]         ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
