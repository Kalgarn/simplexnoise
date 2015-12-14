package game.mapping;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map 
{
	private int width;
	private int height;
	private Region[] regions;
	
	public Map(final int width, final int height)
	{
		this.width = width;
		this.height = height;
		regions = calcRegions(randomize(new Biome[width][height]));
	}
	
	public Region[] getRegions()
	{
		return regions;
	}
	
	public Biome getBiomeAt(final int x, final int y)
	{
		final Point p = new Point(x, y);
		for (int i=0; i<regions.length; i++)
		{
			if (regions[i].contains(p)) return regions[i].getBiome();
		}
		return null;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	private Biome[][] randomize(final Biome[][] grid)
	{
		int width = grid.length;
		int height = grid[0].length;
		
		int octaves = 8;
		double persistence = .4;
		int seed = new Random().nextInt();
		
		final SimplexNoise simplexNoise1 = new SimplexNoise(octaves, persistence, seed);
		final SimplexNoise simplexNoise2 = new SimplexNoise(octaves, persistence, seed);
		
		double xStart=0;
	    double XEnd=500;
	    double yStart=0;
	    double yEnd=500;

	    final double[][] noise1 = new double[width][height];
	    final double[][] noise2 = new double[width][height];
	    
	    int x, y;
	    
	    for(int i=0;i<width;i++)
	    {
	        for(int j=0;j<height;j++)
	        {
				x = (int)(xStart+i*((XEnd-xStart)/width));
				y = (int)(yStart+j*((yEnd-yStart)/height));
				noise1[i][j]=0.5*(1+simplexNoise1.getNoise(x,y));
				noise2[i][j]=0.5*(1+simplexNoise2.getNoise(x,y));
	        }
	    }
	    
	    double value1, value2;
	    
	    for (y = 0; y < noise1[0].length; y++)
        {
			for (x = 0; x < noise1.length; x++)
			{
				value1 = noise1[x][y];
				
				if (noise1[x][y] > 1)
				{
					value1 = 1;
				}
				
				if (noise1[x][y] < 0)
				{
					value1 = 0;
				}

				if (value1 < .35)
				{
					grid[x][y] = Biome.OCEAN;
				}
				else if (value1 < .40)
				{
					grid[x][y] = Biome.BEACH;
				}
				else if (value1 <.67)
				{
					value2 = noise2[x][y];
					
					if (noise2[x][y] > 1)
					{
						value2 = 1;
					}
					
					if (noise2[x][y] < 0)
					{
						value2 = 0;
					}
					
					if (value2 < .62)
					{
						grid[x][y] = Biome.GRASSLAND;
					}
					else
					{
						grid[x][y] = Biome.TEMPERATE_RAIN_FOREST;
					}
				}
				else
				{
					grid[x][y] = Biome.SCORCHED;
				}
			}
        }
	    
	    return grid;
	}
	
	private Region[] calcRegions(final Biome[][] grid)
	{
		final List<Region> regions = new ArrayList<Region>();
		Region region;

		for (int y = 0; y < grid[0].length; y++)
		{
			for (int x = 0; x < grid.length; x++)
			{
				if (grid[x][y] == null || grid[x][y] == null) continue;
				region = new Region(grid[x][y]);
				regions.add(region);
				region(x, y, region, grid);
			}
		}
		
		
		return (Region[]) regions.toArray(new Region[0]);
	}
	
	private void region(final int x, final int y, final Region region, final Biome[][] grid)
	{
		region.add(x,y);
		grid[x][y] = null;
		
		if (x - 1 >= 0 && grid[x - 1][y] != null && grid[x - 1][y] != null && grid[x - 1][y].equals(region.getBiome())) region(x - 1, y, region, grid);
		if (x + 1 < grid.length && grid[x + 1][y] != null && grid[x + 1][y] != null && grid[x + 1][y].equals(region.getBiome())) region(x + 1, y, region, grid);
		if (y - 1 >= 0 && grid[x][y - 1] != null && grid[x][y - 1] != null && grid[x][y - 1].equals(region.getBiome())) region(x, y - 1, region, grid);
		if (y + 1 < grid[0].length && grid[x][y + 1] != null && grid[x][y + 1] != null && grid[x][y + 1].equals(region.getBiome())) region(x, y + 1, region, grid); 
	}
}
