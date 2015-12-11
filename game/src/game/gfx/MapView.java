package game.gfx;

public class MapView extends javax.swing.JComponent
{
	private static final long serialVersionUID = 1L;
	
	public static int tileSize = 32;
	public static int spawnSize = 24;
	
	public static final double DEFAULT_SCALE = .5;
	public static final double MIN_SCALE = 0.125;
	public static final double MAX_SCALE = 16.0;
	
	public static double scale;

	public enum Orientation {FLAT, POINTY}
	
	public boolean showGrid;
	public boolean showSpawns = true;
	public boolean showRegions = true;
	
	private int zoomedTileSize;
	private game.Game game;
	
	public MapView(final game.Game game)
	{
		setGame(game);
		
		addMouseWheelListener(new java.awt.event.MouseAdapter()
		{
			public void mouseWheelMoved(final java.awt.event.MouseWheelEvent event)
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
			setPreferredSize(new java.awt.Dimension(game.getMap().getWidth() * zoomedTileSize, game.getMap().getHeight() * zoomedTileSize));
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
			setPreferredSize(new java.awt.Dimension(game.getMap().getWidth() * zoomedTileSize, game.getMap().getHeight() * zoomedTileSize));
		}
	}
	
	public void resetZoom()
	{
		if (scale != DEFAULT_SCALE)
		{
			scale = DEFAULT_SCALE;
			zoomedTileSize = (int)(tileSize * scale);
			setPreferredSize(new java.awt.Dimension(game.getMap().getWidth() * zoomedTileSize, game.getMap().getHeight() * zoomedTileSize));
		}
	}
	
	public void setGame(final game.Game game)
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
	
	public void paintComponent(final java.awt.Graphics graphics)
	{
		super.paintComponent(graphics);
		
		final game.mapping.Region[] regions = game.getMap().getRegions();
		for (int n = 0; n < regions.length; n++)
		{
			paintRegion(graphics, regions[n]);
		}
		
		if (showGrid)
		{
			java.awt.Color c;
			for (int y = 0; y < game.getMap().getHeight(); y++)
			{
				for (int x = 0; x < game.getMap().getWidth(); x++)
				{
					c = game.getMap().getBiomeAt(x, y).getColor();
					graphics.setColor(new java.awt.Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue()));
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
			java.awt.Color color;
			java.awt.Point[] spawns;
	
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
	
	protected void paintRegion(final java.awt.Graphics g, final game.mapping.Region region)
	{
		g.setColor(region.getBiome().getColor());
		final java.awt.Rectangle b = region.getBounds();
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
	
	protected void paintRegionBorders(final java.awt.Graphics g, final game.mapping.Region region)
	{
		g.setColor(java.awt.Color.BLACK);
		final java.awt.Rectangle b = region.getBounds();

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
	
	protected void paintSpawnPoint(final java.awt.Graphics g, final java.awt.Point p, final java.awt.Color c)
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
