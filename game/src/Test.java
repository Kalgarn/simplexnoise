

import game.Game;
import game.gfx.MapView;

public class Test 
{	
	public static void main(final String[] arguments)
	{
		final JFrame f = new JFrame();
		
		final MapView view = new MapView(new Game());
		f.addKeyListener(new event.KeyAdapter()
		{
			public void keyPressed(final event.KeyEvent event)
			{
				if (event.getKeyCode() == event.KeyEvent.VK_F5)
				{
					view.setGame(new Game());
				}
			}
		});
		
		
		f.setContentPane(new JScrollPane(view));
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
}
