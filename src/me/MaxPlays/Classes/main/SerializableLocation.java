package me.MaxPlays.Classes.main;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
* Created by Max_Plays on 10.06.2017
*/
public class SerializableLocation implements Serializable{

	private static final long serialVersionUID = -2433324631886859704L;
	private double x, y, z;
	private float yaw, pitch;
	private String world;
	
	public SerializableLocation(Location l){
		
		this.x = l.getBlockX() + 0.5;
		this.y = l.getBlockY() + 0.5;
		this.z = l.getBlockZ() + 0.5;
		this.world = l.getWorld().getName();
		this.yaw = l.getYaw();
		this.pitch = l.getPitch();
		
	}
	
	public Location toLocation(){
		Location l = new Location(Bukkit.getWorld(world), x, y, z);
		l.setYaw(yaw);
		l.setPitch(pitch);
		return l;
	}
	
}
