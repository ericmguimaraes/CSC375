import java.util.concurrent.ThreadLocalRandom;

public enum GameState {
	INITIAL_STATE, RUNNING_STATE, 
	JUMPING_STATE, FALLING_STATE, 
	GAME_OVER_STATE, RESTART_STATE;
	
	
	public static GameState pick(){
		int random = ThreadLocalRandom.current().nextInt(GameState.values().length);
		switch(random){
			case 0: return GameState.INITIAL_STATE;
			case 1: return GameState.RUNNING_STATE;
			case 2: return GameState.JUMPING_STATE;
			case 3: return GameState.FALLING_STATE;
			case 4: return GameState.GAME_OVER_STATE;
			case 5: return GameState.RESTART_STATE;
			default: return GameState.INITIAL_STATE;
		}
	}
}
