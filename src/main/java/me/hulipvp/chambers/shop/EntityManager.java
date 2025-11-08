package me.hulipvp.chambers.shop;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.menu.MenuListener;
import me.hulipvp.chambers.menu.menu.BuildMenu;
import me.hulipvp.chambers.menu.menu.CombatMenu;
import me.hulipvp.chambers.menu.menu.EnchanterMenu;
import me.hulipvp.chambers.menu.menu.SellMenu;
import me.hulipvp.chambers.shop.structure.ShopType;
import me.hulipvp.chambers.shop.type.BuildEntity;
import me.hulipvp.chambers.shop.type.CombatEntity;
import me.hulipvp.chambers.shop.type.EnchantEntity;
import me.hulipvp.chambers.shop.type.SellEntity;
import me.hulipvp.chambers.team.structure.Team;
import me.hulipvp.chambers.util.BukkitUtils;
import me.hulipvp.chambers.util.LocationUtil;

import java.util.HashMap;
import java.util.Map;

public class EntityManager implements Listener {
    public Map<Entity, ShopType> killedVillagers = new HashMap<>();

    public EntityManager() {
        Bukkit.getPluginManager().registerEvents(this, Chambers.getInstance());
        Bukkit.getPluginManager().registerEvents(new MenuListener(), Chambers.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() != null && event.getRightClicked() instanceof Villager && ((Villager) event.getRightClicked()).getCustomName() != null) {
            String name = ChatColor.stripColor(((Villager) event.getRightClicked()).getCustomName());

            if (name.toLowerCase().contains("combat")) {
                new CombatMenu(player).open(player);
            } else if (name.toLowerCase().contains("sell")) {
                new SellMenu(player).open(player);
            } else if (name.toLowerCase().contains("build")) {
                new BuildMenu(player).open(player);
            } else if (name.toLowerCase().contains("enchant")) {
                new EnchanterMenu(player).open(player);
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Villager && ((Villager) event.getEntity()).getCustomName() != null)
  //      	if (((Villager)event.getEntity()).getCustomName().toLowerCase().contains(Chambers.getInstance().getTeamManager().getTeamByMember(event.get)))
        	if (((Villager) event.getEntity()).getCustomName().toLowerCase().contains("dead")) {
            event.setCancelled(true);
        	}
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) { // TODO: Team need to have already initializated the location from villagers.
        if (event.getEntity() instanceof Villager && event.getEntity().getCustomName() != null) {
            if (event.getEntity().getCustomName().toLowerCase().contains("combat") || event.getEntity().getCustomName().toLowerCase().contains("enchant") || event.getEntity().getCustomName().toLowerCase().contains("build") || event.getEntity().getCustomName().toLowerCase().contains("sell")) {
                final int[] counter = {90};
                ShopType type = null;

                if (event.getEntity().getCustomName().toLowerCase().contains("combat")) type = ShopType.COMBAT;
                else if (event.getEntity().getCustomName().toLowerCase().contains("enchant")) type = ShopType.ENCHANTER;
                else if (event.getEntity().getCustomName().toLowerCase().contains("sell")) type = ShopType.SELLER;
                else if (event.getEntity().getCustomName().toLowerCase().contains("build")) type = ShopType.TOOLS;

                killedVillagers.put(event.getEntity(), type);

                Villager villager = (Villager) event.getEntity().getLocation().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.VILLAGER);
                villager.setFallDistance(0);
                villager.setRemoveWhenFarAway(false);
                villager.setAdult();
                villager.setProfession(Villager.Profession.BLACKSMITH);
                villager.setCanPickupItems(false);
                villager.setMaxHealth(40);
                villager.setHealth(40);
                villager.setCustomNameVisible(true);
                villager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, -6, true), true);

                new BukkitRunnable() {
                    @Override public void run() {
                        if (counter[0] <= 0) {
                            Team team = Chambers.getInstance().getTeamManager().getByColor(ChatColor.getByChar(ChatColor.getLastColors(event.getEntity().getCustomName()).replace("§", "").replace("&", "")));
                            if (killedVillagers.get(event.getEntity()) == ShopType.SELLER) {
                                new SellEntity(team, LocationUtil.getLocation(Chambers.getInstance().getGameManager().getGame().getMap().getMapFile().getString("TEAMS." + team.getType().name() + "." + "SELLER")));
                            } else if (killedVillagers.get(event.getEntity()) == ShopType.TOOLS) {
                                new BuildEntity(team, LocationUtil.getLocation(Chambers.getInstance().getGameManager().getGame().getMap().getMapFile().getString("TEAMS." + team.getType().name() + "." + "TOOLS")));
                            } else if (killedVillagers.get(event.getEntity()) == ShopType.ENCHANTER) {
                                new EnchantEntity(team, LocationUtil.getLocation(Chambers.getInstance().getGameManager().getGame().getMap().getMapFile().getString("TEAMS." + team.getType().name() + "." + "ENCHANTER")));
                            } else if (killedVillagers.get(event.getEntity()) == ShopType.COMBAT) {
                                new CombatEntity(team, LocationUtil.getLocation(Chambers.getInstance().getGameManager().getGame().getMap().getMapFile().getString("TEAMS." + team.getType().name() + "." + "COMBAT")));
                            }

                            killedVillagers.remove(event.getEntity());
                            villager.remove();

                            cancel();
                            return;
                        }

                        villager.setCustomName(ChatColor.RED + "Dead for" + ChatColor.GRAY + ": " + ChatColor.WHITE + BukkitUtils.niceTime(counter[0], false));
                        counter[0]--;
                    }
                }.runTaskTimer(Chambers.getInstance(), 0L, 20L);
            }
        }
    }
}
