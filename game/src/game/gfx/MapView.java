package game.gfx;

import game.Game;
import game.mapping.Region;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class MapView extends javax.swing.JComponent
{
	private static final long serialVersionUID = 1L;
	
	public static int tileSize = 43; // taille des tuiles
	public static int spawnSize = 22; // taille des blocs équipe
	
	public static final double DEFAULT_SCALE = .5; // échelle de la map      inetervale
	public static final double MIN_SCALE = 0.125;
	public static final double MAX_SCALE = 16.0;
	
	public static double scale;

	public enum Orientation {FLAT, POINTY}
	
	public boolean showGrid = false;  // affiche/cache un quadrillage
	public boolean showSpawns = true;  //  affiche/cache les équipes
	public boolean showRegions = true; //  affiche/suprime le contours des regions
	
	private int zoomedTileSize;
	private Game game;
	
	public MapView(final Game game)
	{
		setGame(game);
		
		addMouseWheelListener(new MouseAdapter()
		{
			public void mouseWheelMoved(final MouseWheelEvent event)
			{
				if (game != null)
				{
					if (event.getUnitsToScroll() > 0)
					{
						zoomOut();
						update();
					}
					else if (event.getUnitsToScroll() < 0)
					{
						zoomIn();
						update();
					}
				}
			}
		});
	}
	
	public void zoomIn()
	{
		final double oldScale = scale;
		double newScale = scale*2;
		if (newScale > MAX_SCALE) newScale = MAX_SCALE;
		if (oldScale != newScale)
		{
			scale = newScale;
			zoomedTileSize = (int)(tileSize * scale);
			setPreferredSize(new Dimension(game.getMap().getWidth() * zoomedTileSize, game.getMap().getHeight() * zoomedTileSize));
		}
	}
	
	public void zoomOut()
	{
		final double oldScale = scale;
		double newScale = scale/2;
		if (newScale < MIN_SCALE) newScale = MIN_SCALE;
		if (oldScale != newScale)
		{
			scale = newScale;
			zoomedTileSize = (int)(tileSize * scale);
			setPreferredSize(new Dimension(game.getMap().getWidth() * zoomedTileSize, game.getMap().getHeight() * zoomedTileSize));
		}
	}
	
	public void resetZoom()
	{
		if (scale != DEFAULT_SCALE)
		{
			scale = DEFAULT_SCALE;
			zoomedTileSize = (int)(tileSize * scale);
			setPreferredSize(new Dimension(game.getMap().getWidth() * zoomedTileSize, game.getMap().getHeight() * zoomedTileSize));
		}
	}
	
	public void setGame(final Game game)
	{
		this.game = game;
		resetZoom();
		update();
	}
	
	public void update()
	{		
		if (isShowing())
		{
			revalidate();
			repaint();
		}
	}

	public void paintTile(final int x, final int y, final int width, final int height, final game.mapping.Biome biome, final java.awt.Graphics graphics)
	{
		graphics.setColor(biome.getColor());
		graphics.fillRect(x, y, width, height);
	}
	
	public void paintComponent(final Graphics graphics)
	{
		super.paintComponent(graphics);
		
		final Region[] regions = game.getMap().getRegions();
		for (int n = 0; n < regions.length; n++)
		{
			paintRegion(graphics, regions[n]);
		}
		
		if (showGrid)
		{
			Color c;
			for (int y = 0; y < game.getMap().getHeight(); y++)
			{
				for (int x = 0; x < game.getMap().getWidth(); x++)
				{
					c = game.getMap().getBiomeAt(x, y).getColor();
					graphics.setColor(new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue()));
					graphics.drawRect(x * zoomedTileSize(), y * zoomedTileSize(), zoomedTileSize()-1, zoomedTileSize()-1);
				}
			}
		}
		
		if (showRegions)
		{
			for (int n = 0; n < regions.length; n++)
			{
				paintRegionBorders(graphics, regions[n]);
			}
		}
		
		if (showSpawns)
		{
			game.Team[] teams = game.getTeams();
			Color color;
			Point[] spawns;
	
			for (int n=0; n <teams.length; n++)
			{
				color = teams[n].getColor();
				spawns = game.getSpawns(teams[n]);
				
				for (int i=0; i<spawns.length; i++)
				{
					paintSpawnPoint(graphics, spawns[i], color);
				}
				
			}
		}
	}
	
	protected void paintRegion(final Graphics g, final Region region)
	{
		g.setColor(region.getBiome().getColor());
		final Rectangle b = region.getBounds();
		for (int y = 0; y < b.height; y++)
		{
			for (int x = 0; x < b.width; x++)
			{
				if (region.contains(b.x + x, b.y + y))
				{
					paintTile((b.x + x) * zoomedTileSize(), (b.y + y) * zoomedTileSize(), zoomedTileSize(), zoomedTileSize(), region.getBiome(), g);
				}
			}
		}
	}
	
	protected void paintRegionBorders(final Graphics g, final Region region)
	{
		g.setColor(Color.BLACK);
		final Rectangle b = region.getBounds();

		boolean showTop, showBottom, showLeft, showRight;
		int x1, y1, x2, y2;
		
		for (int y = b.y; y < b.y + b.height; y++)
		{
			for (int x = b.x; x < b.x + b.width; x++)
			{
				if (region.contains(x, y))
				{
					showTop = !region.contains(x, y-1);
					showBottom = !region.contains(x, y+1);
					showLeft = !region.contains(x-1, y);
					showRight = !region.contains(x+1, y);

					if (!showTop && !showBottom && !showLeft && !showRight) continue;
					
					x1 = x * zoomedTileSize();
					y1 = y * zoomedTileSize();
					x2 = x1 + zoomedTileSize();
					y2 = y1 + zoomedTileSize();
					
					if (showTop) g.drawLine(x1,y1,x2-1,y1);
					if (showBottom) g.drawLine(x1,y2-1,x2-1,y2-1);
					if (showLeft) g.drawLine(x1,y1,x1,y2-1);
					if (showRight) g.drawLine(x2-1,y1,x2-1,y2-1);
				}
			}
		}
	}
	
	protected void paintSpawnPoint(final Graphics g, final Point p, final Color c)
	{
		g.setColor(c);
		final int zss = (int)(spawnSize * scale);
		int x = p.x * zoomedTileSize() + zoomedTileSize() / 2 - zss / 2;
		int y = p.y * zoomedTileSize() + zoomedTileSize() / 2 - zss / 2;
		g.drawRect(x, y, zss-1, zss-1);
	}
	
	private int zoomedTileSize()
	{
		return zoomedTileSize;
	}
}
