package PokerApp;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Player 
{
	//drawing coordinates
	
			private int x;
			private int y;
			//size
			private int r;
			//Player Color
			private Color playerColor;
			private Color playerBoundaryColor;
			public double balance;
			
			//Score
			private int score;
			
			public BufferedImage image;
			
			
			public Player(int a,int b){
				
				//Set initial Coordinates
				setX(a);
			    setY(b);
			    //Set Radius
			    setR(15);
			  //Set Player Colors
				playerColor = Color.BLACK;
				playerBoundaryColor = Color.BLACK;
				//score
				balance = 1000;
				
	}
			public double getBalance(){return balance;}
			public void addScore(int n){balance+=n;}
			public int getX() {
				return x;
			}
			public void setX(int x) {
				this.x = x;
			}
			public int getY() {
				return y;
			}
			public void setY(int y) {
				this.y = y;
			}
			public int getR() {
				return r;
			}
			public void setR(int r) {
				this.r = r;
			}
			
	
  
	// gets 5 cards from deck
	public Card[] draw(Deck deck)
	{
		Card[] hand = deck.deal();
		return hand;
	}
	
	// switches card for a new card
	public Card redraw(int counter, Deck deck)
	{
		Card card = deck.redeal();
		
		return card;
	}

}
