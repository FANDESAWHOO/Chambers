package me.hulipvp.chambers.client.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.game.structure.GameStatus;
import me.hulipvp.chambers.team.structure.Team;

public class WaypointListener implements Listener {
	
	public WaypointListener() {
		Chambers.getInstance().registerListener(this);
	}

	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (Chambers.getInstance().getGameManager().getGame().getStatus() != GameStatus.INGAME) 
			return; 
		displayWaypoints(player);
	}
	
	public void displayWaypoints(Player player) {
		for (Team team : Chambers.getInstance().getTeamManager().getAllPlayerTeams()) {
			Chambers.getInstance().getClientManager().getLunarClient().sendWaypoint(player, team.getFormattedName(), team.getHome(), me.hulipvp.chambers.util.Color.toAwtColor(team.getColor()));
		}
		for (Team team : Chambers.getInstance().getTeamManager().getAllSystemTeams()) {
			Chambers.getInstance().getClientManager().getLunarClient().sendWaypoint(player, team.getFormattedName(), team.getClaim().getCenter(), me.hulipvp.chambers.util.Color.toAwtColor(team.getColor()));
		}
	}
		
}
