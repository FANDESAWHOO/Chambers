package me.hulipvp.chambers.map.structure;

import org.bson.Document;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import me.hulipvp.chambers.util.Mongoable;

@Getter
public class Map implements Mongoable {
	
	private String name;
	private final World world;
	private ItemStack display;
	private String description;
	@Setter private MapLevel mapLevel;
	
	public Map(String name) {
		this.name = name;
		this.world = null;
		this.display = null;
		this.description = null;
		this.mapLevel = MapLevel.NORMAL;
	}
	
	public Map(World world, String name) {
		this.world = world;
		this.name = name;
		this.display = null;
		this.description = null;
		this.mapLevel = MapLevel.NORMAL;
	}
	
	public Map(World world, String name, ItemStack display) {
		this.world = world;
		this.name = name;
		this.display = display;
		this.description = null;
		this.mapLevel = MapLevel.NORMAL;
	}

	public Map(World world, String name, ItemStack display, String description) {
		this.world = world;
		this.name = name;
		this.display = display;
		this.description = description;
		this.mapLevel = MapLevel.NORMAL;
	}
	
	@Override
	public Document toDocument() {
		Document document = new Document();
		document.append("name", name);
		document.append("item", display);
		return document;
	}

}
