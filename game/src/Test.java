

import game.Game;
import game.gfx.MapView;

public class Test 
{	
	public static void main(final String[] arguments)
	{
		final javax.swing.JFrame f = new javax.swing.JFrame();
		
		final MapView view = new MapView(new Game());
		f.addKeyListener(new java.awt.event.KeyAdapter()
		{
			public void keyPressed(final java.awt.event.KeyEvent event)
			{
				if (event.getKeyCode() == java.awt.event.KeyEvent.VK_F5)
				{
					view.setGame(new Game());
				}
			}
		});
		
		
		f.setContentPane(new javax.swing.JScrollPane(view));
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
}
