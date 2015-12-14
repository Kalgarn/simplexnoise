package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game 
{
	private game.mapping.Map map;
	private final List<Team> teams = new ArrayList<Team>();
	private final Map<Team,Point[]> spawns = new Hashtable<Team,Point[]>();
	public int spawnSize = 1; //nb de blocs de l'équipe. ex: 2 sur 2
	public int mapWidth = 41; // largeur de la carte
	public int mapHeight = 21; // hauteur
	
	public Game() // appeller pour générer la map
	{				// couleur et emplacement des équipes
		teams.add(new Team(Color.RED, Team.Border.LEFT));
		teams.add(new Team(Color.CYAN, Team.Border.RIGHT));
		teams.add(new Team(Color.YELLOW, Team.Border.TOP));
		teams.add(new Team(Color.GREEN, Team.Border.BOTTOM));
		
		reset(); // genere la map
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
			
			final List<Point> occupied = new ArrayList<Point>();
			
			for (int n=0; n<teams.size(); n++)
			{
				final Point[] spawns = randomSpawns(spawnSize, teams.get(n), occupied);
				
				if (spawns.length == 0)
				{
					System.out.println("map generation FAILED (could not place team \'" + teams.get(n).toString() + "\')");
					continue;
				}
				
				occupied.addAll(Arrays.asList(spawns));

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
	
	public Point[] getSpawns(final Team team)
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
	
	private Point[] randomSpawns(final int spawnSize, final Team team, final List<Point> exclusions)
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
	
	private Point[] randomTopSpawns(final int spawnSize, final List<Point> exclusions)
	{
		final boolean dir = new Random().nextBoolean();
		Rectangle r;
		
		for (int y = 0; y < map.getHeight(); y++)
		{
			for (int n = 0; n < map.getWidth()/2; n++)
			{
				if (dir)
				{
					r = new Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
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
	
	private Point[] randomBottomSpawns(final int spawnSize, final List<java.awt.Point> exclusions)
	{
		final boolean dir = new Random().nextBoolean();
		Rectangle r;
		
		for (int y = map.getHeight(); y >= 0; y--)
		{
			for (int n = 0; n < map.getWidth()/2; n++)
			{
				if (dir)
				{
					r = new Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new Rectangle(map.getWidth()/2 - n, y, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(map.getWidth()/2 + n, y, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
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
	
	private Point[] randomLeftSpawns(final int spawnSize, final List<java.awt.Point> exclusions)
	{
		final boolean dir = new Random().nextBoolean();
		Rectangle r;
		
		for (int x = 0; x < map.getWidth(); x++)
		{
			for (int n = 0; n < map.getHeight()/2; n++)
			{
				if (dir)
				{
					r = new Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
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
	
	private Point[] randomRightSpawns(final int spawnSize, final List<java.awt.Point> exclusions)
	{
		final boolean dir = new Random().nextBoolean();
		Rectangle r;
		
		for (int x = map.getWidth(); x >= 0; x--)
		{
			for (int n = 0; n < map.getHeight()/2; n++)
			{
				if (dir)
				{
					r = new Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
				{
					if (!rectangleContainsObstacles(r, exclusions))
					{
						return rectangleToPoints(r);
					}
				}
				
				if (dir)
				{
					r = new Rectangle(x, map.getHeight()/2 - n, spawnSize, spawnSize);
				}
				else
				{
					r = new Rectangle(x, map.getHeight()/2 + n, spawnSize, spawnSize);
				}
				
				if (new Rectangle(0, 0, map.getWidth(), map.getHeight()).contains(r))
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
		
	private Point[] rectangleToPoints(final Rectangle r)
	{
		final List<Point> points = new ArrayList<Point>();
		for (int y=r.y; y < r.y + r.height; y++)
		{
			for (int x=r.x; x < r.x + r.width; x++)
			{
				points.add(new Point(x,y));
			}
		}
		return (Point[]) points.toArray(new Point[0]);
	}
	
	private boolean rectangleContainsObstacles(final Rectangle r, final List<Point> exclusions)
	{
		final Point[] points = rectangleToPoints(r);
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
