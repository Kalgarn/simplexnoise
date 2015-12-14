package game.mapping;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Region 
{
	private Biome biome;
	private List<Point> points = new ArrayList<Point>();
	private int minx = Integer.MAX_VALUE;
	private int maxx = Integer.MIN_VALUE;
	private int miny = Integer.MAX_VALUE;
	private int maxy = Integer.MIN_VALUE;
	
	public Region(final Biome biome)
	{
		this.biome = biome;
	}
	
	public int size()
	{
		return points.size();
	}
	
	public Biome getBiome()
	{
		return biome;
	}
	
	public void add(final int x, final int y)
	{
		final Point p = new Point(x, y);
		if (!points.contains(p))
		{
			if (x < minx) minx = x;
			if (x +1 > maxx) maxx = x;
			if (y < miny) miny = y;
			if (y + 1 > maxy) maxy = y;
			points.add(p);
		}	
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(minx, miny, maxx-minx+1, maxy-miny+1);
	}
	
	public boolean contains(final Point p)
	{
		return contains(p.x, p.y);
	}
	
	public boolean contains(final int x, final int y)
	{
		return points.contains(new Point(x,y));
	}
}
