package de.glowman554.item;

import org.bukkit.Location;
import org.bukkit.World;

public class Coordinate
{
	private final int x;
	private final int y;
	private final int z;

	public Coordinate(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		return String.format("Coordinate{x: %d, y: %d, z: %d}", x, y, z);
	}
	
	public Location toLocation(World world)
	{
		return new Location(world, x, y, z);
	}
}
