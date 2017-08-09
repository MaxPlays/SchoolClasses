package me.MaxPlays.Classes.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
* Created by Max_Plays on 10.06.2017
*/
public class CommandClass implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("class")){
			Player p = (Player) sender;
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("join")){
					
					if(p.hasPermission("class.join")){
						Class.joinClass(p);
					}else{
						p.sendMessage(Classes.prefix + Translation.noperm);
					}
					
				}else if(args[0].equalsIgnoreCase("leave")){
					if(p.hasPermission("class.leave")){
						Class.leaveClass(p);
					}else{
						p.sendMessage(Classes.prefix + Translation.noperm);
					}
				}else if(args[0].equalsIgnoreCase("end")){
					
					if(p.hasPermission("class.end")){
						if(Class.classActive()){
							Class.endClass();
							p.sendMessage(Classes.prefix + "You have ended the current class");
						}else{
							p.sendMessage(Classes.prefix + Translation.noclass);
						}
					}else{
						p.sendMessage(Classes.prefix + Translation.noperm);
					}
					
				}else if(args[0].equalsIgnoreCase("list")){
					
					p.sendMessage(Classes.prefix + Translation.list);
					for(Class c: Class.getClasses()){
						if(Class.classActive()){
							if(Class.getActiveClass().equals(c)){
								p.sendMessage("§8- §a" + c.getName());
								continue;
							}
						}
						p.sendMessage("§8- §7" + c.getName());
					}
					
				}else{
					sendHelp(p);
				}
			}else if(args.length == 2){
				String arg = args[1];
				if(args[0].equalsIgnoreCase("create")){
					if(p.hasPermission("class.create")){
						if(!Class.classExists(arg)){
							new Class(arg, p.getLocation());
							p.sendMessage(Classes.prefix + "You have created the class §6" + arg);
						}else{
							p.sendMessage(Classes.prefix + "That class already exists");
						}
					}else{
						p.sendMessage(Classes.prefix + Translation.noperm);
					}
				}else if(args[0].equalsIgnoreCase("start")){
					
					if(p.hasPermission("class.start")){
						if(Class.classExists(arg)){
							p.sendMessage(Classes.prefix + "Starting class §6" + arg);
							Class.startClass(arg);
						}else{
							p.sendMessage(Classes.prefix + "That class does not exist");
						}
					}else{
						p.sendMessage(Classes.prefix + Translation.noperm);
					}
					
				}else if(args[0].equalsIgnoreCase("remove")){
					if(p.hasPermission("class.remove")){
						if(Class.classExists(arg)){
							Class.deleteClass(arg);
							p.sendMessage(Classes.prefix + "The class §6" + arg + " §7was deleted");
						}else{
							p.sendMessage(Classes.prefix + "That class does not exist");
						}
					}else{
						p.sendMessage(Classes.prefix + Translation.noperm);
					}
				}else{
					sendHelp(p);
				}
			}else{
				sendHelp(p);
			}
		}
		
		return true;
	}

	private void sendHelp(Player p){
		p.sendMessage("§7------------------- §8[§cClasses§8] §7-------------------");
		p.sendMessage("§7Plugin developed by §aMax_Plays");
		if(p.hasPermission("class.join"))
			p.sendMessage("§c/class join §7Join a class");
		if(p.hasPermission("class.leave"))
			p.sendMessage("§c/class leave §7Leave class");
		if(p.hasPermission("class.create"))
			p.sendMessage("§c/class create <Name> §7Create class");
		if(p.hasPermission("class.start"))
			p.sendMessage("§c/class start <Name> §7Start class");
		if(p.hasPermission("class.remove"))
			p.sendMessage("§c/class remove <Name> §7Remove class");
		if(p.hasPermission("class.end"))
			p.sendMessage("§c/class end §7End class");
	}
	
}
