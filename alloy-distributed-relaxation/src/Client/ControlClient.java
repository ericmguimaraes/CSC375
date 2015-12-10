package Client;

import java.util.concurrent.ForkJoinPool;

import Comunication.Communication;
import Domain.Alloy;
import Domain.AlloyPanel;
import Domain.ForkHeater;

public class ControlClient {

	static ForkJoinPool pool;

	static ForkHeater heater;

	static Alloy alloy;

	static int s, t, width, height, threshold;

	static float c1, c2, c3;

	static final int LIMIT_ITERACTIONS = 9000;

	static int iteractions = 0;

	static AlloyPanel panel;

	public static double INITIAL_TEMPERATURE = 0;

	public static void main(String[] args) throws InterruptedException {
		while (true) {
			Communication c = new Communication(false);
			heater = c.receiveFork();
			ForkJoinPool pool = new ForkJoinPool();
			pool.invoke(heater);
			heater.join();
			System.out.println("Sending response from client");
			c.send(heater);
		}
	}

}
