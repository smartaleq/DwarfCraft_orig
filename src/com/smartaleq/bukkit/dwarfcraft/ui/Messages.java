package com.smartaleq.bukkit.dwarfcraft.ui;

public enum Messages {
	GENERALHELPMESSAGE (
			"ZOMG LONG MESSAGE LONG " +
			"MESSAGE long message zomg really" +
			"long message"),
	SERVERRULESMESSAGE (
			"server rules"),
	INFO ( "&dWelcome to DwarfCraft. You are a Dwarf with " +
			"a set of skills that let you do minecraft tasks better." +
			" When you first start, things may be more difficult" +
			" than you are used to, but as you level your skills up you " +
			"will be much more productive than normal players. Each of " +
			"the skills listed in your skillsheet(&4/dc skillsheet&d) " +
			"has multiple effects. You can find out more about " +
			"training a skill and its effects with &4/dc skillinfo " +
			"<skillname or id>&d."),
	COMMANDLIST1 (
			"help|info|commands|comands2|tutorial|" +
			"skillinfo|skillsheet|effectinfo|train|" +
			"schoollist|schoolinfo|makemeadwarf|makemeanelf|" +
			"here"),
	COMMANDLIST2 (" setSkill | createschool"),
	
	TUTORIAL1 ("&fWelcome to the dwarfcraft tutorial. To get started, type &4/dc skillsheet&f. Afterwards, type &4/dc tutorial2&f to continue."),
	TUTORIAL2 ("&fYour Skillshset lists all skills that are affecting you and their level. Lets find out more about the Demolitionist skill. Type &4/dc skillinfo Demolit&f or &4/dc skillinfo 63&f. Continue with &4/dc tutorial3&f"),
	TUTORIAL3 ("&fThe skillinfo shows that your low level demotionist skill allows you to craft a normal amount of TNT. If you increase this skill enough, you'll get 2 or even more TNT per craft. The skill also reduces damage you take from explosions. Below that, it shows how to train the skill. Go inside the Specialist trainer in town and use &4/dc train 63&f. Continue with &4/dc tutorial4&f"),
	TUTORIAL4 ("&fWhen you tried to train the skill, it showed whether you had the right materials and were in the right place to train. You can find out about other things you can train by using &4/dc schoollist&f and &4/dc schoolinfo&f. Continue with &4/dc tutorial5&f"),
	TUTORIAL5 ("&fYou now know the basic commands you need to succeed and grow as a dwarf. To find out more, use &4/dc help&f and &4/dc help <command>&f. Try &4/dc help commands&f first. Good Luck!"),

	DWARFSUCCESS ("&6Congratulations, you're now a hearty &9dwarf&6 with level 0 skills!"),
	DWARFCONFIRM ("&6To become a &9dwarf&6 you need to confirm with &4/dc REALLYmakemeadwarf &6- this will reset your skills to 0!"),
	ELFSUCCESS ("&6I'm sorry to say this... really I must apologize... there was a problem in surgery and you're now... ahem... an &fElf&6."),
	ELFCONFIRM ("&6If you really want to remove all your skills, you'll have to say you &4'/dc REALLYmakemeanelf'&6, which is an altogether bad idea!"),
	ELFALREADY ("&6You're already an &fElf&6. No wonder you're so confused!"),

	ERRORBADINPUT (
			"Could not understand your input, try again"),
	;
	
	
	String message;
	Messages (String message){
		this.message = message;
	}
}
