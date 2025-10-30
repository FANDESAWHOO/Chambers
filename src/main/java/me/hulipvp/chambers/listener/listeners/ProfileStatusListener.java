package me.hulipvp.chambers.listener.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.profile.structure.ProfileStatus;

public class ProfileStatusListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
			event.setCancelled(true);
		}
	}
		
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
			event.setCancelled(true);
		}
	}
	
/*	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
			if (!profile.isBypassMode())
			event.setCancelled(true);
		}
	}*/
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getEntity().getUniqueId());
			if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
				Player damager = (Player) event.getDamager();
				damager.sendMessage(ChatColor.RED + "The invincibility timer is still on.");
				event.setCancelled(true);
			}
 		}
		}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
			event.setCancelled(true);
		}
	}
	
}
