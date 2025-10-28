package me.hulipvp.chambers.game;

import lombok.Setter;
import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.game.structure.Game;
import me.hulipvp.chambers.game.structure.GameStatus;
import me.hulipvp.chambers.map.structure.Map;

/**
 * Contiene información sincronizada sobre el estado actual de una partida.
 * Sirve como una capa de acceso rápida para Scoreboards, menús o displays.
 */

public class Information {

    private final String mapName;
    @Setter private GameStatus status;
    
    private final Game game;
    private final Map map;
    public Information(Game game) {
        if (game == null) throw new IllegalArgumentException("Game cannot be null!");
        this.game = game;
        this.mapName = game.getMapName();
        this.status = game.getStatus();
        this.map = Chambers.getInstance().getMapHandler().getMap(mapName);
    }

    /**
     * Devuelve el estado actual del juego, actualizando si cambió.
     */
    public GameStatus getStatus() {
        GameStatus current = game.getStatus();
        if (this.status != current) {
            this.status = current;
        }
        return this.status;
    }
    
    public Map getMap() {
    	return this.map;
    }
    
    public static Information fromGame(Game game) {
    	return new Information(game);
    }
    /**
     * Devuelve si el juego está actualmente en curso.
     */
    public boolean isRunning() {
        return game.hasStarted();
    }

    /**
     * Devuelve el nombre del mapa (sin necesidad de acceder al objeto Game).
     */
    public String getMapName() {
        return this.mapName;
    }
}
