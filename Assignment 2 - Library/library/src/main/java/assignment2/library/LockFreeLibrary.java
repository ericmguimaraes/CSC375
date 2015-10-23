package assignment2.library;

import java.util.concurrent.ThreadLocalRandom;

public class LockFreeLibrary extends Library {

	@Override
	public void rentOrReturnBook(int position) {
		if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
			books[position].compareAndSet(books[position].get(), books[position].get() + 1);
		} else {
			if (books[position].get() != 0)
				books[position].compareAndSet(books[position].get(), books[position].get() - 1);
		}
	}

	@Override
	public int lookBook(int position) {
		return books[position].get();
	}

}
