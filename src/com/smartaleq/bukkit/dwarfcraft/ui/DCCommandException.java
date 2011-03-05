package com.smartaleq.bukkit.dwarfcraft.ui;

import org.bukkit.command.CommandSender;

public class DCCommandException extends Throwable{
	
	Type type;
	
	public DCCommandException() {
	}
	public DCCommandException(Type type) {
	this.type = type;
	}
	
	private static final long serialVersionUID = 7319961775971310701L;

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
	
	public void describe(CommandSender sender) {
		if (type == Type.PARSEEFFECTFAIL) Out.sendMessage(sender, "Could not understand your effect input (Use an ID)");
		else if (type == Type.TOOMANYARGS) Out.sendMessage(sender, "You gave too many arguments, use:");
		else if (type == Type.TOOFEWARGS) Out.sendMessage(sender, "You gave too few arguments, use:");
		else if (type == Type.TOOMANYARGS) Out.sendMessage(sender, "You gave too many arguments");
		else if (type == Type.TOOMANYARGS) Out.sendMessage(sender, "You gave too many arguments");
		else System.out.println("Unhandled DCCommandException: " +type);
	}
	
}
