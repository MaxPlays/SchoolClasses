package me.MaxPlays.Classes.main;

import org.bukkit.ChatColor;

/**
* Created by Max_Plays on 10.06.2017
*/
public class Translation {
	
	public static String noperm, join, leave, noclass, starting, ending, alreadyinclass, innoclass, list, raise, timeout;
	public static int handTimeout;
	
	public Translation(){
		
		Classes.prefix = t(Classes.cfg.getString("prefix")) + " §7";
		noperm = t(Classes.cfg.getString("noPermission"));
		join = t(Classes.cfg.getString("join"));
		leave = t(Classes.cfg.getString("leave"));
		noclass = t(Classes.cfg.getString("noClassesActive"));
		starting = t(Classes.cfg.getString("classStartingBroadcast"));
		ending = t(Classes.cfg.getString("classEndingBroadcast"));
		alreadyinclass = t(Classes.cfg.getString("alreadyJoined"));
		innoclass = t(Classes.cfg.getString("playerInNoClass"));
		list = t(Classes.cfg.getString("listClasses"));
		raise = t(Classes.cfg.getString("raiseHand"));
		timeout = t(Classes.cfg.getString("handTimeoutMessage"));
		handTimeout = Classes.cfg.getInt("handTimeout");
		
	}
	private String t(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	public static String translateName(String msg, String name){
		return msg.replace("{classname}", name);
	}
	public static String translateName(String msg, String name, String player){
		return translateName(msg, name).replace("{playername}", player);
	}
	public static String translateName(String msg, String name, String player, long time){
		return translateName(msg, name, player).replace("{time}", time + "");
	}
}
