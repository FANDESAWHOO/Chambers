package me.hulipvp.chambers.team.command.normal;


import me.hulipvp.chambers.team.command.TeamCommand;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class TeamRallyCommand extends TeamCommand {

	@Command(name = "faction.rally", description = "The command to notify your location", aliases = { "f.rally", "t.rally", "s.rally", "team.rally", "squad.rally", "faction.c", "f.r", "t.r", "s.r", "team.r", "squad.r" }, usage = "/<command> <args>", requiresTeam = true)
	public void onCommand(CommandArgs commandArgs) {
         // TODO: Make the command
	}
	
}
