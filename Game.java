package Win-or-Fold;

import java.util.Arrays;
import java.util.Scanner;

public class Game 
{

  private final int HAND_SIZE = 5;
	public boolean again = true;
	public int prestige[]=new int[2];
	public int point=0;
	public int ran[]=new int[2];
	public boolean y_redraw=false;
	public boolean n_redraw=false;
	public boolean fold=false;
	public String ranks[]=new String[5];
	public String suits[]=new String[5];
	public String answer="";
	
	// instantiate Deck and Player
	Scanner scan = new Scanner(System.in);
	Deck deck = new Deck();
	Player player1 = new Player(100,495);
	Player player2=new Player(880,495);
	Card[] hand1;
	Card[] hand2;
	
	
	
	// plays the game
	public void play()
	{
		while (again == true)
		{
			// fill deck
			deck.fillDeck();
			
			// shuffle
			deck.shuffle();
			
			// player draws
			hand1 = player1.draw(deck);
			hand2=player2.draw(deck);
			
			// sort hand		
			Arrays.sort(hand1);
			Arrays.sort(hand2);
			
			// player redraws
			//this.checkHand(hand1);
			//hand1= this.redraw(hand1);
			
			// display hand again
			// this.makeHand(); //<--- TA ! un-comment this and change makeHand()
			//this.checkHand(hand1);
			//System.out.println("\nCPU's Cards : \n");
			//this.checkHand(hand2);
			
			// sort hand		
			//Arrays.sort(hand1);
			
			// evaluate the hand
			this.evaluate(hand1,"PLAYER 1");
			point=1;
			this.evaluate(hand2,"CPU");
			
			// play again
		}
		System.out.println("Thanks for playing! =]");
	}
	
	// makes a hand (for TA; testing purposes)
	/*public void makeHand()
	{
		hand[0].rank = 1;
		hand[1].rank = 2;
		hand[2].rank = 3;
		hand[3].rank = 4;
		hand[4].rank = 5;
		
		hand[0].suit = 1;
		hand[1].suit = 1;
		hand[2].suit = 1;
		hand[3].suit = 1;
		hand[4].suit = 1;
	}*/
	
	// tells player cards in hand
	public void checkHand(Card[] hand)
	{
		for (int handCounter = 0; handCounter <5; handCounter++)
		{
			ranks[handCounter]=this.displayrank(hand[handCounter]);
			suits[handCounter]=this.displaysuit(hand[handCounter]);
		}
	}
	
	// asks if player wants to redraw
	//public Card[] redraw(Card[] hand)
	//{
		//for (int counter = 0; counter < 5; counter++)
	//	{
			
		//	if (x_redraw == true)
		//	{
			//	hand[counter] = player1.redraw(counter, deck);
		//	}
		//	x_redraw=false;
	//	}
	//	return hand;
	//}
	
	
	// evaluates the hand
	public void evaluate(Card[] hand,String s)
	{
		if (this.royalFlush(hand) == 1)
		{
			answer=s+" has a royal flush";
		}
		else if (this.straightFlush(hand) == 1)
		{
			answer=s+" has a straight flush";
		}
		else if (this.fourOfaKind(hand) == 1)
		{
			answer=s+" has four of a kind";
		}
		else if (this.fullHouse(hand) == 1)
		{
			answer=s+" has a full house";
		}
		else if (this.flush(hand) == 1)
		{
			answer=s+" has a flush";
		}
		else if (this.straight(hand) == 1)
		{
			answer=s+" has a straight";
		}
		else if (this.triple(hand) == 1)
		{
			answer=s+" has a triple";
		}
		else if (this.twoPairs(hand) == 1)
		{
			answer=s+" has two pairs";
		}
		else if (this.pair(hand) == 1)
		{
			answer=s+" has a pair";
		}
		else
		{
			int highCard = this.highCard(hand);
			answer=s+" has highest card " + highCard;
		}
	}
	
