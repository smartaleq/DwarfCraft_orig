package com.smartaleq.bukkit.dwarfcraft.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.smartaleq.bukkit.dwarfcraft.*;
import com.smartaleq.bukkit.dwarfcraft.ui.DCCommandException.Type;
import com.smartaleq.bukkit.dwarfcraft.ui.Out;

public class DCCommand extends Command{
	
	public DCCommand(String name) {
		super(name);
	}
		
	private CommandSender sender;

	/**
	 * This command parses all inputs for commands and sends appropriate objects to the action or output methods.
	 * 
	 */
	public boolean execute(CommandSender sender, String commandLabel, String[] args){
		if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started execute");
		String commandName = getName();
		this.sender = sender;
		
		//Commands prefixed with DC will stillwork, but may soon become unsupported
		if(commandName.equalsIgnoreCase("DC")){
			commandName = args[0];
			String[] tempArgs = new String[args.length-1]; 
			for (int i=1;i<args.length;i++) tempArgs[i-1] = args[i]; 
			args = tempArgs;
		}
		
		CommandParser parser= new CommandParser(sender, args);	
		List<Object> desiredArguments = new ArrayList<Object>();
		List<Object> outputList = null;
		try {
			if (commandName.equalsIgnoreCase("debug")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'debug'");
				if(!sender.isOp()) throw new DCCommandException(Type.NEEDPERMISSIONS);
				Integer i = 0;
				desiredArguments.add(i);
				outputList = parser.parse(desiredArguments, false);
				debug((Integer) outputList.get(0));	return true;
			} else if (commandName.equalsIgnoreCase("dchelp")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'help'");
				//put in parser for command string in arg[0]
				Out.sendMessage(sender, Messages.GeneralInfo); 
				return true;
			} else if (commandName.equalsIgnoreCase("info")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'info'");
				outputList = parser.parse(desiredArguments, false);
				Out.info(sender); return true;
			} else if (commandName.equalsIgnoreCase("rules")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'rules'");
				outputList = parser.parse(desiredArguments, false);
				Out.rules(sender); return true;
			} else if (commandName.equalsIgnoreCase("tutorial")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'tutorial'");
				int page = 0;
				desiredArguments.add(page);
				try{
					outputList = parser.parse(desiredArguments, false);
					page = (Integer) outputList.get(0);
					}
				catch(DCCommandException e) {
					if (e.type == Type.TOOFEWARGS) page = 0;
					else throw e;
				}
				if (page<0 || page >6) throw new DCCommandException(Type.PAGENUMBERNOTFOUND);
				Out.tutorial(sender, page); return true;
			} 
//			else if (commandName.equalsIgnoreCase("commandlist")) {
//				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'commandlist'");
//				outputList = parser.parse(desiredArguments);} 
			else if (commandName.equalsIgnoreCase("skillsheet")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'skillsheet'");
				boolean printFull = false;
				if (args.length == 0 && sender instanceof Player) {
					Out.printSkillSheet(Dwarf.find((Player) sender), sender, ((Player) sender).getName() , printFull);
					return true;
				} else if (args.length == 0) throw new DCCommandException(Type.CONSOLECANNOTUSE);
				if (args[0].equalsIgnoreCase("-f") ||args[0].equalsIgnoreCase("full") ) {
					printFull = true;
					desiredArguments.add(args[0]);
				}
				Dwarf dwarf = new Dwarf(null);
				desiredArguments.add(dwarf);
				String displayName = null;
				try{ 
					outputList = parser.parse(desiredArguments,false);
					if (outputList.get(0) instanceof String) dwarf = (Dwarf) outputList.get(1);
					else dwarf = (Dwarf) outputList.get(0);
					displayName = dwarf.player.getDisplayName();
				}
				catch(DCCommandException dce) {
					if (dce.type == Type.PARSEDWARFFAIL) {
						if (sender instanceof Player) dwarf = Dwarf.find((Player)sender);
						else throw new DCCommandException(Type.CONSOLECANNOTUSE);}
					else throw dce;
				}
				catch(NullPointerException e) {
					if(printFull) displayName = args[1];
					else displayName = args[0];
				}
				Out.printSkillSheet(dwarf, sender, displayName, printFull);
				return true;
			} else if (commandName.equalsIgnoreCase("skillinfo")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'skillinfo'");
				Dwarf dwarf = new Dwarf(null);
				Skill skill = new Skill(0, null, 0, null, null, 0, 0, null, 0, 0, null, 0, 0, null);
				desiredArguments.add(dwarf);
				desiredArguments.add(skill);
				try{ 
					outputList = parser.parse(desiredArguments, false);
					if (args.length>outputList.size()) throw new DCCommandException(Type.TOOMANYARGS);
					skill = (Skill) outputList.get(1);
					dwarf = (Dwarf) outputList.get(0);
				}
				catch(DCCommandException dce) {
					if (dce.type == Type.PARSEDWARFFAIL || dce.type == Type.TOOFEWARGS) {
						desiredArguments.remove(0);
						outputList = parser.parse(desiredArguments, true);
						skill = (Skill) outputList.get(0);
						if(!(sender instanceof Player)) throw new DCCommandException(Type.CONSOLECANNOTUSE);
						dwarf = Dwarf.find((Player) sender);
					}
					else throw dce;
				}
				Out.printSkillInfo(sender, skill, dwarf, 31);
				return true;
			} else if (commandName.equalsIgnoreCase("effectinfo")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'effectinfo'");
				Dwarf dwarf = new Dwarf(null);
				Effect effect = new Effect(0, 0, 0, 0, 0, 0, false, false, 0, 0, 0, 0, null, 0, 0, false, null);
				desiredArguments.add(dwarf);
				desiredArguments.add(effect);
				try{ 
					outputList = parser.parse(desiredArguments, false);
					if (args.length>outputList.size()) throw new DCCommandException(Type.TOOMANYARGS);
					effect = (Effect) outputList.get(1);
					dwarf = (Dwarf) outputList.get(0);
				}
				catch(DCCommandException dce) {
					if (dce.type == Type.PARSEDWARFFAIL || dce.type == Type.TOOFEWARGS) {
						desiredArguments.remove(0);
						outputList = parser.parse(desiredArguments, true);
						effect = (Effect) outputList.get(0);
						if(!(sender instanceof Player)) throw new DCCommandException(Type.CONSOLECANNOTUSE);
						dwarf = Dwarf.find((Player) sender);
					}
					else throw dce;
				}
				Out.effectInfo(sender, dwarf, effect);
				return true;
			} else if (commandName.equalsIgnoreCase("race")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1#: started command 'race'");
				if(args.length==0 && sender instanceof Player) Out.race((Player)sender);
				Dwarf dwarf = new Dwarf(null);
				String newRace = "newRace";
				boolean isElf = false;
				Boolean confirmed = false;
				desiredArguments.add(dwarf);
				desiredArguments.add(newRace);
				desiredArguments.add(confirmed);
				try{
					outputList = parser.parse(desiredArguments, false);
					dwarf = (Dwarf) outputList.get(0);
					isElf = (Boolean) outputList.get(1);
					confirmed = (Boolean) outputList.get(2);
					if(sender.isOp()) race(isElf, confirmed, dwarf);
				}catch (DCCommandException e) { 
					if (e.type == Type.TOOFEWARGS) {
						desiredArguments.remove(0);
						desiredArguments.add(dwarf);
						outputList = parser.parse(desiredArguments, true);
						dwarf = (Dwarf) outputList.get(2);
						isElf = (Boolean) outputList.get(0);
						confirmed = (Boolean) outputList.get(1);
					}
				}
				catch(IndexOutOfBoundsException f){
					Out.race((Player) sender);
					return true;
				}
				race(isElf, confirmed, dwarf);
			} else if (commandName.equalsIgnoreCase("setskill")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'setskill'");
				Dwarf dwarf = new Dwarf(null);
				Skill skill = new Skill(0, null, 0, null, null, 0, 0, null, 0, 0, null, 0, 0, null);
				int level = 0;
				desiredArguments.add(dwarf);
				desiredArguments.add(skill);
				desiredArguments.add(level);
				try{
					outputList = parser.parse(desiredArguments, false);
					dwarf = (Dwarf) outputList.get(0);
					skill = (Skill) outputList.get(1);
					level = (Integer) outputList.get(2);
				}catch (DCCommandException e) { 
					if (e.type == Type.TOOFEWARGS) {
						desiredArguments.remove(0);
						desiredArguments.add(dwarf);
						outputList = parser.parse(desiredArguments, true);
						dwarf = (Dwarf) outputList.get(2);
						skill = (Skill) outputList.get(0);
						level = (Integer) outputList.get(1);
					}
				}
				setSkill(null, commandName, null, 0);
				return true;
			} else if (commandName.equalsIgnoreCase("creategreeter")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'creategreeter'");
				
				if(!(sender.isOp())) throw new DCCommandException(Type.NEEDPERMISSIONS);
				String uniqueId= "UniqueIdAdd";
				String name= "Name";
				String greeterMessage = "GreeterMessage";
				Dwarf dwarf = new Dwarf(null);
				desiredArguments.add(dwarf); 
				desiredArguments.add(uniqueId);
				desiredArguments.add(name);
				desiredArguments.add(greeterMessage);
				try{
					outputList = parser.parse(desiredArguments, false);
					dwarf = (Dwarf) outputList.get(0);
					uniqueId = (String) outputList.get(1);
					name = (String) outputList.get(2);
					greeterMessage = (String) outputList.get(3);
				}
				catch (DCCommandException e) {
					if (e.type == Type.TOOFEWARGS){
						if(!(sender instanceof Player)) throw new DCCommandException(Type.CONSOLECANNOTUSE);
						desiredArguments.remove(0);
						outputList = parser.parse(desiredArguments, false);
						uniqueId = (String) outputList.get(0);
						name = (String) outputList.get(1);
						greeterMessage = (String) outputList.get(2);
						dwarf = (Dwarf) sender;
					}else throw e;
				}
				DwarfTrainer d = new DwarfTrainer(dwarf.player, uniqueId, name, null, null, greeterMessage, true);
				DataManager.insertTrainer(d);
				return true;
			} else if (commandName.equalsIgnoreCase("createtrainer")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'createtrainer'");
				if(!(sender.isOp())) throw new DCCommandException(Type.NEEDPERMISSIONS);
				String uniqueId= "UniqueIdAdd";
				String name = "Name";
				Skill skill = new Skill(0, null, 0, null, null, 0, 0, null, 0, 0, null, 0, 0, null);
				Integer maxSkill = 1;
				Dwarf dwarf = new Dwarf(null);
				desiredArguments.add(dwarf); 
				desiredArguments.add(uniqueId);
				desiredArguments.add(name);
				desiredArguments.add(skill);
				desiredArguments.add(maxSkill);
				if (DwarfCraft.debugMessagesThreshold < 4) System.out.println("DC4: Command looking for argument count:" + desiredArguments.size());
				try{
					outputList = parser.parse(desiredArguments, false);
					dwarf = (Dwarf) outputList.get(0);
					uniqueId = (String) outputList.get(1);
					name = (String) outputList.get(2);
					skill = (Skill) outputList.get(3);
					maxSkill = (Integer) outputList.get(4);
				}
				catch (DCCommandException e) {
					if (e.type == Type.TOOFEWARGS){
						if(!(sender instanceof Player)) throw new DCCommandException(Type.CONSOLECANNOTUSE);
						desiredArguments.remove(0);
						outputList = parser.parse(desiredArguments, false);
						uniqueId = (String) outputList.get(0);
						name = (String) outputList.get(1);
						skill = (Skill) outputList.get(2);
						maxSkill = (Integer) outputList.get(3);
						dwarf = Dwarf.find((Player)sender);
					} else throw e;
				}
				DwarfTrainer d = new DwarfTrainer(dwarf.player, uniqueId, name, skill.getId(), maxSkill, null, false);
				DataManager.insertTrainer(d);
				return true;
			} else if (commandName.equalsIgnoreCase("removetrainer")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'removetrainer'");
				String trainerId = "UniqueIDRmv";
				desiredArguments.add(trainerId);
				outputList = parser.parse(desiredArguments, false);
				trainerId = (String) outputList.get(0);
				DataManager.removeTrainer(trainerId); 
				return true;
			} else if (commandName.equalsIgnoreCase("listtrainers")) {
				if (DwarfCraft.debugMessagesThreshold < 1) System.out.println("DC1: started command 'listtrainers'");
				outputList = parser.parse(desiredArguments, false);
				Out.printTrainerList(sender); 
				return true;
			}
		} catch (DCCommandException e) {
			e.describe(sender);
			sender.sendMessage(this.usageMessage);
			return false;

		}
		return false;
	}
	

