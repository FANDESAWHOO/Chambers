package me.hulipvp.chambers.util;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import java.util.*;

/**
 * Simple and lightweight ItemBuilder compatible with Spigot 1.8.8
 * (no Foundation or external libs required)
 */
public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    // -------------------------------
    // Constructors
    // -------------------------------
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(Material material, String name, String... lore) {
        return new ItemBuilder(material).name(name).lore(lore);
    }

    public static ItemBuilder copy(ItemStack base) {
        ItemBuilder builder = new ItemBuilder(base.getType(), base.getAmount());
        builder.item = base.clone();
        builder.meta = base.getItemMeta();
        return builder;
    }

    // -------------------------------
    // Core methods
    // -------------------------------
    public ItemBuilder name(String name) {
        meta.setDisplayName(color(name));
        return this;
    }

    public ItemBuilder lore(String... lines) {
        List<String> lore = new ArrayList<>();
        for (String line : lines) lore.add(color(line));
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        for (String line : lines) lore.add(color(line));
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int level) {
        meta.addEnchant(ench, level, true);
        return this;
    }

    public ItemBuilder flag(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder unbreakable(boolean state) {
        try {
            meta.spigot().setUnbreakable(state);
        } catch (Throwable ignored) {}
        return this;
    }

    public ItemBuilder color(Color color) {
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(color);
        }
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder owner(String name) {
        if (meta instanceof SkullMeta) {
            ((SkullMeta) meta).setOwner(name);
        }
        return this;
    }

    public ItemBuilder book(String title, String author, String... pages) {
        if (meta instanceof BookMeta) {
            BookMeta book = (BookMeta) meta;
            book.setTitle(color(title));
            book.setAuthor(color(author));
            for (String page : pages) {
                book.addPage(color(page));
            }
            meta = book;
        }
        return this;
    }

    // -------------------------------
    // Finalization
    // -------------------------------
    public ItemStack make() {
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack build() {
        return make();
    }

    public void give(Player player) {
        player.getInventory().addItem(make());
    }

    // -------------------------------
    // Utility
    // -------------------------------
    private String color(String text) {
        return text == null ? null : text.replace('&', '§');
    }

    // -------------------------------
    // Example: debug/test
    // -------------------------------
    public static void exampleGive(Player player) {
        ItemStack sword = ItemBuilder.of(Material.DIAMOND_SWORD, "&bEspada Legendaria", "&7Una espada con poder antiguo")
                .enchant(Enchantment.DAMAGE_ALL, 5)
                .flag(ItemFlag.HIDE_ENCHANTS)
                .unbreakable(true)
                .make();

        player.getInventory().addItem(sword);
    }
}
