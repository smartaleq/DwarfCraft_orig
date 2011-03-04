package com.smartaleq.bukkit.dwarfcraft.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.smartaleq.bukkit.dwarfcraft.ConfigManager;
import com.smartaleq.bukkit.dwarfcraft.DataManager;
import com.smartaleq.bukkit.dwarfcraft.Dwarf;
import com.smartaleq.bukkit.dwarfcraft.DwarfCraft;
import com.smartaleq.bukkit.dwarfcraft.DwarfTrainer;
import com.smartaleq.bukkit.dwarfcraft.Effect;
import com.smartaleq.bukkit.dwarfcraft.Skill;
import com.smartaleq.bukkit.dwarfcraft.Util;

public class Out {
	
/*
 * Messaging Statics	
 */
	static final int lineLength = 340; //pixels
	static final int maxLines = 10;
	
	public enum FixedColor{
		DarkRed 	("§4"),
		Red 		("§c"),
		Yellow		("§e"),
    	Gold		("§6"),
    	DarkGreen	("§2"),
    	Green		("§a"),
    	Teal		("§b"),
    	DarkTeal	("§3"),
    	Blue		("§9"),
    	Pink		("§d"),
    	Purple		("§5"),
    	White		("§f"),
    	Gray		("§7"),
		DarkGray	("§8"),
		Black		("§0"),
	
		;
		
		String color;
		FixedColor(String color){
			this.color = color;
		}	
		public String toString(){
			return color;
		}
		
	}
	
	public enum Color{
		DC			(FixedColor.Gold),
		COMMAND		(FixedColor.DarkRed),
		HELP		(FixedColor.Pink),
		BADAMOUNT 	(FixedColor.Red),
		GOODAMOUNT	(FixedColor.Green),
		NORMALAMOUNT(FixedColor.Yellow),
		
		ITEM		(FixedColor.DarkGreen),
		SKILLID		(FixedColor.DarkTeal),
		SKILLLEVEL	(FixedColor.Teal),
		EFFECT		(FixedColor.Purple),
		
		DWARF		(FixedColor.Blue),
		ELF			(FixedColor.White),
		
		TRAINER		(FixedColor.Gray),
		
		;
		FixedColor fcolor;
		Color(FixedColor fixedcolor){
			this.fcolor = fixedcolor;
		}
		public String toString(){
			return fcolor.toString();
		}
	}
	
	public static void generalInfo(CommandSender sender) {
		if(sender instanceof Player) Out.sendMessage((Player)sender, "&d" + Messages.GeneralInfo, "&6[&d?&6] ");
		else System.out.println(Messages.GeneralInfo);
	}
		
	// need to learn how to do this in current command structure
//	public static boolean commandHelp(CommandSender sender, CommandInfo c) {
//		Out.sendMessage(player, "&d" + c.helpText, "&6[&d?&6] " );
//		return true;
//	}
	
	public static void info(CommandSender sender) {
		Out.sendMessage(sender, Messages.GeneralInfo, "&6[&dInfo&6] ");
	}
	

	public static void rules(CommandSender sender) {
		Out.sendMessage(sender, Messages.ServerRules, "&6[&dRules&6] ");
	}


//	public static boolean commandList(CommandSender sender, int number) {
//		if (number == 1) {
//			Out.sendMessage(sender, Messages.Fixed.COMMANDLIST1.message,"&6[&d?&6] " );
//			return true;
//		}
//		if (number == 2){
//			Out.sendMessage(sender, Messages.Fixed.COMMANDLIST2.message,"&6[&d?&6] " );
//			return true;
//		}
//		return false;
//	}

	public static void tutorial(CommandSender sender, int i) {
		if (i == 1) Out.sendMessage(sender, Messages.Fixed.TUTORIAL1.message,"&6[&dDC&6] ");
		else if (i == 2) Out.sendMessage(sender, Messages.Fixed.TUTORIAL2.message,"&6[&dDC&6] ");
		else if (i == 3) Out.sendMessage(sender, Messages.Fixed.TUTORIAL3.message,"&6[&dDC&6] ");
		else if (i == 4) Out.sendMessage(sender, Messages.Fixed.TUTORIAL4.message,"&6[&dDC&6] ");
		else if (i == 5) Out.sendMessage(sender, Messages.Fixed.TUTORIAL5.message,"&6[&dDC&6] ");
		else if (i == 6) Out.sendMessage(sender, Messages.Fixed.TUTORIAL6.message,"&6[&dDC&6] ");
	}
	
