package game.mapping;

import java.awt.Color;

	// Biome -> écosysteme
	// "Theme" des régions
	// traversable = si les regions peuvent se superposer

public class Biome 
{
	public static Biome OCEAN = new Biome("OCEAN", new Color(Integer.decode("0x44447a")), false);
	public static Biome COAST = new Biome("COAST", new Color(Integer.decode("0x33335a")), false);
	public static Biome LAKESHORE = new Biome("LAKESHORE", new Color(Integer.decode("0x225588")), false);
	public static Biome LAKE = new Biome("LAKE", new Color(Integer.decode("0x336699")), false);
	public static Biome RIVER = new Biome("RIVER", new Color(Integer.decode("0x225588")), false);
	public static Biome MARSH = new Biome("MARSH", new Color(Integer.decode("0x2f6666")), true);
	public static Biome ICE = new Biome("ICE", new Color(Integer.decode("0x99ffff")), true);
	public static Biome BEACH = new Biome("BEACH", new Color(Integer.decode("0xa09077")), true);
	public static Biome LAVA = new Biome("LAVA", new Color(Integer.decode("0xcc3333")), false);
	public static Biome SNOW = new Biome("SNOW", new Color(Integer.decode("0xffffff")), true);
	public static Biome TUNDRA = new Biome("TUNDRA", new Color(Integer.decode("0xbbbbaa")), true);
	public static Biome BARE = new Biome("BARE", new Color(Integer.decode("0x888888")), true);
	public static Biome SCORCHED = new Biome("SCORCHED", new Color(Integer.decode("0x555555")), false);
	public static Biome TAIGA = new Biome("TAIGA", new Color(Integer.decode("0x99aa77")), true);
	public static Biome SHRUBLAND = new Biome("SHRUBLAND", new Color(Integer.decode("0x889977")), true);
	public static Biome TEMPERATE_DESERT = new Biome("TEMPERATE DESERT", new Color(Integer.decode("0xc9d29b")), true);
	public static Biome TEMPERATE_RAIN_FOREST = new Biome("TEMPERATE RAIN FOREST", new Color(Integer.decode("0x448855")), true);
	public static Biome TEMPERATE_DECIDUOUS_FOREST = new Biome("", new Color(Integer.decode("0x679459")), true);
	public static Biome GRASSLAND = new Biome("GRASSLAND", new Color(Integer.decode("0x88aa55")), true);
	public static Biome SUBTROPICAL_DESERT = new Biome("", new Color(Integer.decode("0xd2b98b")), true);
	public static Biome TROPICAL_RAIN_FOREST = new Biome("", new Color(Integer.decode("0x337755")), true);
	public static Biome TROPICAL_SEASONAL_FOREST = new Biome("", new Color(Integer.decode("0x559944")), true);

	private String name;
	private Color color;
	private boolean traversable;
	
	private Biome(final String name, final Color color, final boolean traversable)
	{
		this.name = name;
		this.color = color;
		this.traversable = traversable;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isTraversable()
	{
		return traversable;
	}
	
	public Color getColor()
	{
		return color;
	}
}
