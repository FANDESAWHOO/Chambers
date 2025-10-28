package me.hulipvp.chambers.timer.type;


import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.hulipvp.chambers.timer.GlobalTimer;


public class ProtectionTimer extends GlobalTimer implements Listener {
	
    public ProtectionTimer() {
        super("Protection", TimeUnit.SECONDS.toMillis(60L));
    }

    @Override
    public String getScoreboardPrefix() {
        return ChatColor.DARK_AQUA.toString() + ChatColor.BOLD;
    }

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (getRemaining() > 0) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) 
			if (getRemaining() > 0) {
				Player damager = (Player) event.getDamager();
				damager.sendMessage(ChatColor.RED + "The invincibility timer is still on.");
				event.setCancelled(true);
			}
		}
	
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (getRemaining() > 0) {
			event.setCancelled(true);
		}
	}
	
}
