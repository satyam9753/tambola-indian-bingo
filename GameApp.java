// this is the driver code

// we do not want to put excess load on the 'main' by putting all our code in there as 'main' thread;
// thus, we create threads; here, I'm using 'Runnable Interface'

// BASIC INFO / STEPS ABOUT THREADING (as we proceed in our project assignment) : 
// 1. Create a thread class implementing Runnable interface
// 2. Override 'run()' method
// 3. Create object of the class
// 4. Invoke 'start()' method using the object


package OOP_Game;

public class GameApp 
{
	
	// 'main' method is representing the 'MAIN THREAD'
	public static void main(String... args) {
		// Execution Context
		
		System.out.println("\n<--------------------GAME-HAS-STARTED-------------------->\n");
		
		final initial_VALUES play_Game  = initial_VALUES.getInstance();           // creating GAME (i.e. an object for 'initial_VALUES' class)
		final Dealer dealer  = new Dealer(play_Game);	  // creating a DEALER (i.e. an object for 'Dealer' class)
		
		// creating objects(3 Players) for 'Player' class giving each of them 2 parameters-> the initial_values(pre-initialized game data) & player-ID
		final Player player_ONE = new Player(play_Game, 0);
		final Player player_TWO = new Player(play_Game, 1);
		final Player player_THREE = new Player(play_Game, 2);

		// beginning the life-cycle of the following threads
		Thread dealer_THREAD  = new Thread(dealer); 
		
		Thread player_ONE_THREAD = new Thread(player_ONE);
		Thread player_TWO_THREAD = new Thread(player_TWO);
		Thread player_THREE_THREAD = new Thread(player_THREE);
		
		// all the jobs / child threads will now get executed SIMULTANEOUSLY
		
//---------------------------------------------------------------------------------------------------------------------------------------
		
		// start all the threads
		// here, multiple threads can get mix-matched, so we should synchronize them
		// 'start()' will internally execute the 'run()' method
		
		// since, all of these are running in PARALLEL, so we provide 'synchronization with lock' to avoid any clashes 
		dealer_THREAD.start();
		
		player_ONE_THREAD.start();
		player_TWO_THREAD.start();
		player_THREE_THREAD.start();
	}
}