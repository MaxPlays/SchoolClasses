package me.MaxPlays.Classes.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
* Created by Max_Plays on 10.06.2017
*/
public class Classes extends JavaPlugin{

	public static String prefix;
	public static FileConfiguration cfg;
	
	public void onEnable(){
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		cfg = getConfig();
		
		new Translation();
		Class.load();
		getCommand("class").setExecutor(new CommandClass());
		getCommand("rh").setExecutor(new CommandRh());
		Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
	}
	public void onDisable(){
		Class.endClass();
		Class.save();
	}
	
	
}
