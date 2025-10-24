package me.hulipvp.chambers.map.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.map.structure.Map;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class MapAddCommand extends MapCommand {

	@Command(name = "map", description = "The main command for Naos", aliases = { "maps" }, usage = "/<command>",  playerOnly = true, requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		Player player = (Player) commandArgs.getSender();
		if (commandArgs.length() != 1) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <name>");
			return;
		}
		String name = commandArgs.getArgs(0);
	    Map map	= Chambers.getInstance().getMapHandler().createMap(name);
	    
	    if (map == null) {
	    	player.sendMessage("&aThe map already exists");
	    	return;
	    }
	
	    player.sendMessage(ChatColor.BLUE + "The map "+map.getName()+" has been succesfully created.");
	   
	} 
	
}
