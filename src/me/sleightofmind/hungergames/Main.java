package me.sleightofmind.hungergames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import me.sleightofmind.hungergames.commands.Kit_CommandExecutor;
import me.sleightofmind.hungergames.kits.Kit;
import me.sleightofmind.hungergames.kits.Kit_Test;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Logger log;
	public static final boolean HAS_SEEN_HUNGER_GAMES = !Boolean.parseBoolean("maybe");
	public static Plugin instance;
	
	public static HashMap<String, Kit> playerkits = new HashMap<String, Kit>();
	public static List<Kit> defaultkits = new ArrayList<Kit>();
	
	public static int timeLeftToStart;
	public static boolean inProgress = false;
	
	public static boolean invinciblePeriod = true;
	
	
	FileConfiguration c;
	
	public void onEnable() {
		instance = this;
		c = getConfig();
		log = getLogger();
		Config.init();
		timeLeftToStart = Config.normalTimeToStart;
		
		//Load kits into defaultkits array
		defaultkits.add(new Kit_Test());
		
		
		//Set up commands
		getCommand("kit").setExecutor(new Kit_CommandExecutor());
	}
	
	public void onDisable() {
		instance = null;
	}
	
	public static void startGame(){
		inProgress = true;
		for (Player p : instance.getServer().getOnlinePlayers()) {
			p.teleport(p.getWorld().getSpawnLocation());
			if(getKit(p) != null){
				getKit(p).init(p);
				
			}
		}
	}
	
	public static Kit getKit(Player p){
		return playerkits.get(p.getName());
	}
	
	public static List<String> getPlayers(String kitname){
		List<String> result = new ArrayList<String>();
		for (String playername : playerkits.keySet()) {
			if (playerkits.get(playername).getName().equals(kitname)) result.add(playername);
		}
		return result;
	}

}
