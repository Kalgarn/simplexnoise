package test;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import game.Game;
import game.gfx.MapView;

public class Test 
{	
	public static void main(final String[] arguments)
	{
		final JFrame f = new JFrame();
		
		final MapView view = new MapView(new Game());
		f.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(final KeyEvent event)
			{
				// F5 pour génerer une nouvelle map dans la meme fenetre
				if (event.getKeyCode() == KeyEvent.VK_F5)
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
