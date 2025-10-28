package me.hulipvp.chambers.menu.menu;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.menu.type.ChestMenu;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.util.ItemUtil;

public class CombatMenu extends ChestMenu {

    // Ítems predefinidos
    private final ItemStack diamondHelmet = new ItemUtil(Material.DIAMOND_HELMET).name("&aDiamond Helmet").setLore("&7&m-------------------", "&71 x Diamond Helmet", "&7&m-------------------", "&7Price: &a$50").build();
    private final ItemStack diamondChestplate = new ItemUtil(Material.DIAMOND_CHESTPLATE).name("&aDiamond Chestplate").setLore("&7&m-------------------", "&71 x Diamond Chestplate", "&7&m-------------------", "&7Price: &a$200").build();
    private final ItemStack diamondLeggings = new ItemUtil(Material.DIAMOND_LEGGINGS).name("&aDiamond Leggings").setLore("&7&m-------------------", "&71 x Diamond Leggings", "&7&m-------------------", "&7Price: &a$150").build();
    private final ItemStack diamondBoots = new ItemUtil(Material.DIAMOND_BOOTS).name("&aDiamond Boots").setLore("&7&m-------------------", "&71 x Diamond Boots", "&7&m-------------------", "&7Price: &a$65").build();
    private final ItemStack diamondSword = new ItemUtil(Material.DIAMOND_SWORD).name("&aDiamond Sword").setLore("&7&m-------------------", "&71 x Diamond Sword", "&7&m-------------------", "&7Price: &a$150").build();
    private final ItemStack bow = new ItemUtil(Material.BOW).name("&aBow").setLore("&7&m-------------------", "&71 x Bow", "&7&m-------------------", "&7Price: &a$150").build();
    private final ItemStack arrow = new ItemUtil(Material.ARROW).name("&aArrows").setLore("&7&m-------------------", "&71 x Arrow", "  &e- &aRight click to buy 16", "&7&m-------------------", "&7Price: &a$10").build();
    private final ItemStack pearl = new ItemUtil(Material.ENDER_PEARL).name("&aEnder Pearls").setLore("&7&m-------------------", "&71 x Ender Pearl", "  &e- &aRight click to buy 16", "&7&m-------------------", "&7Price: &a$25").build();
    private final ItemStack beef = new ItemUtil(Material.COOKED_BEEF).name("&aCooked Beef").setLore("&7&m-------------------", "&71 x Beef", "  &e- &aRight click to buy 16", "&7&m-------------------", "&7Price: &a$5").build();
    private final ItemStack speedPotion = new ItemUtil(Material.POTION).durability(8226).name("&aSpeed II Potion").setLore("&7&m-------------------", "&71 x Speed II Potion", "&7&m-------------------", "&7Price: &a$15").build();
    private final ItemStack firePotion = new ItemUtil(Material.POTION).durability(8227).name("&aFire Resistance Potion").setLore("&7&m-------------------", "&71 x Fire Resistance Potion", "&7&m-------------------", "&7Price: &a$25").build();
    private final ItemStack healPotion = new ItemUtil(Material.POTION).durability(16421).name("&aInstant Health II Potion").setLore("&7&m-------------------", "&71 x Instant Health II Potion", "  &e- &aRight click fill your inventory", "&7&m-------------------", "&7Price: &a$5").build();
    private final ItemStack antidote = new ItemUtil(Material.POTION).name("&aAntidote").setLore("&7&m-------------------", "&71 x Antidote", "&7&m-------------------", "&7Price: &a$150").build();
    private final ItemStack fullSet = new ItemUtil(Material.DIAMOND).name("&aFull Diamond Set").setLore("&7&m-------------------", "&71 x Full Diamond Set", "&7&m-------------------", "&7Price: &a$600").build();

    public CombatMenu(Player player) {
        super(6 * 9);
        update();
    }

    @Override
    public String getTitle() {
        return ChatColor.RED + "" + ChatColor.BOLD + "Combat Shop";
    }

