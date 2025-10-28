package me.hulipvp.chambers.timer.type;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Sets;

import lombok.Getter;
import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.timer.PlayerTimer;
import me.hulipvp.chambers.util.Color;
import me.hulipvp.chambers.util.DurationFormatter;
@Getter
public class EnderpearlTimer extends PlayerTimer implements Listener{

	private final Set<Material> blockedPearlTypes;
	
	public EnderpearlTimer() {
		 super("Enderpearl", TimeUnit.SECONDS.toMillis(16L), false);
		  blockedPearlTypes = Sets.immutableEnumSet(Material.THIN_GLASS,
					new Material[] { Material.IRON_FENCE, Material.FENCE, Material.NETHER_FENCE, Material.FENCE_GATE,
							Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS,
							Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.JUNGLE_WOOD_STAIRS,
							Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS,
							Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS });
	}

	@Override
	public String getScoreboardPrefix() {
		return ChatColor.LIGHT_PURPLE.toString();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		clearCooldown(player);
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		clearCooldown(player);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity().getPlayer();
		clearCooldown(player);
	}
	
	public boolean hasCooldown(Player player) {
		return getRemaining(player) > 0L;
	}
	
	public void setCooldown(Player player) {
		setCooldown(player, player.getUniqueId(), defaultCooldown, persistable);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			Material material = event.getMaterial();
			Player player = event.getPlayer();
			if (material == Material.ENDER_PEARL && player.getGameMode() != GameMode.CREATIVE) {
				if (event.getClickedBlock() != null) {
					Block block = event.getClickedBlock();
					if ((block.getType().isSolid()) && (!(block.getState() instanceof InventoryHolder))) {
						event.setCancelled(true);
						player.setItemInHand(event.getItem());
						return;

					}
				}
				if (hasCooldown(player)) {
					event.setUseItemInHand(Event.Result.DENY);
					player.sendMessage(ChatColor.GOLD + "You must wait " + ChatColor.RESET + String.valueOf(DurationFormatter.getRemaining(getRemaining(player), true)) + ChatColor.GOLD + " seconds.");
				} else {
					setCooldown(player);
					new BukkitRunnable() {
						public void run() {
							ItemStack itemStack = player.getItemInHand();
							if (itemStack != null && itemStack.getType() == Material.ENDER_PEARL
									&& player.getGameMode() != GameMode.CREATIVE) {
								if (hasCooldown(player)) {
									ItemMeta itemMeta = itemStack.getItemMeta();
									itemMeta.setDisplayName(ChatColor.RED + String.valueOf(DurationFormatter.getRemaining(getRemaining(player), true)));
									itemStack.setItemMeta(itemMeta);
								} else if (itemStack.getItemMeta().hasDisplayName()) {
									ItemMeta itemMeta = itemStack.getItemMeta();
									itemMeta.setDisplayName(null);
									itemStack.setItemMeta(itemMeta);
									player.sendMessage(Color.color("&aYou can now throw a pearl again."));
									player.setLevel(0);
									player.setExp(0.0F);
									this.cancel();
								}
							}
						}
					}.runTaskTimer(Chambers.getInstance(), 1L, 2L);
				}
			}
		}
	}


}