	public static boolean printSkillInfo(CommandSender sender,  Skill skill,Dwarf dwarf, int maxTrainLevel){
		//general line
		Out.sendMessage(sender, "&6  Skillinfo for &b"+skill.displayName+"&6 [&b"+skill.id+"&6] || Your level &3"+skill.level);
		//effects lines
		Out.sendMessage(sender, "&6[&5EffectID&6]&f------&6[Effect]&f------" );
		for(Effect effect:skill.effects){
			if (effect != null)
				Out.sendMessage(sender, effect.describeLevel(dwarf), "&6[&5"+effect.id+"&6] ");
		}
		//training lines	
		if (skill.level == 30) {
			Out.sendMessage(sender, "&6---This skill is maximum level, no training available---");
			return true;
		}
		if (skill.level > maxTrainLevel){
			Out.sendMessage(sender, "&6---You're as skilled as me, you need a more advanced trainer!--");
			return true;
		}
		Out.sendMessage(sender, "&6---Train costs for level &3"+(skill.level+1));
		List <ItemStack> costs = dwarf.calculateTrainingCost(skill);
		for(ItemStack item:costs){
			if (item != null) Out.sendMessage(sender, " &2" +item.getAmount() + " " + item.getType()+ "&6  --" , " &6-- ");
		}
		return true;
	}
	