    public void update() {
        inventory.clear();

        inventory.setItem(10, diamondHelmet);
        inventory.setItem(11, bow);
        inventory.setItem(12, arrow);
        inventory.setItem(14, speedPotion);
        inventory.setItem(15, firePotion);
        inventory.setItem(23, healPotion);
        inventory.setItem(21, pearl);
        inventory.setItem(19, diamondChestplate);
        inventory.setItem(20, fullSet);
        inventory.setItem(28, diamondLeggings);
        inventory.setItem(37, diamondBoots);
        inventory.setItem(39, antidote);
        inventory.setItem(41, beef);
        inventory.setItem(18, diamondSword);

        for (int i = 0; i < 6 * 9; i++) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, new ItemUtil(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clicked = event.getClickedInventory();
        Inventory top = event.getView().getTopInventory();
        if (clicked == null || top == null || !top.equals(inventory)) return;

        Player player = (Player) event.getWhoClicked();
        Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(player.getUniqueId());
        int balance = profile.getBalance();

        event.setCancelled(true);
        event.setResult(org.bukkit.event.Event.Result.DENY);
        player.updateInventory();

        ItemStack current = event.getCurrentItem();
        if (current == null || current.getType() == Material.AIR || current.getType() == Material.STAINED_GLASS_PANE) return;

        int cost = 0;
        ItemStack toGive = null;
        
        if (current.isSimilar(diamondHelmet)) {
            cost = 50; toGive = new ItemStack(Material.DIAMOND_HELMET);
        } else if (current.isSimilar(diamondChestplate)) {
            cost = 200; toGive = new ItemStack(Material.DIAMOND_CHESTPLATE);
        } else if (current.isSimilar(diamondLeggings)) {
            cost = 150; toGive = new ItemStack(Material.DIAMOND_LEGGINGS);
        } else if (current.isSimilar(diamondBoots)) {
            cost = 65; toGive = new ItemStack(Material.DIAMOND_BOOTS);
        } else if (current.isSimilar(diamondSword)) {
            cost = 150; toGive = new ItemStack(Material.DIAMOND_SWORD);
        } else if (current.isSimilar(bow)) {
            cost = 150; toGive = new ItemStack(Material.BOW);
        } else if (current.isSimilar(arrow)) {
            cost = (event.getClick() == ClickType.RIGHT) ? 10 * 16 : 10;
            toGive = new ItemStack(Material.ARROW, (event.getClick() == ClickType.RIGHT) ? 16 : 1);
        } else if (current.getType() == Material.ENDER_PEARL) {
            cost = (event.getClick() == ClickType.RIGHT) ? 25 * 16 : 25;
            toGive = new ItemStack(Material.ENDER_PEARL, (event.getClick() == ClickType.RIGHT) ? 16 : 1);
        } else if (current.isSimilar(beef)) {
            cost = (event.getClick() == ClickType.RIGHT) ? 5 * 16 : 5;
            toGive = new ItemStack(Material.COOKED_BEEF, (event.getClick() == ClickType.RIGHT) ? 16 : 1);
        } else if (current.isSimilar(speedPotion)) {
            cost = 15; toGive = new ItemStack(Material.POTION, 1, (short) 8226);
        } else if (current.isSimilar(firePotion)) {
            cost = 25; toGive = new ItemStack(Material.POTION, 1, (short) 8227);
        } else if (current.isSimilar(healPotion)) {
            if (event.getClick() == ClickType.RIGHT) {
                int emptySlots = 0;
                for (ItemStack item : player.getInventory().getContents())
                    if (item == null) emptySlots++;
                cost = 5 * emptySlots;
                if (balance < cost) {
                    player.sendMessage(ChatColor.RED + "Not enough balance (" + cost + "$ required)");
                    return;
                }
                for (int i = 0; i < 36; i++)
                    if (player.getInventory().getItem(i) == null)
                        player.getInventory().setItem(i, new ItemStack(Material.POTION, 1, (short) 16421));
                profile.setBalance(balance - cost);
                player.sendMessage(ChatColor.GREEN + "Filled inventory with potions for $" + cost);
                player.updateInventory();
                return;
            } else {
                cost = 5; toGive = new ItemStack(Material.POTION, 1, (short) 16421);
            }
        } else if (current.isSimilar(antidote)) {
            cost = 150; toGive = new ItemUtil(Material.POTION).name("&aAntidote").build();
        } else if (current.isSimilar(fullSet)) {
            cost = 600;
            if (balance < cost) {
                player.sendMessage(ChatColor.RED + "Not enough balance!");
                return;
            }
            player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            profile.setBalance(balance - cost);
            player.sendMessage(ChatColor.GREEN + "You bought a full diamond set for $" + cost);
            return;
        }

        // Compra normal
        if (toGive == null) return;
        if (balance < cost) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis costs &e$" + cost + "&c, you only have &e$" + balance + "&c."));
            return;
        }

        player.getInventory().addItem(toGive);
        profile.setBalance(balance - cost);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&aYou have bought &e" + toGive.getAmount() + "x "
                        + WordUtils.capitalizeFully(toGive.getType().name().replace("_", " "))
                        + " &afor &e$" + cost + "&a."));

        event.getView().setCursor(null);
        player.updateInventory();
    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().equals(getInventory())) {
            event.setCancelled(true);
        }
    }
}
