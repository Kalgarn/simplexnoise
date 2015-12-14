package game;

public class Team 
{

	public enum Border {TOP, RIGHT, BOTTOM, LEFT}
	
	private Color color;
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
