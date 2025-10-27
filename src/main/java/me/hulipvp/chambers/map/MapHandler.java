package me.hulipvp.chambers.map;

import lombok.Getter;

import me.hulipvp.chambers.Chambers;

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

    /**
     * Elimina un mapa por nombre.
     */
    public boolean removeMap(String name) {
        String key = name.toLowerCase();
        me.hulipvp.chambers.map.structure.Map removed = mapCache.remove(key);

        if (removed != null) {
            Bukkit.getLogger().info("[Chambers] Removed map: " + removed.getName());
            return true;
        }
        return false;
    }

    /**
     * Devuelve todos los mapas en una lista inmutable.
     */
    public Collection<me.hulipvp.chambers.map.structure.Map> getMaps() {
        return Collections.unmodifiableCollection(mapCache.values());
    }

    /**
     * Carga los mapas desde Mongo o archivo.
     */
    public void loadMaps() {
        mapCache.clear();

        // 🔧 Si usás Mongo, podés reemplazar esto con tu propia lógica:
        // Ejemplo simulado:
        /*
        List<Document> docs = plugin.getMongoManager().getCollection("maps").find().into(new ArrayList<>());
        for (Document doc : docs) {
            Map map = Map.fromDocument(doc);
            mapCache.put(map.getName().toLowerCase(), map);
        }
        */

        Bukkit.getLogger().info("[Chambers] Loaded " + mapCache.size() + " maps.");
    }

    /**
     * Guarda todos los mapas en Mongo o archivo.
     */
    public void saveMaps() {
        // 🔧 Ejemplo simulado:
        /*
        MongoCollection<Document> col = plugin.getMongoManager().getCollection("maps");
        for (Map map : mapCache.values()) {
            col.replaceOne(Filters.eq("name", map.getName()), map.toDocument(), new ReplaceOptions().upsert(true));
        }
        */
        Bukkit.getLogger().info("[Chambers] Saved " + mapCache.size() + " maps.");
    }
}
