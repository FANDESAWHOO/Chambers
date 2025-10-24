package me.hulipvp.chambers.command.commands;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.command.ChambersCommand;
import me.hulipvp.chambers.shop.structure.ShopType;
import me.hulipvp.chambers.util.LocationUtil;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class VillagerCommand extends ChambersCommand {
	
	@Command(name = "villager", description = "Spawn in Shop Villagers", aliases = { "spawnvillager" }, usage = "/<command> <args>", playerOnly = true, requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		if (commandArgs.length() != 1) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <all|type>");
			return;
		}
		String argument = commandArgs.getArgs(0);
		if (argument.equalsIgnoreCase("all")) {
			plugin.getVillagerManager().spawnAllVillagers();
			commandArgs.getPlayer().sendMessage(ChatColor.GREEN + "All Villagers have been spawned.");
		} else {
			ShopType villagerType = ShopType.getTypeFromString(commandArgs.getArgs(0));
			if (villagerType == null) {
				commandArgs.getPlayer().sendMessage(ChatColor.RED + "Invalid Villager type. Use one the following:");
				Arrays.stream(ShopType.values()).forEach(type -> commandArgs.getPlayer().sendMessage(type.getFormattedName()));
				return;
			}
			plugin.getTeamManager().getAllPlayerTeams().forEach(team -> {	
				if (plugin.getDataFile().getString("TEAMS." + team.getType().name() + "." + villagerType.name()) != null) {
					Chambers.getInstance().getVillagerManager().spawnVillager(villagerType, LocationUtil.deserializeLocation(plugin.getDataFile().getString("TEAMS." + team.getType().name() + "." + villagerType.name())), team);
				}
			});
		}
	}

}
