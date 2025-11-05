package me.hulipvp.chambers.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * The main Event class to extend upon when creating a custom Event 
 * called by Chambers
 */
public class ChambersEvent extends Event {
	
	private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    

    public ChambersEvent setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);
        return !this.isCancelled();
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

}