package me.hulipvp.chambers.listener.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.profile.structure.ProfileStatus;

public class ProfileStatusListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING && profile.getProfileStatus() != ProfileStatus.BUILDING) {
			event.setCancelled(true);
		}
	}
		
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING && profile.getProfileStatus() != ProfileStatus.BUILDING) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING) {
			if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) 
				return;
			if (!profile.isBypassMode() && !profile.isBuilding())
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING && profile.getProfileStatus() != ProfileStatus.BUILDING) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getDamager() instanceof Player) {
			Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getEntity().getUniqueId());
			if (profile.getProfileStatus().equals(ProfileStatus.RESPAWNING) || profile.getProfileStatus().equals(ProfileStatus.SPECTATING)) {
				event.setCancelled(true);
				return;
			}
		}
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getEntity().getUniqueId());
			if (profile.getProfileStatus() != ProfileStatus.PLAYING && profile.getProfileStatus() != ProfileStatus.BUILDING) {
				Player damager = (Player) event.getDamager();
				damager.sendMessage(ChatColor.RED + "You can't damage a player now.");
				event.setCancelled(true);
			}
 		}
		}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		if (profile.getProfileStatus() != ProfileStatus.PLAYING && profile.getProfileStatus() != ProfileStatus.BUILDING) {
			event.setCancelled(true);
		}
	}
	
}
