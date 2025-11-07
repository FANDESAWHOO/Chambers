package me.hulipvp.chambers.listener.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

public class FireListener implements Listener {
	
	@EventHandler
	public void onFire(BlockBurnEvent event) {
		event.setCancelled(true);
	}

}
