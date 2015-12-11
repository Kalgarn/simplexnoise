package game.mapping;

public class Biome 
{
	public static Biome OCEAN = new Biome("OCEAN", new java.awt.Color(Integer.decode("0x44447a")), false);
	public static Biome COAST = new Biome("COAST", new java.awt.Color(Integer.decode("0x33335a")), false);
	public static Biome LAKESHORE = new Biome("LAKESHORE", new java.awt.Color(Integer.decode("0x225588")), false);
	public static Biome LAKE = new Biome("LAKE", new java.awt.Color(Integer.decode("0x336699")), false);
	public static Biome RIVER = new Biome("RIVER", new java.awt.Color(Integer.decode("0x225588")), false);
	public static Biome MARSH = new Biome("MARSH", new java.awt.Color(Integer.decode("0x2f6666")), true);
	public static Biome ICE = new Biome("ICE", new java.awt.Color(Integer.decode("0x99ffff")), true);
	public static Biome BEACH = new Biome("BEACH", new java.awt.Color(Integer.decode("0xa09077")), true);
	public static Biome LAVA = new Biome("LAVA", new java.awt.Color(Integer.decode("0xcc3333")), false);
	public static Biome SNOW = new Biome("SNOW", new java.awt.Color(Integer.decode("0xffffff")), true);
	public static Biome TUNDRA = new Biome("TUNDRA", new java.awt.Color(Integer.decode("0xbbbbaa")), true);
	public static Biome BARE = new Biome("BARE", new java.awt.Color(Integer.decode("0x888888")), true);
	public static Biome SCORCHED = new Biome("SCORCHED", new java.awt.Color(Integer.decode("0x555555")), false);
	public static Biome TAIGA = new Biome("TAIGA", new java.awt.Color(Integer.decode("0x99aa77")), true);
	public static Biome SHRUBLAND = new Biome("SHRUBLAND", new java.awt.Color(Integer.decode("0x889977")), true);
	public static Biome TEMPERATE_DESERT = new Biome("TEMPERATE DESERT", new java.awt.Color(Integer.decode("0xc9d29b")), true);
	public static Biome TEMPERATE_RAIN_FOREST = new Biome("TEMPERATE RAIN FOREST", new java.awt.Color(Integer.decode("0x448855")), true);
	public static Biome TEMPERATE_DECIDUOUS_FOREST = new Biome("", new java.awt.Color(Integer.decode("0x679459")), true);
	public static Biome GRASSLAND = new Biome("GRASSLAND", new java.awt.Color(Integer.decode("0x88aa55")), true);
	public static Biome SUBTROPICAL_DESERT = new Biome("", new java.awt.Color(Integer.decode("0xd2b98b")), true);
	public static Biome TROPICAL_RAIN_FOREST = new Biome("", new java.awt.Color(Integer.decode("0x337755")), true);
	public static Biome TROPICAL_SEASONAL_FOREST = new Biome("", new java.awt.Color(Integer.decode("0x559944")), true);

	private String name;
	private java.awt.Color color;
	private boolean traversable;
	
	private Biome(final String name, final java.awt.Color color, final boolean traversable)
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
	
	public java.awt.Color getColor()
	{
		return color;
	}
}
