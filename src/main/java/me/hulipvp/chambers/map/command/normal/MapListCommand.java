package me.hulipvp.chambers.map.command.normal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.util.Color;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class MapListCommand extends MapCommand {

	@Command(name = "map.list", description = "The list of maps available.", aliases = { "maps.list" }, usage = "/<command> list",  playerOnly = true, requiresTeam = false, adminsOnly = false)
	public void onCommand(CommandArgs commandArgs) {
		Player player = (Player) commandArgs.getSender();
		if (!(Chambers.getInstance().getMapHandler().getMaps().size() > 0)) {
			player.sendMessage(ChatColor.RED + "There are no map available today.");
			return;
		}
		List<String> maps = new ArrayList<>();
		maps.add("&7&m-------------------------------------------------------------");
		Chambers.getInstance().getMapHandler().getMaps().forEach(map -> {
			maps.add("&e"+map.getName() + " &7- &a"+map.getDescription());
		});
		maps.add("&7&m-------------------------------------------------------------");
	   
		maps.forEach(string -> {
			player.sendMessage(Color.color(string));
		});
	}
	
}
