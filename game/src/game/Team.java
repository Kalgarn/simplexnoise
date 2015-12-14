package game;

import java.awt.Color;

public class Team 
{

	public enum Border {TOP, RIGHT, BOTTOM, LEFT}
	
	private java.awt.Color color;
	private Border border;
	
	public Team(final Color color, final Border border)
	{
		this.color = color;
		this.border = border;
	}
	
	public Border getBorder()
	{
		return border;
	}
	
	public Color getColor()
	{
		return color;
	}
}
