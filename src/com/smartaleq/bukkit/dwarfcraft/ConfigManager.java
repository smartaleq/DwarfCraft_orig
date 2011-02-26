package com.smartaleq.bukkit.dwarfcraft;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.smartaleq.bukkit.dwarfcraft.ui.Messages;

public class ConfigManager {

	public ConfigManager(String directory, String paramsFileName){
		configDirectory = directory;
		configParamsFileName = paramsFileName;
	}
	
	private String configDirectory;
	private String configParamsFileName;
	
	private String configSkillsFileName;
	public static int configSkillsVersion;
	private String configEffectsFileName;
	public static int configEffectsVersion;
	private String configMessagesFileName;	
	static String dbpath;
	
	
	private static List<Skill> skillsArray = new ArrayList<Skill>();
	
	private void getDefaultValues() {
		if (configSkillsVersion == 0) configSkillsVersion = 100;
		if (configEffectsVersion == 0) configEffectsVersion = 100;
		if (configSkillsFileName == null) configSkillsFileName = "skills.config";
		if (configEffectsFileName == null) configEffectsFileName = "effects.config";;
		if (configMessagesFileName == null) configMessagesFileName = "messages.config";
		if (dbpath == null) dbpath = "./DwarfCraft/dwarfcraft.db";
		if (Messages.PRIMARYRACECONFIRM == null) Messages.PRIMARYRACECONFIRM = Messages.Fixed.PRIMARYRACECONFIRM.message;
		if (Messages.PRIMARYRACESUCCESS == null) Messages.PRIMARYRACESUCCESS = Messages.Fixed.PRIMARYRACESUCCESS.message;
		if (Messages.SECONDARYRACEALREADY == null) Messages.SECONDARYRACEALREADY = Messages.Fixed.SECONDARYRACEALREADY.message;
		if (Messages.SECONDARYRACECONFIRM == null) Messages.SECONDARYRACECONFIRM = Messages.Fixed.SECONDARYRACECONFIRM.message;
		if (Messages.SECONDARYRACESUCCESS == null) Messages.SECONDARYRACESUCCESS = Messages.Fixed.SECONDARYRACESUCCESS.message;
	}
	
