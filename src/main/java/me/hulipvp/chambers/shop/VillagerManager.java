package me.hulipvp.chambers.shop;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.shop.structure.ShopType;
import me.hulipvp.chambers.shop.type.BuildEntity;
import me.hulipvp.chambers.shop.type.CombatEntity;
import me.hulipvp.chambers.shop.type.EnchantEntity;
import me.hulipvp.chambers.shop.type.SellEntity;
import me.hulipvp.chambers.team.structure.Team;
import me.hulipvp.chambers.util.LocationUtil;

public class VillagerManager {

    public VillagerManager() {
 //       new BukkitRunnable() {
    //        @Override
    //        public void run() {
    //            spawnAllVillagers();
    //        }
    //    }.runTaskLater(Chambers.getInstance(), 20L);
    }

    /**
     * Spawns a Villager of the given ShopType at the provided Location
     */
    public void spawnVillager(ShopType type, Location location, Team team) {
        if (location.getWorld() == null) {
            Chambers.getInstance().getLogger().warning("[VillagerManager] El mundo de " + team.getName() + " es nulo para " + type.name());
            return;
        }

        location.getWorld().getNearbyEntities(location, 1, 2, 1).stream()
                .filter(e -> e.getType() == EntityType.VILLAGER)
                .forEach(e -> e.remove());

        switch (type) {
            case TOOLS:
                new BuildEntity(team, location);
                break;
            case SELLER:
                new SellEntity(team, location);
                break;
            case ENCHANTER:
                new EnchantEntity(team, location);
                break;
            case COMBAT:
                new CombatEntity(team, location);
                break;
            default:
            	break;
        }
    }

    /**
     * Spawns all shop Villagers for each team based on the saved config data.
     */
    public void spawnAllVillagers() {
        Chambers plugin = Chambers.getInstance();

        plugin.getTeamManager().getAllPlayerTeams().forEach(team -> {
            Arrays.stream(ShopType.values()).forEach(type -> {
                String path = "TEAMS." + team.getType().name() + "." + type.name();
                String serialized = plugin.getGameManager().getGame().getMap().getMapFile().getString(path);

                if (serialized != null) {
                    Location loc = LocationUtil.deserializeLocation(serialized);
                    if (loc != null) {
                        spawnVillager(type, loc, team);
                    } else {
                        plugin.getLogger().warning("[VillagerManager] No se pudo deserializar la ubicación de " + path);
                    }
                }
            });
        });
    }
}
