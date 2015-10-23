package assignment2.library;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Library {

	public static final int NUMBER_OF_READERS = 20;
	public static final int NUMBER_OF_BOOKS = 40;

	AtomicInteger[] books;

	Reader[] readers;

	public Library() {
		readers = new Reader[NUMBER_OF_READERS];
		books = new AtomicInteger[NUMBER_OF_BOOKS];
		for (int i = 0; i < NUMBER_OF_READERS; i++) {
			readers[i] = new Reader(i, this);
		}
		for (int i = 0; i < NUMBER_OF_BOOKS; i++) {
			books[i] = new AtomicInteger(ThreadLocalRandom.current().nextInt(4));
		}
		startReaders();
	}

	public void startReaders() {
		for (int i = 0; i < readers.length; i++) {
			readers[i].start();
		}
	}

	public abstract void rentOrReturnBook(int position);

	public abstract int lookBook(int position);

}