	public static void printSkillSheet(Dwarf dwarf, CommandSender sender, String displayName, boolean printFull) {
		String message1;
		String message2 = "";
		String prefix1 = "&6[&dSS&6] ";

		String prefix2 = "&6[&dSS&6] ";	
		message1 = ("&6Printing Skill Sheet for &9" + (displayName == null ? dwarf.player.getName() : displayName) + " Dwarf &6Level is &3" + dwarf.getDwarfLevel());
		sendMessage(sender, message1, prefix1);
		
		if(dwarf.isElf){
			message2 = ("&fElves &6don't have skills, numbskull");
			sendMessage(sender, message2, prefix2); return;
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
				int numSpaces = ((124 - interimLen) / 4) - 1;
				for ( int i = 0; i < numSpaces; i++ )
					interim = interim.concat(" ");
				interimLen = 124 - interimLen - numSpaces*4;
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
				sendMessage(sender, message2, prefix2);
				message2 = "";
			}
			
		}
		if (!message2.equals(""))
			sendMessage(sender, message2, prefix2);
		if(printFull)sendMessage(sender, untrainedSkills, prefix2);
	}	
		
	public static boolean effectInfo(CommandSender sender, Dwarf dwarf, Effect effect) {
		Out.sendMessage(sender, effect.describeLevel(dwarf), "&6[&5" +effect.id+"&6] ");
		Out.sendMessage(sender, effect.describeGeneral(), "&6[&5" +effect.id+"&6] ");
		return true;
	}
		
	public static void becameDwarf(CommandSender sender,Dwarf dwarf) {
		sendMessage(sender, Messages.Fixed.PRIMARYRACESUCCESS.message, "&6[DC] ");
	}
	public static void confirmBecomingDwarf(CommandSender sender,Dwarf dwarf) {
		sendMessage(sender, Messages.Fixed.PRIMARYRACECONFIRM.message,"&6[DC] ");
	}
	public static void becameElf(CommandSender sender,Dwarf dwarf) {
		sendMessage(sender, Messages.Fixed.SECONDARYRACESUCCESS.message, "&6[DC] ");
	}	
	public static void alreadyElf(CommandSender sender, Dwarf dwarf) {
		sendMessage(sender, Messages.Fixed.SECONDARYRACEALREADY.message, "&6[DC] ");
	}
	public static void confirmBecomingElf(CommandSender sender,Dwarf dwarf) {
		sendMessage(sender, Messages.Fixed.SECONDARYRACECONFIRM.message,"&6[DC] ");
	}
	
	/**
	 * Used to send messages to one player or console
	 */
	public static void sendMessage(CommandSender sender, String message) {
		sendMessage(sender, message, "");
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
	public static void sendMessage(CommandSender sender, String message, String prefix){
		if (sender instanceof Player){
			message = parseColors(message);
			prefix = parseColors(prefix);
			messagePrinter((Player)sender, message, prefix);
		}
		else{
			message = stripColors(message);
			prefix = stripColors(prefix);
			messagePrinter(sender, message, prefix);
		}
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
	 * Used to send messages to all players on a server with a prefix
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
	private static void messagePrinter(CommandSender sender, String message, String prefix){
		String[] lines = message.split("/n");
		String lastColor = "";
		for (String line:lines) lastColor = consoleLinePrinter(sender, line, prefix);
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
		for (String line:lines) lastColor = playerLinePrinter(player, lastColor.concat(line), prefix);
	}
	
	
	/**
	 * Used to parse and send multiple line messages 
	 * Sends actual output commands
	 */
	private static String playerLinePrinter(Player player, String message, String prefix){
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
	private static String consoleLinePrinter(CommandSender sender, String line,
			String prefix) {
		System.out.print(prefix.concat(line));
		return null;
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
	 * Finds &0-F in a string and replaces it with the color symbol
	 */
	static String parseColors(String message){	
		if (message == null){
			if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: parsing null message!");
			return null;
		}
		if (DwarfCraft.debugMessagesThreshold < -1) System.out.println("Debug Message: parsing colors for: "+ message);
		for(int i =0; i<message.length();i++){
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
		message = message.replaceAll(" AND ", "&");
		return message;
	}
	
	private static String stripColors(String message) {
		if (message == null){
			if (DwarfCraft.debugMessagesThreshold < 2) System.out.println("Debug Message: stripping colors from null message!");
			return null;
		}
		if (DwarfCraft.debugMessagesThreshold < -1) System.out.println("Debug Message: stripping colors from: "+ message);
		for(int i =0; i<message.length();i++){
			if(message.charAt(i)=='&'){
				if (message.charAt(i+1)=='0') message = message.replace("&0", "§0");
				else if (message.charAt(i+1)=='1') message = message.replace("&1", "");
				else if (message.charAt(i+1)=='2') message = message.replace("&2", "");
				else if (message.charAt(i+1)=='3') message = message.replace("&3", "");			
				else if (message.charAt(i+1)=='4') message = message.replace("&4", "");
				else if (message.charAt(i+1)=='5') message = message.replace("&5", "");			
				else if (message.charAt(i+1)=='6') message = message.replace("&6", "");
				else if (message.charAt(i+1)=='7') message = message.replace("&7", "");			
				else if (message.charAt(i+1)=='8') message = message.replace("&8", "");
				else if (message.charAt(i+1)=='9') message = message.replace("&9", "");			
				else if (message.charAt(i+1)=='a') message = message.replace("&a", "");
				else if (message.charAt(i+1)=='b') message = message.replace("&b", "");
				else if (message.charAt(i+1)=='c') message = message.replace("&c", "");
				else if (message.charAt(i+1)=='d') message = message.replace("&d", "");
				else if (message.charAt(i+1)=='e') message = message.replace("&e", "");
				else if (message.charAt(i+1)=='f') message = message.replace("&f", "");
				else if (message.charAt(i+1)=='p') message = message.replace("&p", Messages.primaryRaceName);
				else if (message.charAt(i+1)=='q') message = message.replace("&q", Messages.primaryRacePlural);
				else if (message.charAt(i+1)=='s') message = message.replace("&s", Messages.secondaryRaceName);
				else if (message.charAt(i+1)=='t') message = message.replace("&t", Messages.secondaryRacePlural);
				else message = message.replaceFirst("&", " AND ");
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

	public static void printTrainerList(CommandSender sender) {
		if ( DataManager.trainerList.isEmpty() ) {
			sendMessage(sender, "There are currently no trainers.");
			System.out.println("There are currently no trainers.");
		}
		else { 
			for ( Iterator<Map.Entry<String, DwarfTrainer>> i = DataManager.trainerList.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry<String, DwarfTrainer> pairs = i.next();
				DwarfTrainer d = (DwarfTrainer)(pairs.getValue());
				if ( d.isGreeter() ) {
					sendMessage(sender, "Greeter ID: " + d.getUniqueId() + " Name: " + d.getName());
				}
				else {
					String skillName = null;
					for(Skill s:ConfigManager.getAllSkills()) if (s.id == d.getSkillTrained()) skillName = s.displayName;
					sendMessage(sender, "Trainer ID: " + d.getUniqueId() + " Name: " + d.getName() + " Trains: (" + d.getSkillTrained() + ") " + skillName);
				}
			}
		}
	}

	public static void race(Player player) {
		// TODO Auto-generated method stub
		
	}


}
