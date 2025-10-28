package me.hulipvp.chambers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


import lombok.Getter;
import me.hulipvp.chambers.claim.ClaimManager;
import me.hulipvp.chambers.client.ClientManager;
import me.hulipvp.chambers.command.ChambersCommand;
import me.hulipvp.chambers.command.commands.*;
import me.hulipvp.chambers.config.DataFile;
import me.hulipvp.chambers.game.GameManager;
import me.hulipvp.chambers.game.command.GameCommand;
import me.hulipvp.chambers.game.command.general.*;
import me.hulipvp.chambers.koth.KothManager;
import me.hulipvp.chambers.listener.ListenerManager;
import me.hulipvp.chambers.map.MapHandler;
import me.hulipvp.chambers.map.command.MapCommand;
import me.hulipvp.chambers.map.command.admin.MapAddCommand;
import me.hulipvp.chambers.map.command.admin.MapRemoveCommand;
import me.hulipvp.chambers.map.command.admin.MapSetDescriptionCommand;
import me.hulipvp.chambers.map.command.normal.MapListCommand;
import me.hulipvp.chambers.profile.ProfileManager;
import me.hulipvp.chambers.scoreboard.ProviderResolver;
import me.hulipvp.chambers.scoreboard.TabProvider;
import me.hulipvp.chambers.shop.EntityManager;
import me.hulipvp.chambers.shop.VillagerManager;
import me.hulipvp.chambers.team.TeamManager;
import me.hulipvp.chambers.team.command.TeamCommand;
import me.hulipvp.chambers.team.command.admin.*;
import me.hulipvp.chambers.team.command.normal.TeamChatCommand;
import me.hulipvp.chambers.team.command.normal.TeamHelpCommand;
import me.hulipvp.chambers.team.command.normal.TeamHomeCommand;
import me.hulipvp.chambers.team.command.normal.TeamLocationCommand;
import me.hulipvp.chambers.team.command.normal.TeamShowCommand;
import me.hulipvp.chambers.timer.TimerManager;
import me.hulipvp.chambers.util.commandapi.CommandFramework;
import me.hulipvp.chambers.util.scoreboard.Assemble;
import me.hulipvp.chambers.util.scoreboard.AssembleStyle;
import me.hulipvp.chambers.util.scoreboard.tab.TabHandler;


/**
 * Copyright (c) 2017 Zach Sills
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
@Getter
public class Chambers extends JavaPlugin {
	
	/* SINGLE, like me, plugin instance */
	@Getter
	private static Chambers instance;
	
	/* Game Managers */
	private ClaimManager claimManager;
	private GameManager gameManager;
	private KothManager kothManager;
	private ProfileManager profileManager;
	private TeamManager teamManager;
	private ListenerManager listenerManager;
	private VillagerManager villagerManager;
	private MapHandler mapHandler;
    private TimerManager timerManager;
    private ClientManager clientManager;
    

	/* Commands & Listeners */
	private CommandFramework commandFramework;

	/* Data Files */
	private DataFile dataFile;

	public void onEnable() {
        instance = this;
		dataFile = new DataFile(this, "data");
		
		claimManager = new ClaimManager();
		gameManager = new GameManager();
		kothManager = new KothManager();
		profileManager = new ProfileManager();
		teamManager = new TeamManager();
		listenerManager = new ListenerManager();
		villagerManager = new VillagerManager();
        mapHandler = new MapHandler();
        clientManager = new ClientManager();
        // Start Instance.
    	Assemble assemble = new Assemble(this, new ProviderResolver());
    	
    	// Set Interval (Tip: 20 ticks = 1 second).
    	assemble.setTicks(2);
    	
    	// Set Custom Style (Tip: This will set the scoreboard scores to range from 8 to -7)
    	assemble.setAssembleStyle(AssembleStyle.KOHI);
    	new TabHandler(new TabProvider(), this, 20L);
        timerManager = new TimerManager(this);
		commandFramework = new CommandFramework(this);
		getServer().getScheduler().runTaskLater(this, () -> {
	        teamManager.loadTeamHomes();
	    }, 20L);
		registerAllCommands();
		
	}
	
	public void onDisable() {
		gameManager.clearAllMobs();
		instance = null;
	}
	
	private void registerAllCommands() {
		new ChambersCommand();
		new BalanceCommand();
		new GameCommand();
		new GameStartCommand();
		new GameStopCommand();
		new GameSetCountdownCommand();
		new GameSetKothNameCommand();
		new GameSetLobbyCommand();
		new GameSetMapNameCommand();
		new KothCommand();
		new PayCommand();
		new ToggleBoardCommand();
		new VillagerCommand();
		new TeamCommand();
		new TeamBypassCommand();
		new TeamClaimCommand();
		new TeamHomeCommand();
		new TeamForceJoinCommand();
		new TeamForceLeaveCommand();
		new TeamForcePlaceCommand();
		new TeamSetDtrCommand();
		new TeamSetHomeCommand();
		new TeamSetVillagerCommand();
		new TeamChatCommand();
		new TeamHelpCommand();
		new TeamLocationCommand();
		new TeamShowCommand();
		new EntityManager();
		new MapCommand();
		new MapListCommand();
		new MapAddCommand();
		new MapSetDescriptionCommand();
		new MapRemoveCommand();
	}
	
	public void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, instance);
	}
}