	/**
	 * Changes the level of debug reporting in console
	 * @param integer 
	 */
	private void debug(Integer debugLevel) {
		DwarfCraft.debugMessagesThreshold=debugLevel;
		System.out.println("*** DEBUG LEVEL CHANGED TO "+debugLevel+" ***");
		Out.sendBroadcast(sender.getServer(), "Debug messaging level set to "+DwarfCraft.debugMessagesThreshold);
	}

//	private void commands(int i) {
//		return Out.commandList(player, i);
//	}

	/**
	 * Admin Command to change a player's skill.
	 * Syntax: /dc setskill <player> <skill> <level>
	 * <player> is target, <skill> is skill ID or alpha
	 * <level> is desired level in range 0-30
	 */
	private void setSkill(Dwarf dwarf, String name, Skill skill, int skillLevel) {
		skill.level = skillLevel;
		Out.sendMessage(sender, "&aAdmin: &eset skill &b" + skill.displayName + "&e for player &9" + name +"&e to &3" + skillLevel);
		DataManager.saveDwarfData(dwarf);
	}
	
	private void race(boolean elf, boolean confirm, Dwarf dwarf){
		if(elf){
			if (dwarf.isElf) Out.alreadyElf(sender, dwarf);
			else if (confirm) Out.becameElf(sender, dwarf);
			else Out.confirmBecomingElf(sender, dwarf);
		}
		else{
			if (dwarf.isElf) dwarf.makeElfIntoDwarf(); 
			else if (confirm) {
				Out.becameDwarf(sender, dwarf);
				dwarf.makeElfIntoDwarf();
			}
			else {
				Out.confirmBecomingDwarf(sender,dwarf);
			}
		}
	}

}
