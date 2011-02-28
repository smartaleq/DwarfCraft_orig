package com.smartaleq.bukkit.dwarfcraft.ui;

public enum CommandInfo {
	NONE(""), HELP(
			"&dThe &4/dc help &dcommand gives general help information. Use &4/dc help <command> &dfor command specific info. Alias &4'/dc ?'"), INFO(
			"&dThe &4/dc info &dcommand gives general information about the DwarfCraft plugin."), COMMANDS(
			"&dThe &4/dc commands &dcommand will list available player commands. &4/dc commands2 &dlists admin commands"), TUTORIAL(
			"&dThe &4/dc tutorial &dcommand will start a quick tutorial to familiarize you with basic DwarfCraft features and commands"),

	SKILLSHEET(
			"&dThe &4/dc skillsheet <DwarfName> &dcommand will list a Dwarf's skill levels. Blank playername will list your skills."), SKILLINFO(
			"&dThe &4/dc skillinfo <args> &dcommand will list details about a skill's effects and training costs. Use &bSkillID &dor &bSkillName&d."), EFFECTINFO(
			"&dThe &4/dc effectinfo &5<ID> &dcommand will list general information about an effect and what it does for you at your current level."),

	TRAIN(
			"&dThe &4/dc train <args> &dcommand will check if you meet the requirements to train a skill and then level it up. Use &bSkillID &dor &bSkillName&d."), SETSKILL(
			"&dThe &4/dc setskill <ID> <Level> &fADMIN &dcommand will set the selected skill level for the admin."),

	MAKEMEADWARF(
			"&dThe &4/dc Make&p &dcommand will make the player into a &6&p&d. If already a &6&p&d it will reset your skills. Use &4/dc ReallyMakeMeA&p &dto confirm."), 
	REALLYMAKEMEADWARF(
			"&dThe &4/dc ReallyMake&p &dcommand will make the player into a &6&p&d. If already a &6&p&d it will reset your skills."), 
	MAKEMEANELF(
			"&dThe &4/dc Make&s &dcommand will make the player into an &f&s&d. &f"+Messages.secondaryRacePlural+"&d have no skill levels and play like DwarfCraft doesn't exist (mostly). Use &4/dc ReallyMake&f&s &dto confirm."), 
	REALLYMAKEMEANELF(
			"&dThe &4/dc ReallyMake&s &dcommand will make the player into an &f&s&d. &f"+Messages.secondaryRacePlural+" &dhave no skill levels and play like DwarfCraft doesn't exist (mostly)."),;
	public String helpText;

	CommandInfo(String helpText) {
		this.helpText = helpText;

	}

	boolean performSKILLINFO() {
		return false;

	}
}
