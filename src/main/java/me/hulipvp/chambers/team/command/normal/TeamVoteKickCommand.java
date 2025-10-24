package me.hulipvp.chambers.team.command.normal;

import me.hulipvp.chambers.team.command.TeamCommand;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class TeamVoteKickCommand extends TeamCommand {
	@Command(name = "faction.votekick", description = "The command to vote for kick a teammate.", usage = "/<command> <args>", requiresTeam = true)
	public void onCommand(CommandArgs commandArgs) {
         // TODO: Make the command
	}
}
