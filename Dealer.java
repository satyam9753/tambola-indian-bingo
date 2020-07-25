package OOP_Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Dealer implements Runnable 
{
	
	private initial_VALUES initial_VALUES;
	private int count_TotalTokens = 0;
	
	ArrayList<Integer> announcedTokens_Array= new ArrayList<Integer>();
	
	Dealer(initial_VALUES initial_VALUES)
	{
		this.initial_VALUES = initial_VALUES;
		this.count_TotalTokens = 0;
	}
	
	public void run() 
	{
			synchronized(initial_VALUES.LOCK_INITIALIZED) 
			{			
			// following are the conditions for the players
			while (!initial_VALUES.individual_ExecutionFlag[0] && !initial_VALUES.individual_ExecutionFlag[1] &&
												!initial_VALUES.individual_ExecutionFlag[2] && count_TotalTokens<10)
			{
				
				// set announced flag to false before announcing the number
				initial_VALUES.noAnnouncedFlag = false;
				
				// before announcing , all players will set to false
				initial_VALUES.player_CheckSwap[1] = initial_VALUES.player_CheckSwap[0] = initial_VALUES.player_CheckSwap[2] = false;

				// GENERATING RANDOM NUMBERS
				Random rand = new Random();
				initial_VALUES.token_Number = rand.nextInt(initial_VALUES.max - initial_VALUES.min)+1;
				
				announcedTokens_Array.add(initial_VALUES.token_Number);
				System.out.println(initial_VALUES.token_Number);
				  
				count_TotalTokens++;
				initial_VALUES.noAnnouncedFlag = true;
				
				try{
					TimeUnit.SECONDS.sleep(initial_VALUES.wait_TIME); // WE WAIT FOR THE PLAYERS' TO MATCH THE NUMBER THE DEALER JUST ANNOUNCED
				}
				catch(Exception e) {
					System.out.println(e);}
				
				
				initial_VALUES.LOCK_INITIALIZED.notifyAll();
								
				while(!initial_VALUES.player_CheckSwap[0] || !initial_VALUES.player_CheckSwap[1] || !initial_VALUES.player_CheckSwap[2]) 
				{
					try {  // Manager is waiting for both the players to finish searching for the announced token number
						initial_VALUES.LOCK_INITIALIZED.wait(); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}
			System.out.println("Announced Numbers by Moderator/Manager/Dealer are :"+ announcedTokens_Array + "\n");
			
//        <------------------------------------------------DISPLAY--RESULTS-------------------------------------------------------->
			
			
			if(initial_VALUES.individual_ExecutionFlag[0]) 
			{ 
				System.out.println("THIS ROUND'S WINNER IS 'PLAYER-ONE' :) \n\n<--------------------GAME-HAS-FINISHED-------------------->");				
			} 
			
			else if(initial_VALUES.individual_ExecutionFlag[1])
			{ 
				System.out.println("THIS ROUND'S WINNER IS 'PLAYER-TWO' :) \n\n<--------------------GAME-HAS-FINISHED-------------------->");				
			} 
			else if(initial_VALUES.individual_ExecutionFlag[2])
			{ 
				System.out.println("THIS ROUND'S WINNER IS 'PLAYER-THREE' :) \n\n<--------------------GAME-HAS-FINISHED-------------------->");				
			} 
			
			// STEP-11: Checking if NO ONE has won by seeing if all tokens are announced but no winner emerged
			else if(count_TotalTokens == 10) 
			{
				System.out.println("UNFORTUNATELY, WE HAVE NO WINNER FOR THIS ROUND :( \n\n<--------------------GAME-HAS-FINISHED-------------------->");
			}
			
			
//			<------------------------------------------------------------------------------------------------------------------------->
			
			System.out.println("\n PLEASE RUN THE CODE TO PLAY AGAIN :)");
			
			initial_VALUES.completed_GAME = true; // Set 'completed_Game' to TRUE to ensure completion of game
			initial_VALUES.LOCK_INITIALIZED.notifyAll(); // Just in case, any player is left to execute
			
		}		
	}
}
