package com.eguimaraes;

import java.util.concurrent.ThreadLocalRandom;

public class SynchronizedLibrary extends Library {

	@Override
	public void rentOrReturnBook(int position) {
		synchronized (books) {
			if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
				books[position].set(books[position].get() + 1);
			} else {
				if (books[position].get() != 0)
					books[position].set(books[position].get() - 1);
			}
		}
	}

	@Override	
	public int lookBook(int position) {
		synchronized (books) {
			return books[position].get();
		}
	}

}
