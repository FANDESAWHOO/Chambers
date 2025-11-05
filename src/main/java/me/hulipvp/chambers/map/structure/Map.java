package me.hulipvp.chambers.map.structure;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.config.MapFile;
import me.hulipvp.chambers.util.ItemBuilder;
import me.hulipvp.chambers.util.LocationUtil;

@Getter
public class Map  {
	
	private String name;
	private World world;
	private ItemStack display;
	private String description;
	private MapLevel mapLevel;
	private MapFile mapFile; // this is where the infrimation is located
	private Location spawnLocation;
	private String kothName;
	@Setter private Integer votes = 0;
	
	public Map(String name) {
		this.name = name;
		this.world = null;
		this.display = ItemBuilder.of(Material.DIAMOND_AXE, name, "").build();
		this.description = null;
		this.mapLevel = MapLevel.NORMAL;
		this.spawnLocation = null;
		this.kothName = null;
		manageData();
	}
	
/*	public Map(World world, String name) {
		this.world = world;
		this.name = name;
		this.display = null;
		this.description = null;
		this.mapLevel = MapLevel.NORMAL;
		manageData();
	}
	
	public Map(World world, String name, ItemStack display) {
		this.world = world;
		this.name = name;
		this.display = display;
		this.description = null;
		this.mapLevel = MapLevel.NORMAL;
		manageData();
	}

	public Map(World world, String name, ItemStack display, String description) {
		this.world = world;
		this.name = name;
		this.display = display;
		this.description = description;
		this.mapLevel = MapLevel.NORMAL;
		manageData();
	}*/
	
	public void manageData() {
		mapFile = new MapFile(Chambers.getInstance(), name);
		String path = name + ".";

		if (mapFile.getConfiguration().getConfigurationSection(name) != null) {

			String worldName = mapFile.getString(path + "world");
			if (worldName != null)
				this.world = Bukkit.getWorld(worldName);

			this.description = mapFile.getString(path + "description");

			String lvl = mapFile.getString(path + "level");
			if (lvl != null)
				this.mapLevel = MapLevel.valueOf(lvl);

			String itemString = mapFile.getString(path + "item");
			if (itemString != null && !itemString.isEmpty())
				this.display = ItemBuilder.fromString(itemString).make();
			String koth = mapFile.getString(path + "koth");
			if (koth != null && !koth.isEmpty())
				this.kothName = koth;
			String location = mapFile.getString(path + "spawn");
			if (location != null && !location.isEmpty())
				this.spawnLocation = LocationUtil.deserializeLocation(location);
		}
	}
	
	public void setWorld(World world) { this.world = world; }
	public void setDisplay(ItemStack display) { this.display = display; }
	public void setDescription(String desc) { this.description = desc; }
	public void setMapLevel(MapLevel lvl) { this.mapLevel = lvl; }
	
	public void sendInformation(CommandSender sender) {
	    sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------");
	    sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "Map Information " + ChatColor.GRAY + "(" + name + ")");
	    sender.sendMessage(ChatColor.GRAY + "");
	    sender.sendMessage(ChatColor.YELLOW + "World: " + ChatColor.WHITE + (world != null ? world.getName() : "Not set"));
	    sender.sendMessage(ChatColor.YELLOW + "Level: " + ChatColor.WHITE + mapLevel.name());
	    sender.sendMessage(ChatColor.YELLOW + "Description: " + ChatColor.WHITE + (description != null ? description : "Not set"));
	    sender.sendMessage(ChatColor.GRAY + "");
	    sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------");
	}

	public void delete() {
	    String path = name + ".";
	    mapFile.getConfiguration().set(path, null);
	    mapFile.save();
	    mapFile.getFile().delete();
	}

	public void save() {
	    saveData();
	}

	public void saveData() {
		String path = name + ".";

		if (name != null)
			mapFile.getConfiguration().set(path + "world", name);

		mapFile.getConfiguration().set(path + "description", description);
		mapFile.getConfiguration().set(path + "level", mapLevel.name());
		if (kothName != null)
        mapFile.getConfiguration().set(path + "koth", this.kothName);
		if (spawnLocation != null)
        mapFile.getConfiguration().set(path + "spawn", LocationUtil.serializeLocation(spawnLocation));
		// item serialize
		if (display != null)
			mapFile.getConfiguration().set(path + "item", ItemBuilder.toString(display));

		mapFile.save();
	}

	
}
