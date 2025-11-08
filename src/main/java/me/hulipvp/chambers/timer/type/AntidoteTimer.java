package me.hulipvp.chambers.timer.type;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.timer.PlayerTimer;
import me.hulipvp.chambers.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class AntidoteTimer extends PlayerTimer implements Listener {
    private final List<PotionEffectType> potions;
	public AntidoteTimer() {
		super("Antidote", TimeUnit.SECONDS.toMillis(15L));
		potions = new ArrayList<>();
		potions.add(PotionEffectType.WEAKNESS);
		potions.add(PotionEffectType.WITHER);
		potions.add(PotionEffectType.POISON);
		potions.add(PotionEffectType.SLOW);
		potions.add(PotionEffectType.HUNGER);
		potions.add(PotionEffectType.FAST_DIGGING);
	}

	@Override
	public String getScoreboardPrefix() {
		return null;
	}
	
	@EventHandler
	public void onInteractEvent(PlayerItemConsumeEvent event) {
		ItemStack stack = event.getItem();		
		if (ItemUtil.hasLore(stack, "Antidote")) {
			Player player = event.getPlayer();
			if (getRemaining(player) > 0) {
				player.sendMessage(ChatColor.GREEN + "You have remaining cooldown of "+getRemaining(player));
				event.setCancelled(true);
				return;
			}
			setCooldown(player, player.getUniqueId());
            new BukkitRunnable() {

				@Override
				public void run() {
					player.getActivePotionEffects().forEach(effect -> {
						if (potions.contains(effect.getType())) 
							player.getActivePotionEffects().remove(effect);
						
					});	
					if (getRemaining(player) <= 0) { 
						cancel();
					}
				}
            	
            }.runTaskLater(Chambers.getInstance(), 20L);
		}
	}
	@Override
	public boolean setCooldown(Player player, UUID uniqueID) {
		return super.setCooldown(player, uniqueID);
	}

}