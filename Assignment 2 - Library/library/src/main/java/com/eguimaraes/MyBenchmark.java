package com.eguimaraes;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class MyBenchmark {

	public static final int NUMBER_OF_BOOKS = 40;
	public static final int NUMBER_OF_ITERACTIONS = 200;
	public static final int PERCENTAGE_OF_READ = 75;
	AtomicInteger[] books;

	@Setup
	public void createBooks() {
		books = new AtomicInteger[NUMBER_OF_BOOKS];
		for (int i = 0; i < NUMBER_OF_BOOKS; i++) {
			books[i] = new AtomicInteger(ThreadLocalRandom.current().nextInt(4));
		}
	}

	public void synchronizedRentOrReturnBook(int position) {
		synchronized (books) {
			if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
				books[position].set(books[position].get() + 1);
			} else {
				if (books[position].get() != 0)
					books[position].set(books[position].get() - 1);
			}
		}
	}

	public int synchronizedLookBook(int position) {
		synchronized (books) {
			return books[position].get();
		}
	}

	public void lockFreeRentOrReturnBook(int position) {
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

	public int lockFreeLookBook(int position) {
		return books[position].get();
	}

	@Benchmark
	public void synchronizedBenchmark() {
		int i = 0;
		while (i < NUMBER_OF_ITERACTIONS) {
			int random = ThreadLocalRandom.current().nextInt(100);
			if (random > PERCENTAGE_OF_READ) {
				synchronizedRentOrReturnBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			} else {
				synchronizedLookBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			}
			i++;
		}
	}

	@Benchmark
	public void lockFreeBenchmark() {
		int i = 0;
		while (i < NUMBER_OF_ITERACTIONS) {
			int random = ThreadLocalRandom.current().nextInt(100);
			if (random > PERCENTAGE_OF_READ) {
				lockFreeRentOrReturnBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			} else {
				lockFreeLookBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			}
			i++;
		}
	}
}