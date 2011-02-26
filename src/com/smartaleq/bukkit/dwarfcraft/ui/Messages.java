package com.smartaleq.bukkit.dwarfcraft.ui;

public class Messages {
	
	
	//String messages loaded from messages.config
	public static String GeneralInfo = null;
	public static String ServerRules = null;
	
	public static String primaryRaceName = "Dwarf";
	public static String primaryRacePlural = "Dwarves";
	public static String secondaryRaceName = "Elf";
	public static String secondaryRacePlural = "Elves";
	
	public static String PRIMARYRACESUCCESS = null;
	public static String PRIMARYRACECONFIRM = null;
	public static String SECONDARYRACESUCCESS = null;
	public static String SECONDARYRACECONFIRM = null;
	public static String SECONDARYRACEALREADY = null;
	
	
	
	
	
	
	//String messages fixed for DwarfCraft, and backup messages when loading fails.
	public enum Fixed{
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
				"&dhelp &6|&d info &6|&d  commands &6|&d  comands2 &6|&d  tutorial &6|&d  "
					+ "skillinfo skillsheet &6|&d  effectinfo &6|&d  train "
					+ "schoollist(schools) &6|&d  schoolinfo(school) &6|&d  "
					+ "make"+primaryRaceName+" &6|&d  make"+secondaryRaceName+" &6|&d  "
					+ "here"), 
		COMMANDLIST2(
				"setSkill createschool removeschool"),
	
		TUTORIAL1(
				"&fWelcome to the dwarfcraft tutorial. To get started, type &4/dc skillsheet&f."
					+ " Afterwards, type &4/dc tutorial2&f to continue."), 
		TUTORIAL2(
				"&fYour Skillshset lists all skills that are affecting you and their level. Lets"
					+ " find out more about the Demolitionist skill. Type &4/dc skillinfo Demolit&f or"
					+ " &4/dc skillinfo 63&f. Continue with &4/dc tutorial3&f"), 
		TUTORIAL3(
				"&fThe skillinfo shows that your low level demotionist skill allows you to craft"
					+ " a normal amount of TNT. If you increase this skill enough, you'll get 2 or "
					+ "even more TNT per craft. The skill also affects damage you take from explosions. "
					+ "Below that, it shows how to train the skill. Go inside the Specialist trainer "
					+ "in town and use &4/dc train 63&f. Continue with &4/dc tutorial4&f"), 
		TUTORIAL4(
				"&fWhen you tried to train the skill, it showed whether you had the right "
					+ "materials and were in the right place to train. You can find out about other "
					+ "things you can train by using &4/dc schoollist&f and &4/dc schoolinfo&f. "
					+ "Continue with &4/dc tutorial5&f"), 
		TUTORIAL5(
				"&fYou now know the basic commands you need to succeed and develop as a " + primaryRaceName + ". " 
					+ "To find out more, use &4/dc help&f and &4/dc help <command>&f. Try &4/dc help " 
					+ "commands&f first. Good Luck!"),
	
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
	
		ERRORBADINPUT("(Input Error)Could not understand your input, try again"), 
		ERRORNOZONES("(No Zones)Could not find any school zones in your world!"),
		ERRORNOTOP("(Not OP)You are not authorized to use that command"), 
		ERRORINVALIDDWARFNAME("(Input Error)Could not find that player"),
		ERRORINVALIDSKILLLEVEL("(Input Error)Skill levels must be between 0 and 30"), 
		ERRORNOTANUMBER("(Input Error)Where an integer was expected something else was found"), 
		ERRORMISSINGINPUT("(Input Error)Could not find all the arguments required for that command"), 
		ERRORNOTVALIDSKILLINPUT("(Input Error)To find a skill you must input either the name or ID of an existing skill"),
		ERRORNOTVALIDEFFECTINPUT("(Input Error)To find an effect you must input either the name or ID of an existing effect"), 
		ERRORTOOMANYINPUTS("(Input Error)You gave me too many arguments. Please use underscores for spaces in skill names and no extra inputs."), 
		ERRORCOMMANDNOTFOUND("(Input Error)Could not understand your input as a command."),		
		
		;
	
		public String message;
	
		Fixed(String message) {
			this.message = message;
		}
	}
}
