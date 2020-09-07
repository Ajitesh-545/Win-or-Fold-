package PokerApp;

import javax.swing.JFrame;

public class GameTest
	{

	public static void main(String[] args)
		{
		GamePanel G=new GamePanel();

	JFrame window  = new JFrame("WIN OR FOLD");

			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			window.pack();		

	window.setContentPane(G);

	window.setResizable(false);

			window.setVisible(true);

			window.pack();
			G.game.play();
			

	}
}