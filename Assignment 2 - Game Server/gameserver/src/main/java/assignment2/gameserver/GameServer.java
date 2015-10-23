package assignment2.gameserver;
public abstract class GameServer {

	public static final int NUMBER_OF_PLAYERS = 20;

	GameState[] states;

	Player[] players;

	public GameServer() {
		players = new Player[NUMBER_OF_PLAYERS];
		states = new GameState[NUMBER_OF_PLAYERS];
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
			players[i] = new Player(i, this);
			states[i] = GameState.INITIAL_STATE;
		}
		startPlayers();
	}
	
	public void startPlayers(){
		for (int i = 0; i < players.length; i++) {
			players[i].start();
		}
	}

	public abstract void modifyGameState(GameState state, int id);

	public abstract GameState getGameState(int id);

}
