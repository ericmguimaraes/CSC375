package com.eguimaraes;

import java.util.concurrent.ThreadLocalRandom;

import org.openjdk.jmh.annotations.Scope;

@org.openjdk.jmh.annotations.State(Scope.Thread)
public class Reader extends Thread {

	Library gs;

	public static final int NUMBER_OF_ITERACTIONS = 200;
	
	public static final int PERCENTAGE_OF_READ = 75;

	public Reader(Library gs) {
		this.gs = gs;
	}

	@Override
	public void run() {
		run_method();
	}

	public void run_method() {
		int i = 0;
		while (i < NUMBER_OF_ITERACTIONS) {
			int random = ThreadLocalRandom.current().nextInt(100);
			if (random > PERCENTAGE_OF_READ) {
				gs.rentOrReturnBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			} else {
				gs.lookBook(ThreadLocalRandom.current().nextInt(Library.NUMBER_OF_BOOKS));
			}
			i++;
		}
	}

}
