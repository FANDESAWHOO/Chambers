package me.hulipvp.chambers.claim.structure;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.hulipvp.chambers.team.structure.Team;


@Getter
@Setter
public class Claim {

	private Location cornerOne, cornerTwo;

	private Team owner;
    // Cache para mejor rendimiento
    private transient Integer minX, maxX, minZ, maxZ, minY, maxY;
    private transient boolean boundsCalculated = false;
    
    public Claim(Location cornerOne, Location cornerTwo, Team owner) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.owner = owner;
    }
    /**
     * Calcula y cachea los límites del claim para mejor rendimiento
     */
    private void calculateBounds() {
        if (cornerOne != null && cornerTwo != null) {
            minX = Math.min(cornerOne.getBlockX(), cornerTwo.getBlockX());
            maxX = Math.max(cornerOne.getBlockX(), cornerTwo.getBlockX());
            minZ = Math.min(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
            maxZ = Math.max(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
            minY = Math.min(cornerOne.getBlockY(), cornerTwo.getBlockY());
            maxY = Math.max(cornerOne.getBlockY(), cornerTwo.getBlockY());
            boundsCalculated = true;
        }
    }
	/**
	 * Returns <tt>true</tt> if the provided Location is inside the claim.<br>
	 * However, it returns <tt>false</tt> if the provided Location is not inside
	 * the claim
	 * 
	 * @param location - the Location in which you wish to see is inside the claim
	 * @return boolean - Whether or not the provided Location is inside the claim or not
	 */
	public boolean isInsideClaim(Location location) {
	    if (location == null || cornerOne == null || cornerTwo == null) {
	        return false;
	    }
	    
		return (location.getBlockX() >= Math.min(cornerOne.getBlockX(), cornerTwo.getBlockX()))
				&& (location.getBlockZ() >= Math.min(cornerOne.getBlockZ(), cornerTwo.getBlockZ()))
				&& (location.getBlockX() <= Math.max(cornerOne.getBlockX(), cornerTwo.getBlockX()))
				&& (location.getBlockZ() <= Math.max(cornerOne.getBlockZ(), cornerTwo.getBlockZ()));
	}
    /**
     * Returns <tt>true</tt> if the provided Location is within the Y (height) bounds of the claim.<br>
     * However, it returns <tt>false</tt> if the provided Location is not within the Y bounds
     * 
     * @param location - the Location in which you wish to check the Y coordinate
     * @return boolean - Whether or not the location's Y coordinate is within the claim's Y bounds
     */
    public boolean isY(Location location) {
        if (location == null || cornerOne == null || cornerTwo == null) {
            return false;
        }

        if (!boundsCalculated) {
            calculateBounds();
        }

        int y = location.getBlockY();
        return y >= minY && y <= maxY;
    }

    /**
     * Returns <tt>true</tt> if the provided Location is completely inside the claim (including Y coordinate).<br>
     * However, it returns <tt>false</tt> if the provided Location is not completely inside the claim
     * 
     * @param location - the Location in which you wish to check if it's completely inside
     * @return boolean - Whether or not the location is completely inside the claim
     */
    public boolean isCompletelyInsideClaim(Location location) {
        return isInsideClaim(location) && isY(location);
    }
	/**
	 * Returns <tt>true</tt> if the Player's location is inside the claim.<br>
	 * However, it returns <tt>false</tt> if the Player's location is not inside
	 * the claim
	 * <p>
	 * It's just a faster way instead of doing
	 * {@link Claim#isInsideClaim(player.getLocation)}, cuz I'm just that lazy
	 * 
	 * @param player - the Player in which you wish to see is inside the claim
	 * @return boolean - Whether or not the player's location is inside the claim or not
	 */
	public boolean isInsideClaim(Player player) {
		return isInsideClaim(player.getLocation());
	}
	
}
