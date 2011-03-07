package com.smartaleq.bukkit.dwarfcraft;

import org.bukkit.command.CommandSender;

public class DCCommandException extends Throwable{
	
	private Type type;
	private final DwarfCraft plugin;
	
	protected DCCommandException(final DwarfCraft plugin) {
		this.plugin = plugin;
	}
	protected DCCommandException(final DwarfCraft plugin, Type type) {
		this.plugin = plugin;
		this.type = type;
	}
	
	private static final long serialVersionUID = 7319961775971310701L;

	protected Type getType() { return type; }
	public enum Type{
		TOOFEWARGS,
		TOOMANYARGS,
		PARSEDWARFFAIL,
		PARSELEVELFAIL,
		PARSESKILLFAIL,
		PARSEEFFECTFAIL,
		EMPTYPLAYER,
		COMMANDUNRECOGNIZED, 
		LEVELOUTOFBOUNDS, 
		PARSEINTFAIL, 
		PAGENUMBERNOTFOUND, 
		CONSOLECANNOTUSE, 
		NEEDPERMISSIONS, 
		NOGREETERMESSAGE, 
		NPCIDINUSE, 
		PARSEPLAYERFAIL, 
		NPCIDNOTFOUND, 
		PARSERACEFAIL,
		
		
	}
	
	protected void describe(CommandSender sender) {
		if (type == Type.PARSEEFFECTFAIL) plugin.getOut().sendMessage(sender, "Could not understand your effect input (Use an ID)");
		else if (type == Type.TOOMANYARGS) plugin.getOut().sendMessage(sender, "You gave too many arguments, use:");
		else if (type == Type.TOOFEWARGS) plugin.getOut().sendMessage(sender, "You gave too few arguments, use:");
		else if (type == Type.TOOMANYARGS) plugin.getOut().sendMessage(sender, "You gave too many arguments");
		else if (type == Type.TOOMANYARGS) plugin.getOut().sendMessage(sender, "You gave too many arguments");
		else System.out.println("Unhandled DCCommandException: " +type);
	}
	
}
