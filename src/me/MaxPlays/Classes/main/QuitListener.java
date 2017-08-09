package me.MaxPlays.Classes.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
* Created by Max_Plays on 11.06.2017
*/
public class QuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(Class.classActive()){
			if(Class.getActiveClass().getPlayers().contains(p.getName()))
				Class.leaveClass(p);
		}
	}
	
}
