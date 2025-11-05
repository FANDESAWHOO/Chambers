package me.hulipvp.chambers.map;

import lombok.Getter;

import me.hulipvp.chambers.Chambers;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;



@Getter
public class MapHandler {

    /** Cache principal de mapas (nombre → mapa) */
    private final Map<String, me.hulipvp.chambers.map.structure.Map> mapCache = new LinkedHashMap<>();

    private final Chambers plugin = Chambers.getInstance();

    public MapHandler() {
        loadMaps();
    }

    /**
     * Obtiene un mapa por nombre (case-insensitive).
     */
    public me.hulipvp.chambers.map.structure.Map getMap(String name) {
        if (name == null) return null;
        return mapCache.get(name.toLowerCase());
    }

    /**
     * Crea un nuevo mapa si no existe ya.
     * @return El mapa recién creado, o null si ya existía.
     */
    public me.hulipvp.chambers.map.structure.Map createMap(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Map name cannot be empty!");

        String key = name.toLowerCase();

        if (mapCache.containsKey(key)) {
            return null;
        }

        me.hulipvp.chambers.map.structure.Map map = new me.hulipvp.chambers.map.structure.Map(name);
        mapCache.put(key, map);

        Bukkit.getLogger().info("[Chambers] Created map: " + name);
        return map;
    }
    
    public void removeMap(String name) {
    	me.hulipvp.chambers.map.structure.Map map = mapCache.remove(name);
        if (map != null)
            map.delete();
    }

 /*   /**
     * Elimina un mapa por nombre.
     
    public boolean removeMap(String name) {
        String key = name.toLowerCase();
        me.hulipvp.chambers.map.structure.Map removed = mapCache.remove(key);

        if (removed != null) {
            Bukkit.getLogger().info("[Chambers] Removed map: " + removed.getName());
            return true;
        }
        return false;
    }*/

    /**
     * Devuelve todos los mapas en una lista inmutable.
     */
    public Collection<me.hulipvp.chambers.map.structure.Map> getMaps() {
        return Collections.unmodifiableCollection(mapCache.values());
    }


    public void loadMaps() {
        mapCache.clear();


        File folder = new File(plugin.getDataFolder(), "maps");
        if (!folder.exists()) folder.mkdirs();

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            String fileName = file.getName();
            String mapName = fileName.substring(0, fileName.length() - 4);

            me.hulipvp.chambers.map.structure.Map map = new me.hulipvp.chambers.map.structure.Map(mapName);
            mapCache.put(mapName.toLowerCase(), map);
        }

        Bukkit.getLogger().info("[Chambers] Loaded " + mapCache.size() + " maps.");
    }

    public void saveMaps() {
        for (me.hulipvp.chambers.map.structure.Map map : mapCache.values()) {
            map.saveData();
        }
        Bukkit.getLogger().info("[Chambers] Saved " + mapCache.size() + " maps.");
    }

}
