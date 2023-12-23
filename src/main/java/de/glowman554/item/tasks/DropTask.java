package de.glowman554.item.tasks;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.glowman554.item.Coordinate;
import de.glowman554.item.ItemSpawnerMain;
import de.glowman554.item.utils.Particles;

public class DropTask extends BukkitRunnable
{
	private Random random = new Random();

	@Override
	public void run()
	{
		Coordinate drop = ItemSpawnerMain.getInstance().getNextDropLocation();
		World world = ItemSpawnerMain.getInstance().getServer().getWorld(ItemSpawnerMain.getInstance().getWorld());

		ArrayList<ItemStack> items = ItemSpawnerMain.getInstance().getItems();
		ItemStack item = items.get(random.nextInt(items.size()));
		Location location = drop.toLocation(world);

		if (world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4))
		{
			world.dropItem(location, item);
			Particles.spawnParticleEffect(location, Particle.SMOKE_NORMAL, 10, 20 * 10);
		}
	}

}
