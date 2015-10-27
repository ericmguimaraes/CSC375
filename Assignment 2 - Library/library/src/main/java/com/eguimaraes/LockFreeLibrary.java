package com.eguimaraes;

import java.util.concurrent.ThreadLocalRandom;

public class LockFreeLibrary extends Library {

	@Override
	public void rentOrReturnBook(int position) {
		if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
			int localVar = books[position].get();
			while (!books[position].compareAndSet(localVar, localVar + 1)) {
				localVar = books[position].get();
			}
		} else {
			int localVar = books[position].get();
			if (localVar != 0)
				while (!books[position].compareAndSet(localVar, localVar - 1)) {
					localVar = books[position].get();
					if (localVar == 0)
						break;
				}
		}
	}
	
	@Override
	public int lookBook(int position) {
		return books[position].get();
	}

}
