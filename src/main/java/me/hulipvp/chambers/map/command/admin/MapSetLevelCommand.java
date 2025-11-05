package me.hulipvp.chambers.map.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.map.structure.Map;
import me.hulipvp.chambers.map.structure.MapLevel;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class MapSetLevelCommand extends MapCommand {
	
	@Command(name = "map.setlevel", description = "The main command for Naos", aliases = { "map.add", "maps.add", "maps.create" }, usage = "/<command>",  playerOnly = true, requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		Player player = (Player) commandArgs.getSender();
		if (commandArgs.length() != 2) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <name> <level>");
			return;
		}
		String name = commandArgs.getArgs(0);
		String level = commandArgs.getArgs(1);
	    Map map	= Chambers.getInstance().getMapHandler().getMap(name);
	    
	    if (map == null) {
	    	player.sendMessage(ChatColor.GREEN + "The map doesn't exists, please select another name.");
	    	return;
	    }
	    if (MapLevel.valueOf(level) == null) {
	    	player.sendMessage(ChatColor.GREEN + "The level provided doesn't exist, please provide: 'Normal' , 'High' , 'Awesome'");
	    	return;
	    }
	    map.setMapLevel(MapLevel.valueOf(level));
	    map.save();
	    player.sendMessage(ChatColor.BLUE + "The map '"+map.getName()+"' level changed to '"+map.getMapLevel().getName()+"'.");
	} 

}