	// checks for a royal flush
	public int royalFlush(Card[] hand)
	{
		prestige[point]=9;
		if (hand[0].rank == 1 && hand[1].rank == 10 && hand[2].rank == 11 &&
				hand[3].rank == 12 && hand[4].rank == 13)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	// checks for a straight flush
	public int straightFlush(Card[] hand)
	{
		prestige[point]=8;
		ran[point]=hand[4].rank;
		for (int counter = 1; counter < 5; counter++)
		{
			if (hand[0].suit != hand[counter].suit)
			{
				return 0;
			}
		}
		for (int counter2 = 1; counter2 < 5; counter2++)
		{
			if (hand[counter2 - 1].rank != (hand[counter2].rank - 1))
			{
				return 0;
			}
				
		}
		return 1;
		
	}
	
	// checks for four of a kind
	public int fourOfaKind(Card[] hand)
	{
		prestige[point]=7;
		ran[point]=hand[2].rank;
		if (hand[0].rank != hand[3].rank && hand[1].rank != hand[4].rank)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	// checks for full house
	public int fullHouse(Card[] hand)
	{
		prestige[point]=6;
		ran[point]=hand[2].rank;
		int comparison = 0;
		for (int counter = 1; counter < 5; counter++)
		{
			if (hand[counter - 1].rank == hand[counter].rank)
			{
				comparison++;
			}
		}
		if (comparison == 3)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	// checks for flush
	public int flush(Card[] hand)
	{
		ran[point]=hand[4].rank;
		prestige[point]=5;
		for (int counter = 1; counter < 5; counter++)
		{
			if (hand[0].suit != hand[counter].suit)
			{
				return 0;
			}
		}
		return 1;
	}
	
	// check for straight
	public int straight(Card[] hand)
	{
		ran[point]=hand[4].rank;
		prestige[point]=4;
		for (int counter2 = 1; counter2 < 5; counter2++)
		{
			if (hand[counter2 - 1].rank != (hand[counter2].rank - 1))
			{
				return 0;
			}
				
		}
		return 1;
	}
	
	// checks for triple
	public int triple(Card[] hand)
	{
		ran[point]=hand[2].rank;
		prestige[point]=3;
		if (hand[0].rank == hand[2].rank || hand[2].rank == hand[4].rank|| hand[1].rank==hand[3].rank )
		{
			return 1;
		}
		return 0;
	}
	
	// checks for two pairs
	public int twoPairs(Card[] hand)
	{
		
		prestige[point]=2;
		int check = 0;
		for(int counter = 1; counter < 5; counter++)
		{
			if (hand[counter - 1].rank == hand[counter].rank)
			{
				check++;
				if(hand[counter].rank==1)
					ran[point]=1;
				
			}
		}
		if (check == 2)
		{
			if(ran[point]!=1)
				ran[point]=hand[3].rank;
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	// check for pair
	public int pair(Card[] hand)
	{
		prestige[point]=1;
		int check = 0;
		for(int counter = 1; counter < 5; counter++)
		{
			if (hand[counter - 1].rank == hand[counter].rank)
			{
				check++;
				ran[point]=hand[counter].rank;
			}
		}
		if (check == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	// find highest card
	public int highCard(Card[] hand)
	{
		prestige[point]=0;
		int highCard = 0;
		
		for (int counter = 0; counter < 5; counter++)
		{
			if (hand[counter].rank > highCard)
			{
				highCard = hand[counter].rank;
			}
		}
		ran[point]=highCard;
		return highCard;
	}
	
	// asks user if they want to play again
	public void setAgain(boolean x)
	{
		again=x;
	}
	
	// generates string for each card in hand
	public String displayrank(Card card)
	{
		if (card.rank == 1)
		{
			return "Ace of ";
		}
		if (card.rank == 2)
		{
			return "Two of ";
		}
		if (card.rank == 3)
		{
			return "Three of ";
		}
		if (card.rank == 4)
		{
			return "Four of ";
		}
		if (card.rank == 5)
		{
			return "Five of ";
		}
		if (card.rank == 6)
		{
			return "Six of ";
		}
		if (card.rank == 7)
		{
			return "Seven of ";
		}
		if (card.rank == 8)
		{
			return "Eight of ";
		}
		if (card.rank == 9)
		{
			return "Nine of ";
		}
		if (card.rank == 10)
		{
			return "Ten of ";
		}
		if (card.rank == 11)
		{
			return "Jack of ";
		}
		if (card.rank == 12)
		{
			return "Queen of ";
		}
		if (card.rank == 13)
		{
			return "King of ";
		}
		else return "Ace of ";
	}
	public String displaysuit(Card card)
	{
		if (card.suit == 1)
		{
			return "Spades\n";
		}
		if (card.suit == 2)
		{
			return "Hearts\n";
		}
		if (card.suit == 3)
		{
			return "Diamonds\n";
		}
		if (card.suit == 4)
		{
			return "Clubs\n";
		}
		else return "Clubs\n";
		
	}
}