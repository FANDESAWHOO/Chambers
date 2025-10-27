package me.hulipvp.chambers.map.menu;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.map.structure.Map;

import me.hulipvp.chambers.menu.type.PaginatedMenu;
import me.hulipvp.chambers.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MapMenu extends PaginatedMenu {

    private final List<Map> maps;
    private final Chambers plugin = Chambers.getInstance();

    public MapMenu() {
        super(54, 0); 
        this.maps = new ArrayList<>(plugin.getMapHandler().getMaps());
    }

    @Override
    public String getTitle() {
        return ChatColor.DARK_AQUA + "Map Browser " + ChatColor.GRAY + "(Page " + (getPage() + 1) + ")";
    }

    @Override
    public void updateContents() {
        getInventory().clear();

        int start = getPage() * 45;
        int end = Math.min(start + 45, maps.size());
        List<Map> subList = maps.subList(start, end);

        int index = 0;
        for (Map map : subList) {
            ItemStack display = map.getDisplay() != null
                    ? map.getDisplay()
                    : new ItemBuilder(Material.MAP)
                        .name(ChatColor.YELLOW + map.getName())
                        .lore(ChatColor.GRAY + (map.getDescription() != null ? map.getDescription() : "No description"))
                        .build();
            getInventory().setItem(index++, display);
        }

        if (getPage() > 0)
            getInventory().setItem(45, new ItemBuilder(Material.ARROW).name(ChatColor.GREEN + "← Previous").build());
        if (getPage() < getTotalPages() - 1)
            getInventory().setItem(53, new ItemBuilder(Material.ARROW).name(ChatColor.GREEN + "Next →").build());
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (slot == 45 && getPage() > 0) {
            setPage(getPage() - 1);
            player.playSound(player.getLocation(), "CLICK", 1, 1);
            return;
        }

        if (slot == 53 && getPage() < getTotalPages() - 1) {
            setPage(getPage() + 1);
            player.playSound(player.getLocation(), "CLICK", 1, 1);
            return;
        }

        // Seleccionar mapa
        int index = getPage() * 45 + slot;
        if (index < maps.size()) {
            Map selected = maps.get(index);
            player.sendMessage(ChatColor.YELLOW + "Selected map: " + ChatColor.AQUA + selected.getName());
            player.closeInventory();
        }
    }

    @Override
    public int getTotalPages() {
        return (int) Math.ceil((double) maps.size() / 45.0);
    }
}
