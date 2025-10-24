package me.hulipvp.chambers.map;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;

import lombok.Getter;
import me.hulipvp.chambers.map.structure.Map;

@Getter
public class MapHandler {

	private final Set<Map> maps;
	
	public MapHandler() {
		maps = new HashSet<>();
	}
	
	public me.hulipvp.chambers.map.structure.Map getMap(String name) {
	    return maps.stream()
	            .filter(map -> map.getName().equalsIgnoreCase(name))
	            .findFirst()
	            .orElse(null);
	}

    /**
     * Crea un nuevo mapa si no existe.
     * @param name Nombre del mapa
     * @return El mapa recién creado, o null si ya existía
     */
    public Map createMap(String name) {
        if (getMap(name) != null) {
            return null;
        }

        Map map = new Map(name);
        maps.add(map);
        return map;
    }

	public void onMapsLoad() {
		
	}
	
	public void onMapsSave() {
		
	}
	
}
