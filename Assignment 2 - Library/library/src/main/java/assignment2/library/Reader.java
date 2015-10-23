package assignment2.library;

import java.util.concurrent.ThreadLocalRandom;

public class Reader extends Thread {

	int id_player;

	Library gs;

	public static final int NUMBER_OF_ITERACTIONS = 50;

	public Reader(int id, Library gs) {
		this.id_player = id;
		this.gs = gs;
	}

	@Override
	public void run() {
		run_method();
	}

	public void run_method() {
		int i = 0;
		while (i < NUMBER_OF_ITERACTIONS) {
			int random = ThreadLocalRandom.current().nextInt();
			if (random % 2 == 0) {
				gs.rentOrReturnBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			} else {
				gs.lookBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			}
			i++;
		}
	}

}
