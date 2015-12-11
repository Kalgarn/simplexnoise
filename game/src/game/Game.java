package game;

public class Game 
{
	private game.mapping.Map map;
	private final java.util.List<Team> teams = new java.util.ArrayList<Team>();
	private final java.util.Map<Team,java.awt.Point[]> spawns = new java.util.Hashtable<Team,java.awt.Point[]>();
	public int spawnSize = 4;
	public int mapWidth = 41;
	public int mapHeight = 21;
	
	public Game()
	{
		teams.add(new Team(java.awt.Color.RED, Team.Border.LEFT));
		teams.add(new Team(java.awt.Color.CYAN, Team.Border.RIGHT));
		teams.add(new Team(java.awt.Color.YELLOW, Team.Border.TOP));
		teams.add(new Team(java.awt.Color.GREEN, Team.Border.BOTTOM));
		
		reset();
	}
	
	public void reset()
	{
		while (true)
		{
			map = new game.mapping.Map(mapWidth, mapHeight);
			
			if (!mapIsFullyTraversable())
			{
				System.out.println("map generation FAILED (map not traversable)");
				continue;
			}
			
			final java.util.List<java.awt.Point> occupied = new java.util.ArrayList<java.awt.Point>();
			
			for (int n=0; n<teams.size(); n++)
			{
				final java.awt.Point[] spawns = randomSpawns(spawnSize, teams.get(n), occupied);
				
				if (spawns.length == 0)
				{
					System.out.println("map generation FAILED (could not place team \'" + teams.get(n).toString() + "\')");
					continue;
				}
				
				occupied.addAll(java.util.Arrays.asList(spawns));

				this.spawns.put(teams.get(n), spawns);
			}

			System.out.println("map generation SUCCESS");
			break;
		}
	}
	
	public game.mapping.Map getMap()
	{
		return map;
	}
	
	public java.awt.Point[] getSpawns(final Team team)
	{
		return spawns.get(team);
	}
	
	public Team[] getTeams()
	{
		return (Team[]) teams.toArray(new Team[0]);
	}
	
	private boolean mapIsFullyTraversable()
	{
		int count;
		
		for (int y=0; y < map.getHeight(); y++)
		{
			count = 0;
			
			for (int x=0; x < map.getWidth(); x++)
			{
				if (!map.getBiomeAt(x, y).isTraversable()) count++;
			}
			
			if (count == map.getWidth()) return false;
		}
		
		for (int x=0; x < map.getWidth(); x++)
		{
			count = 0;
			
			for (int y=0; y < map.getHeight(); y++)
			{
				if (!map.getBiomeAt(x, y).isTraversable()) count++;
			}
			
			if (count == map.getHeight()) return false;
		}
		return true;
	}
	
	private java.awt.Point[] randomSpawns(final int spawnSize, final Team team, final java.util.List<java.awt.Point> exclusions)
	{
		switch (team.getBorder())
		{
			case TOP:
				return randomTopSpawns(spawnSize, exclusions);
			case BOTTOM:
				return randomBottomSpawns(spawnSize, exclusions);
			case LEFT:
				return randomLeftSpawns(spawnSize, exclusions);
			case RIGHT:
				return randomRightSpawns(spawnSize, exclusions);
			default:
				return null;
		}
	}
	
	private java.awt.Point[] randomTopSpawns(final int spawnSize, final java.util.List<java.awt.Point> exclusions)
	{
		final boolean dir = new java.util.Random().nextBoolean();
		java.awt.Rectangle r;
		
		for (int y = 0; y < map.getHeight(); y++)
		{
			for (int n = 0; n < map.getWidth()/2; n++)
			{
				if (dir)
				{
					r = new java.awt.Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new java.awt.Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
			}
		}
		return null;
	}
	
	private java.awt.Point[] randomBottomSpawns(final int spawnSize, final java.util.List<java.awt.Point> exclusions)
	{
		final boolean dir = new java.util.Random().nextBoolean();
		java.awt.Rectangle r;
		
		for (int y = map.getHeight(); y >= 0; y--)
		{
			for (int n = 0; n < map.getWidth()/2; n++)
			{
				if (dir)
				{
					r = new java.awt.Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new java.awt.Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
			}
		}
		return null;
	}
	
	private java.awt.Point[] randomLeftSpawns(final int spawnSize, final java.util.List<java.awt.Point> exclusions)
	{
		final boolean dir = new java.util.Random().nextBoolean();
		java.awt.Rectangle r;
		
		for (int x = 0; x < map.getWidth(); x++)
		{
			for (int n = 0; n < map.getHeight()/2; n++)
			{
				if (dir)
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
			}
		}
		return null;
	}
	
	private java.awt.Point[] randomRightSpawns(final int spawnSize, final java.util.List<java.awt.Point> exclusions)
	{
		final boolean dir = new java.util.Random().nextBoolean();
		java.awt.Rectangle r;
		
		for (int x = map.getWidth(); x >= 0; x--)
		{
			for (int n = 0; n < map.getHeight()/2; n++)
			{
				if (dir)
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				else
				{
					r = new java.awt.Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				
				if (new java.awt.Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
			}
		}
		return null;
	}
		
	private java.awt.Point[] rectangleToPoints(final java.awt.Rectangle r)
	{
		final java.util.List<java.awt.Point> points = new java.util.ArrayList<java.awt.Point>();
		for (int y=r.y; y < r.y + r.height; y++)
		{
			for (int x=r.x; x < r.x + r.width; x++)
			{
				points.add(new java.awt.Point(x,y));
			}
		}
		return (java.awt.Point[]) points.toArray(new java.awt.Point[0]);
	}
	
	private boolean rectangleContainsObstacles(final java.awt.Rectangle r, final java.util.List<java.awt.Point> exclusions)
	{
		final java.awt.Point[] points = rectangleToPoints(r);
		for (int i=0; i<points.length; i++)
		{
			if (!map.getBiomeAt(points[i].x, points[i].y).isTraversable())
			{
					return true;
			}
			if (exclusions != null)
			{
				for (int j=0; j<exclusions.size(); j++)
				{
					if (exclusions.get(j).x == points[i].x && exclusions.get(j).y == points[i].y) return true;
				}
			}
		}
		return false;
	}
}
