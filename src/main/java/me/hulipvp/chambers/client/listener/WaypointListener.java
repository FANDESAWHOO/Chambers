package me.hulipvp.chambers.client.listener;

import org.bukkit.event.Listener;

import me.hulipvp.chambers.Chambers;

public class WaypointListener implements Listener {
	
	public WaypointListener() {
		Chambers.getInstance().registerListener(this);
	}

}
