import java.util.concurrent.ThreadLocalRandom;

public class Player extends Thread {

	int id;

	GameServer gs;

	public Player(int id, GameServer gs) {
		this.id = id;
		this.gs = gs;
	}

	@Override
	public void run() {
		while (true) {
			int random = ThreadLocalRandom.current().nextInt();
			if (random % 2 == 0) {
				gs.modifyGameState(GameState.pick(), id);
			} else {
				gs.getGameState(id);
			}
		}
	}

}
