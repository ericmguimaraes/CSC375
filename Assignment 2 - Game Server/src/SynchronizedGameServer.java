
public class SynchronizedGameServer extends GameServer {

	@Override
	public void modifyGameState(GameState state, int id) {
		synchronized (states) {
			states[id] = state;
		}
	}

	@Override
	public GameState getGameState(int id) {
		synchronized (states) {
			return states[id];
		}
	}

}
