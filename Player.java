package OOP_Game;

import java.util.*;
import java.util.Random;
public class Player implements Runnable 
{
	private int playerID;	
	private initial_VALUES initial_VALUES;	
	private int totalNumbersFound;    	
	// we create the TOTAL_NUMBERS as static as it'll not change for different instances of this class
	private final static int TOTAL_NUMBERS = 10;	// No. of NUMBERS on each player's ticket
	
	ArrayList<Integer> A= new ArrayList<Integer>(); // this array will contain all the numbers present on each player's card/token
			
	public Player(initial_VALUES initial_VALUES, int playerID) 
	{ 
		this.playerID = playerID; 		
		this.initial_VALUES = initial_VALUES;	
		this.totalNumbersFound = 0;
	

		for(int i = 0; i < TOTAL_NUMBERS; i++) 
		{
			Random rand = new Random();
			A.add(rand.nextInt(initial_VALUES.max - initial_VALUES.min)+3);
		}
		System.out.print("PLAYER-" + (this.playerID+1) + "'s TICKET: ");
		System.out.println(A); // print EACH PLAYER'S TICKET
	}
	
//	<------------------------------------------------------------------------------------------------------------------------------>	
	
	public void run() {
		/* STEP-12: TAKE a lock on initial_VALUES using LOCK_INITIALIZED */ 
		
		synchronized(initial_VALUES.LOCK_INITIALIZED) { 			
			
			// STEP-13: Specify condition both players execute while the game is not complete
			
			while(!initial_VALUES.completed_GAME) { // checks whether game is over on basis of flag
			
				// STEP-14: both players should wait using lock1 of initial_VALUES until a number 
				// is announced by the dealer or its not the chance of the player  
								  
				while(initial_VALUES.player_CheckSwap[playerID] || !initial_VALUES.noAnnouncedFlag) // checks for player's turn and waits until turn comes
				{
					try {
						initial_VALUES.LOCK_INITIALIZED.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				// its important to check this condition again because it is possible that one player may have found all the numbers when the other was waiting
				if(!initial_VALUES.completed_GAME) 
				{						
				// Checking if announced number is on the player's ticket; if the number is found, the player increments the totalNumbersFound found by that player

					for(int i = 0; i < TOTAL_NUMBERS; i++) {						
						if(initial_VALUES.token_Number == A.get(i)) {
							this.totalNumbersFound++;							
							break;
						}
					}
					
					// STEP-16: player checks if it has won the game i.e., it has found all numbers
					// then it should report success
					
					if(this.totalNumbersFound == 3) {
						// a player will set the success flag if that player WINS
						initial_VALUES.individual_ExecutionFlag[this.playerID] = true;						
					}
					
					// player sets its chance flag ................so that other players does not interfere in each other's chance
					initial_VALUES.player_CheckSwap[playerID] = true;
										
					initial_VALUES.LOCK_INITIALIZED.notifyAll();
				}
			}
		}
	}

}
