package me.hulipvp.chambers.map.command.admin;

import java.util.Arrays;

import org.bukkit.ChatColor;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.map.structure.Map;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class MapSetDescriptionCommand extends MapCommand {
	
	@Command(name = "map.setdescription", description = "Set description of a map.", aliases = { "maps.announcement" }, usage = "/<command> setdescription",  playerOnly = true, requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		if (commandArgs.length() != 2) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <name> <announcement>");
			return;
		}
		String name = commandArgs.getArgs(0);
        Map map	= Chambers.getInstance().getMapHandler().getMap(name);
	    
	    if (map == null) {
	    	commandArgs.getSender().sendMessage(ChatColor.GREEN + "The map doesnt exist, please provide a valid name.");
	    	return;
	    }
	    
	    map.setDescription(Chambers.SPACE_JOINER.join(Arrays.copyOfRange(commandArgs.getArgs(), 1, commandArgs.getArgs().length)));
	}

}
