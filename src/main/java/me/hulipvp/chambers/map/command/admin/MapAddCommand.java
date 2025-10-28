package me.hulipvp.chambers.map.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.map.structure.Map;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class MapAddCommand extends MapCommand {

	@Command(name = "map.create", description = "The main command for Naos", aliases = { "map.add", "maps.add", "maps.create" }, usage = "/<command>",  playerOnly = true, requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		Player player = (Player) commandArgs.getSender();
		if (commandArgs.length() != 1) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <name>");
			return;
		}
		String name = commandArgs.getArgs(0);
	    Map map	= Chambers.getInstance().getMapHandler().getMap(name);
	    
	    if (!(map == null)) {
	    	player.sendMessage(ChatColor.GREEN + "The map already exist, please select another name.");
	    	return;
	    }
	    map	= Chambers.getInstance().getMapHandler().createMap(name);
	    player.sendMessage(ChatColor.BLUE + "The map "+ map.getName() +" has been succesfully created.");
	   
	} 
	
}
