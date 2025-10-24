package me.hulipvp.chambers.map.command.admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class MapSetDescriptionCommand extends MapCommand {
	
	@Command(name = "map.setdescription", description = "Set description of a map.", aliases = { "maps.announcement" }, usage = "/<command> setdescription",  playerOnly = true, requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		Player player = (Player) commandArgs.getSender();
		
		if (commandArgs.length() != 2) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <name> <announcement>");
			return;
		}
	}

}
