package de.glowman554.item;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.glowman554.item.tasks.DropTask;

public class ItemSpawnerMain extends JavaPlugin
{
	private static ItemSpawnerMain instance;
	
	public ItemSpawnerMain()
	{
		instance = this;
	}
	
	private int currentLocation = 0;
	private ArrayList<Coordinate> dropLocations = new ArrayList<>();
	private ArrayList<ItemStack> items = new ArrayList<>();
	private String world;

	private FileConfiguration config = getConfig();
	
	
	private void loadDropLocations()
	{
		List<String> dropLocationStrings = (List<String>) config.getList("coordinates");
		for (String dropLocationString : dropLocationStrings)
		{
			String[] split = dropLocationString.split(",");
			
			Coordinate coordinate = new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
			
			getLogger().log(Level.INFO, coordinate.toString());
			
			dropLocations.add(coordinate);
		}
		
	}

	private void loadItems()
	{
		List<String> itemStrings = (List<String>) config.getList("items");
		for (String itemString : itemStrings)
		{
			String[] split = itemString.split("\\*");
			int multiplier = Integer.parseInt(split[1]);
			
			Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(split[0]));
			if (enchantment != null)
			{
				getLogger().log(Level.INFO, "Adding enchanted book with " + enchantment + " " + multiplier);
				ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
				meta.addStoredEnchant(enchantment, multiplier, true);
				enchantedBook.setItemMeta(meta);
				
				items.add(enchantedBook);
			}
			else
			{
				Material material = Material.getMaterial(split[0].toUpperCase());
				if (material == null)
				{
					throw new IllegalStateException("Material " + split[0] + " not found!");
				}
				
				getLogger().log(Level.INFO, "Adding item " + material + " * " + multiplier);
				
				ItemStack item = new ItemStack(material);
				item.setAmount(multiplier);
				
				items.add(item);
			}
		}
	}
	
	@Override
	public void onLoad()
	{
		config.addDefault("coordinates", new String[] {"0,60,0", "10,60,0", "0,60,10", "10,60,10"});
		config.addDefault("world", "world");
		config.addDefault("items", new String[] {"grass_block*64", "diamond_block*64", "sharpness*5"});

		config.options().copyDefaults(true);
		saveConfig();
		
		
		loadDropLocations();
		loadItems();
		
		world = config.getString("world");
	}

	@Override
	public void onEnable()
	{
		new DropTask().runTaskTimer(this, 20 * 30, 20 * 30);
	}

	@Override
	public void onDisable()
	{
	}
	
	public static ItemSpawnerMain getInstance()
	{
		return instance;
	}
	
	public ArrayList<ItemStack> getItems()
	{
		return items;
	}
	
	public String getWorld()
	{
		return world;
	}
	
	public Coordinate getNextDropLocation()
	{
		currentLocation = (currentLocation + 1) % dropLocations.size();
		return dropLocations.get(currentLocation);
	}

}
