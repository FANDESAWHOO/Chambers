package me.hulipvp.chambers.scoreboard.provider;

import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.profile.structure.ProfileStatus;
import me.hulipvp.chambers.util.DurationFormatter;
import me.hulipvp.chambers.util.scoreboard.AssembleAdapter;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IngameProvider implements AssembleAdapter {

	@Override
	public String getTitle(Player player) {
		return null;
	}

	@Override
	public List<String> getLines(Player player) {
		List<String> lines = new ArrayList<>();

		Profile profile = plugin.getProfileManager().getProfileByUuid(player.getUniqueId());
		switch (profile.getProfileStatus()) {
		case SPECTATING: {
			lines.add("&7You are a spectator.");
			break;
		}
		case RESPAWNING: {
			lines.add("&7You are respawning.");
			lines.add("&6&lRespawning in&7: " + profile.getRespawnTime());
			break;
		}
		case PLAYING: {
			lines.add("&e&lTeam&7:&r " + profile.getTeam().getFormattedName());
			lines.add("&4&lDTR&7:&6 " + profile.getTeam().getFormattedDTR());
			lines.add("&a&lBalance&7:&6 $" + profile.getBalance());
			if (plugin.getGameManager().getGame().getInvincibilityTime() > 0) {
				lines.add("&9&lInvincibility:&c " + formatIntToTime(plugin.getGameManager().getGame().getInvincibilityTime()));
			}
			if (plugin.getTimerManager().getTeleportTimer().getRemaining(player) > 0) {
				lines.add("&9&lHome&7: &6" +  DurationFormatter.getRemaining(plugin.getTimerManager().getTeleportTimer().getRemaining(player),true));
			}
			if (plugin.getTimerManager().getEnderpearlTimer().getRemaining(player) > 0) {
				lines.add("&5&lEnderpearl&7:&6 " +  DurationFormatter.getRemaining(plugin.getTimerManager().getEnderpearlTimer().getRemaining(player),true));
			}
			break;
		}
		}
		lines.add(profile.getProfileStatus() == ProfileStatus.PLAYING ? 0 : 1, "&6&lGame Time&7:&6 " + formatIntToTime(plugin.getGameManager().getGame().getTotalTime()));
		if (plugin.getKothManager().getKoth() != null) {
			lines.add("&9&l" + plugin.getKothManager().getKoth().getName() + "&7:&6 " + formatIntToTime(plugin.getKothManager().getKoth().getTime()));
		}

		return lines;
	}
	public String formatIntToTime(int i) {
		if (i < 3600) {
			int minutes = (i - (i % 60)) / 60;
			int seconds = i % 60;
			return ((minutes < 10) ? "0" + minutes : String.valueOf(minutes)) + ":" + ((seconds < 10) ? "0" + seconds : String.valueOf(seconds));
		} else if (i > 3600 && i < 3600 * 24) {
			int minutes = (i / 60) % 60;
			int hours = (i / 60) / 60;
			int seconds = i % 60;
			return hours + ":" + ((minutes < 10) ? "0" + minutes : String.valueOf(minutes)) + ":" + ((seconds < 10) ? "0" + seconds : String.valueOf(seconds));
		}
		return "00:00";
	}
}
