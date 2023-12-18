package de.glowman554.item.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import de.glowman554.item.ItemSpawnerMain;

public class Particles
{

	public static void spawnParticleEffect(Location location, Particle particleType, int count, int durationTicks)
	{
		new BukkitRunnable(
				)
		{
			int ticksRemaining = durationTicks;

			@Override
			public void run()
			{
				if (ticksRemaining > 0)
				{
					World world = location.getWorld();
					world.spawnParticle(particleType, location, count, 0, 0, 0, 0, null, false);

					ticksRemaining--;
				}
				else
				{
					cancel();
				}
			}
		}.runTaskTimer(ItemSpawnerMain.getInstance(), 0, 1);
	}
}
