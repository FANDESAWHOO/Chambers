package me.hulipvp.chambers.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.game.structure.Game;
import me.hulipvp.chambers.profile.structure.Profile;

public class CountdownTask extends BukkitRunnable {

	@Override
	public void run() {
		Game game = Chambers.getInstance().getGameManager().getGame();
		game.setCountdownTime(game.getCountdownTime() - 1);
		if (game.getCountdownTime() <= 0) {
			Chambers.getInstance().getGameManager().finallyStart();
			cancel();
		} else if (game.getCountdownTime() % 5 == 0 || game.getCountdownTime() <= 5) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "Game starting in " + ChatColor.GREEN + game.getCountdownTime() + ChatColor.YELLOW + (game.getCountdownTime() == 1 ? " second" : " seconds") + ".");
		}
	    for (Player player : Bukkit.getOnlinePlayers()) {
	    	Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(player.getUniqueId());
	    	if (profile.getTeam() == null) {
	    		profile.setTeam(Chambers.getInstance().getTeamManager().getSmallestTeam());
	    		player.sendMessage(ChatColor.BLUE + "We selected a random team for you, because you wasn't having they.");
	    	}
	    }
	}

}
