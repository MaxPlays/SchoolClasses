package me.MaxPlays.Classes.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
* Created by Max_Plays on 10.06.2017
*/
public class Class implements Serializable{

	private static final long serialVersionUID = -6577933429243084305L;
	public static ArrayList<Class> classes = new ArrayList<>();
	public static String active = "";
	
	public static File file = new File("plugins/Classes/classes.dat");
	
	private SerializableLocation loc;
	private String name;
	private HashMap<String, SerializableLocation> players = new HashMap<>(); 
	
	public Class(String name, Location spawn){
		this.name = name;
		this.loc = new SerializableLocation(spawn);
		classes.add(this);
	}
	public Location getSpawn(){
		return loc.toLocation();
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String> getPlayers(){
		ArrayList<String> a = new ArrayList<>();
		for(Entry<String, SerializableLocation> e: players.entrySet())
			a.add(e.getKey());
		return a;
	}
	public HashMap<String, SerializableLocation> getLocations(){
		return players;
	}
	
	public static boolean classActive(){
		return !active.equals("");
	}
	public static Class getActiveClass(){
		if(classActive()){
			for(Class c: classes){
				if(c.getName().equalsIgnoreCase(active))
					return c;
			}
		}
		return null;
	}
	public static void endClass(){
		if(classActive()){
			for(String s: getActiveClass().getPlayers()){
				if(Bukkit.getPlayer(s) != null){
					Player p = Bukkit.getPlayer(s);
					p.sendMessage(Classes.prefix + Translation.translateName(Translation.ending, getActiveClass().getName()));
					p.teleport(getActiveClass().getLocations().get(p.getName()).toLocation());
				}
			}
			getActiveClass().players.clear();
			active = "";
		}
	}
	public static void startClass(String name){
		if(classExists(name)){
			endClass();
			Class c = getClass(name);
			Bukkit.broadcast(Classes.prefix + Translation.translateName(Translation.starting, c.getName()), "class.join");
			active = c.getName();
		}
	}
	public static boolean classExists(String name){
		for(Class c: classes){
			if(name.toLowerCase().equals(c.getName().toLowerCase()))
				return true;
		}
		return false;
	}
	public static Class getClass(String name){
		for(Class c: classes){
			if(name.toLowerCase().equals(c.getName().toLowerCase()))
				return c;
		}
		return null;
	}
	public static void joinClass(Player p){
		if(classActive()){
			if(!getActiveClass().getPlayers().contains(p.getName())){
				getActiveClass().players.put(p.getName(), new SerializableLocation(p.getLocation()));
				p.teleport(getActiveClass().getSpawn());
				p.sendMessage(Classes.prefix + Translation.translateName(Translation.join, active));
			}else{
				p.sendMessage(Classes.prefix + Translation.translateName(Translation.alreadyinclass, active));
			}
		}else{
			p.sendMessage(Classes.prefix + Translation.noclass);
		}
	}
	public static void leaveClass(Player p){
		if(classActive()){
			if(getActiveClass().getPlayers().contains(p.getName())){
				p.teleport(getActiveClass().getLocations().get(p.getName()).toLocation());
				getActiveClass().players.remove(p.getName());
				p.sendMessage(Classes.prefix + Translation.translateName(Translation.leave, active));
			}else{
				p.sendMessage(Classes.prefix + Translation.translateName(Translation.innoclass, active));
			}
		}else{
			p.sendMessage(Classes.prefix + Translation.noclass);
		}
	}
	@SuppressWarnings("unchecked")
	public static void load(){
		if(file.exists()){
			try{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				classes = (ArrayList<Class>)ois.readObject();
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void save(){
		if(classActive())
			getActiveClass().players.clear();
		active = "";
		try{
			if(file.exists())
				file.delete();
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(classes);
			oos.flush();
			fos.flush();
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void deleteClass(String name){
		if(getClass(name).equals(getActiveClass()))
			endClass();
		classes.remove(getClass(name));
	}
	public static ArrayList<Class> getClasses(){
		return classes;
	}
	
}
