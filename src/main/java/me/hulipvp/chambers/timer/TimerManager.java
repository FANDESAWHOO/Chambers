package me.hulipvp.chambers.timer;


import lombok.Getter;
import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.timer.type.TeleportTimer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.LinkedHashSet;
import java.util.Set;

public class TimerManager {
    @Getter private final Chambers plugin;

 //   @Getter private ProtectionTimer protectionTimer;
    @Getter private TeleportTimer teleportTimer;

    @Getter private final Set<Timer> timers = new LinkedHashSet<>();

    public TimerManager(Chambers plugin) {
        this.plugin = plugin;

        registerTimer(teleportTimer = new TeleportTimer(plugin));
    //    registerTimer(protectionTimer = new ProtectionTimer());
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