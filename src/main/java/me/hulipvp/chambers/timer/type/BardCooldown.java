package me.hulipvp.chambers.timer.type;

import java.util.concurrent.TimeUnit;

import me.hulipvp.chambers.timer.PlayerTimer;

public class BardCooldown extends PlayerTimer {

	public BardCooldown() {
		super("Bard Cooldown", TimeUnit.SECONDS.toMillis(10L));
	}

	@Override
	public String getScoreboardPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

}
