package me.hulipvp.chambers.map.menu;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.structure.Map;
import me.hulipvp.chambers.menu.type.ChestMenu;

import me.hulipvp.chambers.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MapMenu extends ChestMenu {

    private final List<Map> maps = new ArrayList<>();
    private final Chambers plugin = Chambers.getInstance();


    public MapMenu() {
        super(18);
        
        // More robust map loading
        if (plugin.getMapHandler() != null && plugin.getMapHandler().getMaps() != null) {
            for (me.hulipvp.chambers.map.structure.Map map : plugin.getMapHandler().getMaps()) {
                if (map != null && map.getName() != null) { // Additional validation
                    this.maps.add(map);
                }
            }
        }
        updateContents();
    }



    public void updateContents() {
        if (getInventory() == null) {
            return; // Inventory not initialized yet
        }
        
        getInventory().clear();

        if (maps.isEmpty()) {
            // Optionally add a placeholder item when no maps are available
            getInventory().setItem(4, new ItemBuilder(Material.BARRIER)
                    .name(ChatColor.RED + "No maps available")
                    .lore(ChatColor.GRAY + "There are no maps to display.")
                    .build());
            return;
        }
        int slot = 0;
        for (Map map : maps) {
            if (map == null) continue;
            
            ItemStack display = new ItemBuilder(Material.MAP)
                    .name(ChatColor.YELLOW + map.getName())
                    .lore(ChatColor.GRAY + (map.getDescription() != null ? map.getDescription() : "No description"),
                            ChatColor.AQUA + "Votes: " + map.getVotes())
                    .build();      
            getInventory().setItem(slot, display);
            slot++;
        }

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        int slot = event.getRawSlot();


       if (plugin.getMapHandler().hasVoted(uuid)) {
         plugin.getMapHandler().getVotes().remove(uuid);
     }

   

        int index = slot;
        if (index < maps.size()) {
            Map selected = maps.get(index);
            selected.setVotes(selected.getVotes() + 1);
            plugin.getMapHandler().putVote(uuid, selected.getName());

            player.sendMessage(ChatColor.YELLOW + "Votaste por: " + ChatColor.AQUA + selected.getName()
                    + ChatColor.GRAY + " (" + selected.getVotes() + " votos)");

            player.closeInventory();

            // ---- chequear inicio ----

            int online = plugin.getServer().getOnlinePlayers().size();
            int needed = (int) Math.ceil(online * (2.0 / 3.0));

            if (selected.getVotes() >= needed) {
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendMessage("§aEl mapa " + selected.getName() + " alcanzó 2/3 de los votos! Iniciando juego...");
                    p.closeInventory();
                }

                plugin.getGameManager().getGame().setupGame(selected);
                plugin.getGameManager().start();
            }
        }
    }


    
    @Override
    public String getTitle() {
        return ChatColor.DARK_AQUA + "Map Browser ";
    }

  
}
