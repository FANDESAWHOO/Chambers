package me.hulipvp.chambers.command.commands;

import me.hulipvp.chambers.command.ChambersCommand;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.profile.structure.ProfileStatus;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;
import net.md_5.bungee.api.ChatColor;

public class BuilderCommand  extends ChambersCommand {
	@Command(name = "builder", description = "Toggle/untoggle the builder mode", aliases = { "constructor" }, usage = "/<command>", playerOnly = true, requiresTeam = false)
	public void onCommand(CommandArgs commandArgs) {
	   Profile profile = plugin.getProfileManager().getProfileByUuid(commandArgs.getPlayer().getUniqueId());
	   boolean status = profile.isBuilding();
	   if (status) {
		   profile.setProfileStatus(ProfileStatus.SPECTATING);
		   profile.setBuilding(!status);
	   } else {
		   profile.setProfileStatus(ProfileStatus.BUILDING);
		   profile.setBuilding(!status);
	   }
	   String building = profile.isBuilding() ? "Building" : "Spectating";
       profile.getPlayer().sendMessage(ChatColor.BLUE + "Changed building status to: "+building);
	}
}