	public boolean readConfigFile(){
		try {
			System.out.println("Reading config file");
			getDefaultValues();
			FileReader fr = new FileReader(configDirectory + configParamsFileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line!= null) {
				if(line.length()==0) {line = br.readLine(); continue;}
				if(line.charAt(0) == '#') {line = br.readLine(); continue;}
				String[] theline = line.split(":");
				if (theline.length > 2){line = br.readLine(); continue;}
				if (theline[0].equalsIgnoreCase("Skills File Name")) configSkillsFileName = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Effects File Name")) configEffectsFileName = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Messages File Name")) configMessagesFileName = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Database File Name")) dbpath = configDirectory + theline[1].trim();
				if (theline[0].equalsIgnoreCase("Debug Level")) DwarfCraft.debugMessagesThreshold = Integer.parseInt(theline[1].trim());
				if (theline[0].equalsIgnoreCase("Primary Race Name")) Messages.primaryRaceName = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Secondary Race Name")) Messages.secondaryRaceName = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Primary Race Plural")) Messages.primaryRacePlural = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Secondary Race Plural")) Messages.secondaryRacePlural = theline[1].trim();
				line = br.readLine();
			}
			
		}
		catch(FileNotFoundException fN) {
			fN.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean readSkillsFile(){
		String line = "";
		System.out.println("Reading skills file");
		try {
			FileReader fr = new FileReader(configDirectory + configSkillsFileName);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();
			while (line!= null) {
				if(line.length()==0) {line = br.readLine(); continue;}
				if(line.charAt(0) == '#')  {line = br.readLine(); continue;}
				if(line.charAt(0) == '^') {configSkillsVersion = Integer.parseInt(line.substring(2));line = br.readLine(); continue;}
				String[] theline = line.split(",");
				if (theline.length < 11){ 
					continue;
					}
				//Creating a new Skill - with ID, Name, School read from file
				Material trainerHeldMaterial = Material.AIR;
				int id = Integer.parseInt(theline[0]);
				String displayName = theline[1];
				School school = School.getSchool(theline[2]);
				//New skill initialized with level 0
				int level = 0;
				//Training cost stack array created, including "empty" itemstacks of type 0 qty 0
				List <ItemStack> trainingCost = new ArrayList <ItemStack>();
				if (theline[3] != "0") trainingCost.add(new ItemStack(Integer.parseInt(theline[3]), Integer.parseInt(theline[4])));
				if (theline[5] != "0") trainingCost.add(new ItemStack(Integer.parseInt(theline[5]), Integer.parseInt(theline[6])));
				if (theline[7] != "0") trainingCost.add(new ItemStack(Integer.parseInt(theline[7]), Integer.parseInt(theline[8])));
				//training multipliers taken from file
				double noviceIncrement = Double.parseDouble(theline[9]);
				double masterMultiplier = Double.parseDouble(theline[10]);
				//Effects generated from effects file
				List<Effect> effects = new ArrayList<Effect>();
				//create the new skill in the skillsarray
				skillsArray.add(new Skill(id, displayName, school, level, effects, trainingCost, noviceIncrement, masterMultiplier, trainerHeldMaterial));
				line = br.readLine();
			}
			return true;
		}
		catch(FileNotFoundException fN) {
			fN.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean readEffectsFile(){
		System.out.println("Reading effects file");
		String line = "";
		try {
			FileReader fr = new FileReader(configDirectory + configEffectsFileName);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();
			while (line!= null) {
				if(line.length()==0) {line = br.readLine(); continue;}
				if(line.charAt(0) == '#') {line = br.readLine(); continue;}
				if(line.charAt(0) == '^') {configEffectsVersion = Integer.parseInt(line.substring(2)); line = br.readLine(); continue;}
				String[] theline = line.split(",");
				if (theline.length < 20) {line = br.readLine();continue;}
				
				int			effectId 				= Integer.parseInt(theline[0]); 
				double 		baseValue 				= Double.parseDouble(theline[1]);
				double 		levelUpMultiplier 		= Double.parseDouble(theline[2]);
				double 		noviceLevelUpMultiplier = Double.parseDouble(theline[3]);
				double 		minValue 				= Double.parseDouble(theline[4]);
				double 		maxValue 				= Double.parseDouble(theline[5]);
				boolean 	floorResult 			= (theline[6].equalsIgnoreCase("TRUE")); 
				boolean 	hasException 			= (theline[7].equalsIgnoreCase("TRUE")); 
				int 		exceptionLow 			= Integer.parseInt(theline[8]); 
				int 		exceptionHigh 			= Integer.parseInt(theline[9]); 
				double 		exceptionValue 			= Double.parseDouble(theline[10]);
				int 		elfLevel 				= Integer.parseInt(theline[11]);
				EffectType 	effectType 				= EffectType.getEffectType(theline[12]);
				int 		initiator 				= Integer.parseInt(theline[13]); 
				int 		output 					= Integer.parseInt(theline[14]); 
				boolean 	toolRequired 			= (theline[15].equalsIgnoreCase("TRUE"));

				int[] tooltable = 
					{	Integer.parseInt(theline[16]),
						Integer.parseInt(theline[17]),
						Integer.parseInt(theline[18]),
						Integer.parseInt(theline[19]),
						Integer.parseInt(theline[20])};

				for(Skill skill:skillsArray){
					if(effectId / 10 == skill.id){
						skill.effects.add(new Effect(effectId, baseValue, levelUpMultiplier, noviceLevelUpMultiplier, minValue, maxValue, floorResult, hasException, exceptionLow, exceptionHigh, exceptionValue, elfLevel, effectType, initiator, output, toolRequired, tooltable));
						break;		
					}
				}
				line = br.readLine();
			}
			return true;
		}
		catch(FileNotFoundException fN) {
			fN.printStackTrace();
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readMessagesFile() {
		System.out.println("Reading messages file");
		try {
			getDefaultValues();
			FileReader fr = new FileReader(configDirectory + configMessagesFileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line!= null) {
				if(line.length()==0) {line = br.readLine(); continue;}
				if(line.charAt(0) == '#') {line = br.readLine(); continue;}
				String[] theline = line.split(":");
				if (theline.length > 2)	{line = br.readLine(); continue;}
				
				if (theline[0].equalsIgnoreCase("General Info")) Messages.GeneralInfo = theline[1].trim();
				if (theline[0].equalsIgnoreCase("Server Rules")) Messages.ServerRules = theline[1].trim();
				
				line = br.readLine();
			}

		}
		catch(FileNotFoundException fN) {
			fN.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			//Default to enum values if not found
			if (Messages.GeneralInfo ==null) Messages.GeneralInfo = Messages.Fixed.GENERALHELPMESSAGE.message;
			if (Messages.ServerRules ==null) Messages.ServerRules = Messages.Fixed.SERVERRULESMESSAGE.message;
		}
		return true;
	}
	
	public static List<Skill> getAllSkills() {
		List<Skill> newSkillsArray = new ArrayList<Skill>();
		for (Skill s: skillsArray){
			newSkillsArray.add(s.clone());
		}
		return newSkillsArray;
	}

	public int countSkills() {
		int count = 0;
		for (Skill s: skillsArray){
			if (s != null) count++;
		}
		return count;
	}

	
	
}
