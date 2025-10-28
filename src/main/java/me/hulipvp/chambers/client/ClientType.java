package me.hulipvp.chambers.client;

import java.awt.Color;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lunarclient.apollo.module.cooldown.Cooldown;
import com.lunarclient.apollo.module.waypoint.Waypoint;


public interface ClientType {

	void setNametags(Player target, Player viewer, List<String> tag);
	
	void setCooldown(Player player, Cooldown cooldown);
	
	void sendLocation(List<Player> team, Player player);
	
	void sendWaypoint(Player player, String name, Location location, Color color);
	
	void removeWaypoint(Player player, String name);
	
	Waypoint getWaypoint(String name, Location location, Color color);
	
}
