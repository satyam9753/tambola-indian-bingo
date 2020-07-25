package OOP_Game;

// using SINGLETON design pattern
// this is where all the initial Game Information is available 
public class initial_VALUES
{
	private static initial_VALUES instanceUnique;
	

	public int wait_TIME = 1; // set waiting time for dealer after which he'll unveil the next token number
	public int num_players = 3;  // initializing total number of players (keeping fixed)
	public int max = 50, min = 0; 

	public int token_Number = 0;
	public boolean completed_GAME = false, noAnnouncedFlag = false;  // these are flags that store whether 'game is completed' and 'announced status' flag
	public boolean[] individual_ExecutionFlag = new boolean[num_players]; // this flag checks if player is successfully executed
	public boolean[] player_CheckSwap = new boolean[num_players];
	
	public Object LOCK_INITIALIZED = new Object(); 
	
	
	private initial_VALUES()
	{
		token_Number = 0;
		wait_TIME = 1;
		num_players = 3;
		max = 50; min =0;
	}
	
	public static initial_VALUES getInstance() 
	{
		if(instanceUnique == null)
			instanceUnique = new initial_VALUES();
		
		return instanceUnique;
	}
}
