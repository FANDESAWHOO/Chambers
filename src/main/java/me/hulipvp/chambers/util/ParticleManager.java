package me.hulipvp.chambers.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


public class ParticleManager {
    
    public static void sendHeartsToPlayer(Player player) {
        try {
            // Obtener la ubicación del jugador (ligeramente arriba de su cabeza)
            Location location = player.getLocation().add(0, 2, 0);
            
            // Mostrar partículas de corazón alrededor del jugador
            ParticleEffect.HEART.display(
                0.5f,  // offsetX - dispersión horizontal
                0.5f,  // offsetY - dispersión vertical  
                0.5f,  // offsetZ - dispersión profunda
                0,     // speed - velocidad de las partículas (0 para estáticas)
                10,    // amount - cantidad de partículas
                location, // ubicación donde aparecerán
                player    // jugador que verá las partículas
            );
            
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cError al mostrar partículas de corazón");
        }
    }
    public static void sendHeartsToLocation(Player player) {
        try {
         for (Entity teammate : player.getNearbyEntities(25, 25 / 2, 25)) {
        	 if (teammate instanceof Player) {
                 // Mostrar partículas de corazón alrededor del jugador
                 ParticleEffect.HEART.display(
                     0.5f,  // offsetX - dispersión horizontal
                     0.5f,  // offsetY - dispersión vertical  
                     0.5f,  // offsetZ - dispersión profunda
                     0,     // speed - velocidad de las partículas (0 para estáticas)
                     10,    // amount - cantidad de partículas
                     player.getLocation(), // ubicación donde aparecerán
                     (Player) teammate    // jugador que verá las partículas
                 );
        	   }
        	 }
            
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cError al mostrar partículas de corazón");
        }
    }
    // Método alternativo con más personalización
    public static void sendCustomHearts(Player player, int cantidad, float dispersion) {
        try {
            Location location = player.getLocation().add(0, 2.2, 0);
            
            ParticleEffect.HEART.display(
                dispersion,  // offsetX
                dispersion,  // offsetY  
                dispersion,  // offsetZ
                0.1f,        // speed lenta
                cantidad,    // cantidad de corazones
                location,    // ubicación
                player       // jugador objetivo
            );
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método para mostrar corazones en un área alrededor del jugador
    public static void sendHeartsAroundPlayer(Player player, double radio) {
        try {
            Location center = player.getLocation();
            
            // Mostrar corazones que todos los jugadores cercanos puedan ver
            ParticleEffect.HEART.display(
                1.0f,    // offsetX más amplio
                1.0f,    // offsetY  
                1.0f,    // offsetZ
                0,       // speed
                15,      // cantidad
                center,  // ubicación central
                radio    // radio de visibilidad
            );
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}