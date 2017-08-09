package me.MaxPlays.Classes.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

/**
* Created by Max_Plays on 11.06.2017
*/
public class CommandRh implements CommandExecutor {

	static HashMap<Player, Long> raise = new HashMap<>(); 
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("rh")){
			Player p = (Player) sender;
			if(p.hasPermission("class.raisehand")){
				if(Class.classActive()){
					if(Class.getActiveClass().getPlayers().contains(p.getName())){
						if(raise.containsKey(p) && raise.get(p) > System.currentTimeMillis()){
							p.sendMessage(Classes.prefix + Translation.translateName(Translation.timeout, Class.active, p.getName(), Math.abs(System.currentTimeMillis() - raise.get(p)) / 1000));
							return true;
						}
						if(raise.containsKey(p))
							raise.remove(p);
						raise.put(p, System.currentTimeMillis() + (Translation.handTimeout * 1000));
						for(String s: Class.getActiveClass().getPlayers())
							Bukkit.getPlayer(s).sendMessage(Classes.prefix + Translation.translateName(Translation.raise, Class.active, p.getName()));
						playParticles(p.getLocation());
					
					}else{
						p.sendMessage(Classes.prefix + Translation.innoclass);
					}
				}else{
					p.sendMessage(Classes.prefix + Translation.noclass);
				}
			}else{
				p.sendMessage(Classes.prefix + Translation.noperm);
			}
		}
		
		return true;
	}
	public void playParticles(Location l){
		for(String s: Class.getActiveClass().getPlayers()){
			Player p = Bukkit.getPlayer(s);
			PacketPlayOutWorldParticles pkg = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) l.getX(), (float) l.getY() + 2, (float) l.getZ(), 0, 0, 0, 0, 10, 0);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(pkg);
		}
	}

}
