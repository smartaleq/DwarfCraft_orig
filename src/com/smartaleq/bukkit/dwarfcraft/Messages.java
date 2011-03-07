package com.smartaleq.bukkit.dwarfcraft;

final class Messages {
	
	
	//String messages loaded from messages.config
	protected static String GeneralInfo = null;
	protected static String ServerRules = null;
	
	protected static String primaryRaceName = "Dwarf";
	protected static String primaryRacePlural = "Dwarves";
	protected static String secondaryRaceName = "Elf";
	protected static String secondaryRacePlural = "Elves";
	
	protected static String PRIMARYRACESUCCESS = null;
	protected static String PRIMARYRACECONFIRM = null;
	protected static String SECONDARYRACESUCCESS = null;
	protected static String SECONDARYRACECONFIRM = null;
	protected static String SECONDARYRACEALREADY = null;
	
		
	//String messages fixed for DwarfCraft, and backup messages when loading fails.
	protected enum Fixed{
		INFO(
				"This is a dummy General Help Message, please place a message in your messages.config"), 
		SERVERRULESMESSAGE(
				"This is a dummy Server Rules Message, please place a message in your messages.config"), 
		GENERALHELPMESSAGE(
				"&dWelcome to DwarfCraft. You are a &p with "
					+ "a set of skills that let you do minecraft tasks better."
					+ " When you first start, things may be more difficult"
					+ " than you are used to, but as you level your skills up you "
					+ "will be much more productive than normal players. Each of "
					+ "the skills listed in your skillsheet(&4/dc skillsheet&d) "
					+ "has multiple effects. You can find out more about "
					+ "training a skill and its effects with &4/dc skillinfo "
					+ "<skillname or id>&d."), 
		COMMANDLIST1(
				""), 
		COMMANDLIST2(
				""),
	
		TUTORIAL1(
				"&fWelcome to the dwarfcraft tutorial. To get started, type &4/skillsheet full&f."
					+ " Afterwards, type &4/tutorial 2&f to continue."), 
		TUTORIAL2(
				"&fYour Skillshset lists all skills that are affecting you and their level. Lets"
					+ " find out more about the Demolitionist skill. Type &4/skillinfo Demolit&f or"
					+ " &4/skillinfo 63&f. Continue with &4/tutorial 3&f"), 
		TUTORIAL3(
				"&fThe skillinfo shows that your low level demotionist skill allows you to craft"
					+ " a normal amount of TNT. If you increase this skill enough, you'll get 2 or "
					+ "even more TNT per craft. The skill also affects damage you take from explosions. "
					+ "Below that, it shows how to train the skill. Find a nearby trainer and left click " 
					+ "them to get more information about the skill. Right click to attempt training" 
					+ ", then continue with &4/tutorial 4&f"), 
		TUTORIAL4(
				"&fWhen you tried to train the skill, it showed what training cost was missing. All "
					+ "skills train for a cost in relevant materials. The first few levels cost little,"
					+ "but becoming a master is very challenging. Continue with &4/tutorial 5&f"), 
		TUTORIAL5(
				"&fMost trainers can only take you to a limited level, you'll need to seek out the "
					+ "best trainers in the world to eventually reach level 30 in a skill. Go gather"
					+ "some dirt, stone, or logs and try to train up a relevant skill, using what" 
					+ "you have learned, then continue with &4/tutorial 6&f"), 
		TUTORIAL6(
				"&fYou now know the basic commands you need to succeed and develop as a " + primaryRaceName + ". " 
					+ "To find out more, use &4/help&f and &4/help <command>&f."),
	
		PRIMARYRACESUCCESS(
				"&6Congratulations, you're now a hearty &9&p&6 with level 0 skills!"),
		PRIMARYRACECONFIRM(
				"&6To become a &9&p&6 you need to confirm with &4/dc REALLYmake&p"
					+ "&6- this will reset your skills to 0!"), 
		SECONDARYRACESUCCESS(
				"&6I'm sorry to say this... really I must apologize... there was a problem "
					+ "in surgery and you're now... ahem... an &f&s&6."), 
		SECONDARYRACECONFIRM(
				"&6If you really want to remove all your skills, you'll have to say you &4'/dc "
					+ "REALLYmake&s&6, which is an altogether bad idea!"), 
		SECONDARYRACEALREADY(
				"&6You're already an &f&s&6. No wonder you're so confused!"),
	
//		ERRORBADINPUT("(Input Error)Could not understand your input, try again"), 
//		ERRORNOTOP("(Not OP)You are not authorized to use that command"), 
//		ERRORINVALIDDWARFNAME("(Input Error)Could not find that player"),
//		ERRORINVALIDSKILLLEVEL("(Input Error)Skill levels must be between 0 and 30"), 
//		ERRORNOTANUMBER("(Input Error)Where an integer was expected something else was found"), 
//		ERRORMISSINGINPUT("(Input Error)Could not find all the arguments required for that command"), 
//		ERRORNOTVALIDSKILLINPUT("(Input Error)To find a skill you must input either the name or ID of an existing skill"),
//		ERRORNOTVALIDEFFECTINPUT("(Input Error)To find an effect you must input either the name or ID of an existing effect"), 
//		ERRORTOOMANYINPUTS("(Input Error)You gave me too many arguments. Please use underscores for spaces in skill names and no extra inputs."), 
//		ERRORCOMMANDNOTFOUND("(Input Error)Could not understand your input as a command."),		
//		
		;
	
		private String message;
	
		protected String getMessage() { return message; }
		
		private Fixed(String message) {
			this.message = message;
		}
	}
}
