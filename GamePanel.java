package PokerApp;

	  import java.awt.BasicStroke;
	  import java.awt.Color;
	  import java.awt.Dimension;
	  import java.awt.Font;
	  import java.awt.Graphics;
	  import java.awt.Graphics2D;
	  import java.awt.RenderingHints;
	  import java.awt.event.KeyEvent;
	  import java.awt.event.KeyListener;
	  import java.awt.image.BufferedImage;
	  import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
	  import javax.swing.JPanel;

	  public class GamePanel extends JPanel implements Runnable, KeyListener{
	  private static final long serialVersionUID = 10;
	  public double pot=0;
	  public double bet=0;
	  public boolean up=false;
	  public boolean down=false;
	  public boolean enter=false;
	  public int cpufold=0;
	  public int winner=0;
	  public boolean cpuredraw=false;
	 
	  	
	// make game
			Game game = new Game();
			
			
	  	//Background Color
	  	private Color bgColor;
	  	
	  	//Dimensions
	  	public static int WIDTH;
	  	public static int HEIGHT;
	  	public String ranks1[]=new String[5];
	  	public String suits1[]=new String[5];
	  	public String ranks2[]=new String[5];
	  	public String suits2[]=new String[5];
	  	
	  	//FPS
	  	private int FPS;
	  	
	  	//Game Loop
	  	private boolean running;
	  	
	  	//Graphics
	  	public Graphics2D g;
	  	public BufferedImage image;
	  	public int pointer=0;
	  	public String arrow="<--  Redraw? (Y or N)";

	  	
	  	//Thread
	  	private Thread thread;
	  	public GamePanel(){
	  		bgColor = new Color(50, 50, 50);
	  		
	  		//Dimension Initialize
	  		WIDTH = 1000;
	  		HEIGHT = 1000;
	  		
	  		//set size
	  		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	  		
	  		//FPS Cap Set
	  		FPS = 60;
	  		
	  		//Focus Panel
	  		setFocusable(true);
	  		requestFocus();
	  	}
	  	public void addNotify(){
			
			super.addNotify();
			if(thread == null){
				thread = new Thread(this);
				thread.start();
			}
			addKeyListener(this);
		}
		public void run(){
			
			running = true;
			
			//Graphics Initialize
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) image.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			           RenderingHints.VALUE_ANTIALIAS_ON);

			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
			           RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			// FPS
			long startTime;
			long waitTime;
			long URDTimeMillis;
			long targetTime = 1000/FPS;

			/**
			 * GAME LOOP
			 */
			while(running) {
		
				startTime = System.nanoTime();
		
				gameUpdate();
				gameRender();
				gameDraw();
				
		
				URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
		
				waitTime = targetTime - URDTimeMillis;
		
				try {
					Thread.sleep(waitTime);
				}
				catch(Exception e) {
				}
		
			}

		}
		public void gameUpdate(){
	}
		
		public void gameRender(){
			

		
			
			g.setColor(bgColor);
			g.fillRect(0, 0 , WIDTH, HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.BOLD, 20));
			g.drawString("Player 1", 10, 500);	
		
			g.drawString("CPU",930,500);
			g.setColor(Color.WHITE.lightGray);
			g.drawString("WELCOME TO POKER (This is a 5-card style Poker game)  ! ", 10, 170);
			g.drawString("Press   Y    to redraw the card pointed at ($5 to redraw every card) ", 10, 195);
			g.drawString("Press   N    to stay with the current card at the pointer", 10,220);
			g.drawString("Press   F    to fold at any given time", 10, 245);
			g.drawString("Press   SPACE for next hand", 10, 345);
			g.drawString("Press   UP for increasing bet by $5.0 ", 10, 270);
			g.drawString("Press   DOWN for decreasing bet by $5.0 ", 10, 295);
			g.drawString("Press   ENTER when finalizing bet to show result ", 10, 320);
			
		
			g.setColor(Color.WHITE);
			g.fillOval(game.player1.getX(), game.player1.getY(),2*game.player1.getR(), 2*game.player1.getR());
			g.setColor(Color.BLACK);
			g.drawOval(game.player1.getX(), game.player1.getY(), 2*game.player1.getR(), 2*game.player1.getR());
		
			g.setColor(Color.WHITE);
			g.fillOval(game.player2.getX(), game.player2.getY(),2*game.player2.getR(), 2*game.player2.getR());
			g.setColor(Color.BLACK);
			g.drawOval(game.player2.getX(), game.player2.getY(), 2*game.player2.getR(), 2*game.player2.getR());
			g.setColor(Color.BLACK.yellow.darker().darker().brighter());
			g.setFont(new Font("ARIAL BOLD", Font.ITALIC, 70));
			g.drawString("WIN OR FOLD",280,100);
			g.setColor(Color.BLACK.white.darker());
			g.setStroke(new BasicStroke(4));
			g.drawLine(280, 130, 740, 130);
			g.setColor(Color.BLACK.yellow.darker().darker().brighter());
			g.setFont(new Font("ARIAL BOLD", Font.ITALIC, 30));
			g.drawString("$ "+game.player1.balance,40,580);
			g.drawString("$ "+game.player2.balance,820,580);
			g.drawString("POT",470,380);
			g.drawString("$ "+this.pot,450,420);
			if(pointer<5) {
			g.drawString(arrow,490,(550+35*pointer));}
			if(game.again==true)
			{
				// fill deck
				if(this.pot!=0)
				{
					game.player1.balance+=pot/2;
					game.player2.balance+=pot/2;
					this.pot=0;
				}
				game.deck.fillDeck();
				bet=0;
				cpufold=0;
				winner=0;
				
				// shuffle
				game.deck.shuffle();
				game.answer="";
				cpuredraw=false;
				
				// player draws
				game.hand1 = game.player1.draw(game.deck);
				game.hand2=game.player2.draw(game.deck);
				// sort hand		
				Arrays.sort(game.hand1);
				Arrays.sort(game.hand2);
				
				game.again=false;
				pointer=0;
				this.pot=20;
				game.player1.balance-=10.0;
				game.player2.balance-=10.0;
				game.checkHand(game.hand1);
				this.ranks1=game.ranks;
				this.suits1=game.suits;
				
				game.checkHand(game.hand2);
				this.ranks2=game.ranks;
				this.suits2=game.suits;
				
			}
				
			game.checkHand(game.hand1);
			this.ranks1=game.ranks;
			this.suits1=game.suits;
			
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Century Gothic", Font.ITALIC, 30));
				for (int handCounter = 0; handCounter < 5; handCounter++)
				{
					g.drawString(this.ranks1[handCounter]+this.suits1[handCounter],200,(550+35*handCounter));
				}
				if(game.y_redraw==true)
				{
					Card c=game.hand1[pointer];
					game.hand1[pointer]=game.player1.redraw(pointer, game.deck);
					for (int handCounter = 0; handCounter < 5; handCounter++)
					{
						for (int counter = 0; counter < 5;counter++)
						{
							if(c==game.hand1[counter])
								game.hand1[pointer]=game.player1.redraw(pointer, game.deck);	
						}
						for (int counter = 0; counter < 5; counter++)
						{
							if(c==game.hand2[counter])
								game.hand1[pointer]=game.player1.redraw(pointer, game.deck);	
						}
						
							
					}
					pointer+=1;
					game.player1.balance-=5.0;
					
					this.pot+=5;
					game.y_redraw=false;

					
				}
				if(game.n_redraw==true)
				{
					pointer+=1;
					game.n_redraw=false;
				}
				if(game.fold==true)
				{
					game.player2.balance+=this.pot;
					this.pot=0;
					game.fold=false;
				}
				if(pointer==5)
				{
					g.drawString("Bet Amount",530,(550+35*1));
					g.drawString("$ "+bet,550,(550+35*2));
					
				}
				if(up==true)
				{
					bet+=5;
					up=false;
					
				}
				if(down==true)
				{
					down=false;
					if(bet>=5)
						bet-=5;
				}
				if(enter==true)
				{
					this.pot+=bet;
					game.player1.balance-=bet;
					pointer+=1;
					enter=false;
				}
				
				if(pointer==6)
				{
					Arrays.sort(game.hand1);
					Arrays.sort(game.hand2);
					game.checkHand(game.hand2);
					this.ranks2=game.ranks;
					this.suits2=game.suits;
					game.point=0;
					game.evaluate(game.hand1,"PLAYER 1");
					g.drawString(game.answer,200,760);
					game.point=1;
					game.evaluate(game.hand2,"CPU");
					
					if(game.prestige[1]==0&&cpuredraw==false)
					{
						for (int a = 0; a < 5; a++)
						{
							Card c=game.hand2[a];
							if(c.rank<10) {
							game.hand2[a]=game.player2.redraw(a, game.deck);
							for (int handCounter = 0; handCounter < 5; handCounter++)
							{
								for (int counter = 0; counter < 5;counter++)
								{
									if(c==game.hand1[counter])
										game.hand2[a]=game.player2.redraw(counter, game.deck);	
								}
								for (int counter = 0; counter < 5; counter++)
								{
									if(c==game.hand2[counter])
										game.hand2[a]=game.player2.redraw(counter, game.deck);	
								}
								
									
							}
							}
							if(this.pot!=0.0)
							{
							game.player2.balance-=5;
							this.pot+=5;
							}
						}
						cpuredraw=true;
					}
					Arrays.sort(game.hand1);
					Arrays.sort(game.hand2);
					game.point=0;
					game.evaluate(game.hand1,"PLAYER 1");
					game.point=1;
					game.evaluate(game.hand2,"CPU");
					if((game.prestige[0]-game.prestige[1])<2 || game.prestige[1]>=3 || bet==0) 
					{
						if((game.prestige[0]-game.prestige[1])>=0 && bet>=200 )
						{
							cpufold=2;
						}
						else
						cpufold=1;
						
					}
					
					else {cpufold=2;}
					if(cpufold==1) {
						if(this.pot!=0.0)
						{
						game.player2.balance-=bet;
						this.pot+=bet;
						}
						for (int handCounter = 0; handCounter < 5; handCounter++)
						{
							g.drawString(this.ranks2[handCounter]+this.suits2[handCounter],500,(550+35*handCounter));
						}
					if(game.prestige[0]>game.prestige[1])
					{
						g.setColor(Color.BLACK.yellow.darker().brighter());
						g.setFont(new Font("ARIAL BOLD", Font.ITALIC, 40));
						g.drawString("PLAYER 1 WINS",370,830);
						winner=1;
						
					}
					else if(game.prestige[0]<game.prestige[1])
					{
						g.setColor(Color.BLACK.yellow.darker().brighter());
						g.setFont(new Font("ARIAL BOLD", Font.ITALIC, 40));
						g.drawString("CPU WINS",410,830);
						winner=2;
					}
					else if (game.prestige[0]==game.prestige[1])
					{
						if(game.ran[0]==1)
						{
							game.ran[0]=14;
						}
						if(game.ran[1]==1)
						{
							game.ran[1]=14;
						}
						if(game.ran[0]>game.ran[1]) {
						g.setColor(Color.BLACK.yellow.darker().brighter());
						g.setFont(new Font("ARIAL BOLD", Font.ITALIC, 40));
						
						
						g.drawString("PLAYER 1 WINS",370,840);
						
						winner=1;
						}
						if(game.ran[0]<game.ran[1])
						{
							g.drawString("CPU WINS",410,830);
							winner=2;
						}
						if(game.ran[0]==game.ran[1])
						{
							game.again=true;
						}
					}
						
					}
					}
				
	  
					if(cpufold==2)
					{
						g.setColor(Color.BLACK.yellow.darker().brighter());
						g.setFont(new Font("ARIAL BOLD", Font.ITALIC, 40));
						g.drawString("PLAYER 1 WINS",370,830);
					
						g.drawString("CPU FOLDS THE HAND",500,(550+35*2));
						winner=1;
					}
					if(winner==1)
					{
						game.player1.balance+=this.pot;
						this.pot=0;
						winner=0;
						
					}
					if(winner==2)
					{
						game.player2.balance+=this.pot;
						this.pot=0.0;
						winner=0;
						
					}
					// play again
				}
				
				
				
			
			
		
		public void gameDraw(){
			
			//get graphics
			Graphics g2 = this.getGraphics();
			
			//draw
			g2.drawImage(image, 0, 0, null);
			
			//refresh
			g2.dispose();
			
		}

	  	public void keyPressed(KeyEvent e) {
	  		// TODO Auto-generated method stub
	  		int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_SPACE) game.setAgain(true);
			if(pointer<5) {
		if(keyCode == KeyEvent.VK_Y) game.y_redraw=true;
		if(keyCode == KeyEvent.VK_N) game.n_redraw=true;
			if(keyCode == KeyEvent.VK_F) game.fold=true;
			}
			if(pointer==5)
			{
			if(keyCode == KeyEvent.VK_UP) up=true;
			if(keyCode == KeyEvent.VK_DOWN) down=true;
			if(keyCode == KeyEvent.VK_ENTER) enter=true;
			}
			
	 	}

	  	public void keyReleased(KeyEvent e) {
	  		// TODO Auto-generated method stub
	  		int keyCode = e.getKeyCode();
	  		if(keyCode == KeyEvent.VK_SPACE) game.setAgain(false);
	  		if(pointer<5) {
			
		if(keyCode == KeyEvent.VK_Y) game.y_redraw=false;
		if(keyCode == KeyEvent.VK_N) game.n_redraw=false;
			if(keyCode == KeyEvent.VK_F) game.fold=false;}
			if(pointer==5)
				{
				if(keyCode == KeyEvent.VK_UP) up=false;
				if(keyCode == KeyEvent.VK_DOWN) down=false;
				if(keyCode == KeyEvent.VK_ENTER) enter=false;
				}
			
	  	}

	  	public void keyTyped(KeyEvent arg0) {
	  		// TODO Auto-generated method stub
	  		
	  	}


	  	
		

	}

