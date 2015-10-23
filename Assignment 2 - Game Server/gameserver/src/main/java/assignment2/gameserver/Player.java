package assignment2.gameserver;

import java.util.concurrent.ThreadLocalRandom;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;

public class Player extends Thread {

	int id;

	GameServer gs;

	public static final int NUMBER_OF_ITERACTIONS = 50;

	public Player(int id, GameServer gs) {
		this.id = id;
		this.gs = gs;
	}

	@GenerateMicroBenchmark
	@Override
	public void run() {
		int i = 0;
		while (i < NUMBER_OF_ITERACTIONS) {
			int random = ThreadLocalRandom.current().nextInt();
			if (random % 2 == 0) {
				gs.modifyGameState(GameState.pick(), id);
			} else {
				gs.getGameState(id);
			}
			i++;
		}
	}

}
