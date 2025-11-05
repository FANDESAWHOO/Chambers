package me.hulipvp.chambers.timer;


import lombok.Getter;
import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.timer.type.BardCooldown;
import me.hulipvp.chambers.timer.type.CombatTimer;
import me.hulipvp.chambers.timer.type.EnderpearlTimer;
import me.hulipvp.chambers.timer.type.TeleportTimer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.LinkedHashSet;
import java.util.Set;
@Getter
public class TimerManager {
     private final Chambers plugin;

     private TeleportTimer teleportTimer;
     private EnderpearlTimer enderpearlTimer;
     private CombatTimer combatTimer;
     private BardCooldown bardCooldown;

     private final Set<Timer> timers = new LinkedHashSet<>();

    public TimerManager(Chambers plugin) {
        this.plugin = plugin;

        bardCooldown = new BardCooldown();
        registerTimer(teleportTimer = new TeleportTimer());
        registerTimer(enderpearlTimer = new EnderpearlTimer());
        registerTimer(combatTimer = new CombatTimer());
    }

    public void registerTimer(Timer timer) {
        timers.add(timer);
        if (timer instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) timer, plugin);
        }
    }

    public void unregisterTimer(Timer timer) {
        timers.remove(timer);
    }
}