package me.hulipvp.chambers.client.type;

import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.common.location.ApolloBlockLocation;
import com.lunarclient.apollo.module.cooldown.Cooldown;
import com.lunarclient.apollo.module.cooldown.CooldownModule;
import com.lunarclient.apollo.module.nametag.Nametag;
import com.lunarclient.apollo.module.nametag.NametagModule;
import com.lunarclient.apollo.module.waypoint.Waypoint;
import com.lunarclient.apollo.module.waypoint.WaypointModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.recipients.Recipients;

import me.hulipvp.chambers.client.ClientType;
import net.kyori.adventure.text.Component;

public class LunarClient implements ClientType {

	private final NametagModule nametagModule = Apollo.getModuleManager().getModule(NametagModule.class); 
    private final WaypointModule waypointModule = Apollo.getModuleManager().getModule(WaypointModule.class);
    private final CooldownModule cooldownModule = Apollo.getModuleManager().getModule(CooldownModule.class);
    
	@Override
    public void setNametags(Player target, Player viewer, List<String> tag) {
        Apollo.getPlayerManager()
            .getPlayer(viewer.getUniqueId())
            .ifPresent(apolloViewer -> nametagModule.overrideNametag(
                (Recipients) apolloViewer,
                target.getUniqueId(),
                Nametag.builder()
                    .lines(tag.stream()
                        .map(Component::text)
                        .collect(Collectors.toList()))
                    .build()
            ));
    }

	@Override
	public void setCooldown(Player player, Cooldown cooldown) {
	Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(player.getUniqueId());
		 
	apolloPlayerOpt.ifPresent(apolloPlayer -> {
		        this.cooldownModule.displayCooldown(apolloPlayer, cooldown
		        );
		    });
		

		
	}

	@Override
	public void sendLocation(List<Player> team, Player player) {
	  // Null, because we go to LunarListener.
		
	}

	@Override
	public void sendWaypoint(Player player, String name, Location location, Color color) {
        Waypoint waypoint = Waypoint.builder()
                .location(ApolloBlockLocation.builder()
                        .world(location.getWorld().getName())
                        .x(location.getBlockX())
                        .y(location.getBlockY())
                        .z(location.getBlockZ())
                        .build())
                .name(name)
                .color(color)
                .build();
        
        displayWaypoint(player, waypoint);
	}
	
	public void sendWaypoint(Player player, Waypoint waypoint) {
		displayWaypoint(player, waypoint);
	}
	
	
	
    public void displayWaypoint(Player viewer, Waypoint waypoint) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(viewer.getUniqueId());

        apolloPlayerOpt.ifPresent(apolloPlayer -> {
            this.waypointModule.displayWaypoint(apolloPlayer, waypoint);
        });
    }
    @Override
    public void removeWaypoint(Player viewer, String waypoint) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(viewer.getUniqueId());
        apolloPlayerOpt.ifPresent(apolloPlayer -> this.waypointModule.removeWaypoint(apolloPlayer, waypoint));
    }

	@Override
	public Waypoint getWaypoint(String name, Location location, Color color) {
		return Waypoint.builder()
	                .location(ApolloBlockLocation.builder()
	                        .world(location.getWorld().getName())
	                        .x(location.getBlockX())
	                        .y(location.getBlockY())
	                        .z(location.getBlockZ())
	                        .build())
	                .name(name)
	                .color(color)
	                .build();
		
	}


}
