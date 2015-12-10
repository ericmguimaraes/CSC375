package Server;

import java.io.Serializable;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

import Comunication.Communication;
import Domain.Alloy;
import Domain.ForkHeater;

@SuppressWarnings("serial")
public class ForkHeaterServer extends RecursiveAction implements Serializable {

	Alloy alloy;
	int start;
	int length;
	int threshold;
	int end;

	public ForkHeaterServer(int start, int length, Alloy alloy, int threshold) {
		this.alloy = alloy;
		this.start = start;
		this.length = length;
		this.threshold = threshold;
		end = start + length;
	}

	public ForkHeaterServer(Alloy alloy, int sThreshold) {
		this.alloy = alloy;
		this.start = 0;
		this.length = alloy.getOrigin().lenght;
		this.threshold = sThreshold;
	}

	@Override
	protected void compute() {
		if (length < threshold) {
			computeDirectly();
			return;
		}
		
		Communication c = new Communication(true);
		int split = length / 2;
		ForkHeater left = new ForkHeater(start, split, alloy, threshold);
		left.fork();
		ForkHeater right = new ForkHeater(start + split, length - split, alloy, threshold);
		ForkHeater fork = c.sendAndWait(right);
		left.join();
		System.out.println("Integrating processed data");
		c.integrate(alloy,fork);
	}

	private void computeDirectly() {
		int x, y;
		for (int n = start; n < end; n++) {
			x = n % alloy.w;
			y = n / alloy.w;
			if (alloy.getOrigin(x, y).tag)
				throw new RuntimeException("Tried to modify already processed atom");
			else {
				if ((x != 0 || y != 0) && (x != alloy.w - 1 || y != alloy.h - 1))
					update(x, y);
				alloy.getOrigin(x, y).tag = true;
			}
		}
	}

	private void update(int xGoal, int yGoal) {
		double temp = 0;
		for (int i = 0; i < 3; i++) {
			temp += alloy.constants[i] * sumOfTempMultByMetalPercentageInEachNeighbor(xGoal, yGoal, i);
		}
		temp = temp / countNeighbors(xGoal, yGoal);
		if (Double.isNaN(temp)) {
			int r = ThreadLocalRandom.current().nextInt();
			if (r % 2 == 0)
				temp = Double.MAX_VALUE;
			else
				temp = -Double.MAX_VALUE;
		} else if (Double.isInfinite(temp)) {
			if (temp == Double.NEGATIVE_INFINITY) {
				temp = -Double.MAX_VALUE;
			} else {
				temp = Double.MAX_VALUE;
			}
		}

		alloy.getDestination(xGoal, yGoal).setTemperature(temp);
	}

	private double sumOfTempMultByMetalPercentageInEachNeighbor(int xGoal, int yGoal, int c) {
		double t = 0;
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, -1, -1, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, -1, 0, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			double aux = neighborTempMultByMetalC(xGoal, yGoal, -1, 1, c);
			t += aux;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, 0, 1, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, 1, 1, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, 1, 0, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, 1, -1, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t += neighborTempMultByMetalC(xGoal, yGoal, 0, -1, c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return t;
	}

	private double neighborTempMultByMetalC(int xGoal, int yGoal, int xVariation, int yVariation, int i) {
		int x = xGoal + xVariation;
		int y = yGoal + yVariation;
		double temp = alloy.getOrigin(x, y).getTemperature();
		float metal = alloy.getOrigin(x, y).metals[i];
		float res = (float) (temp * metal);
		return (double) (res);
	}

	private int countNeighbors(int xGoal, int yGoal) {
		int count = 0;
		try {
			alloy.getOrigin(xGoal - 1, yGoal - 1);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal - 1, yGoal + 0);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal - 1, yGoal + 1);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal + 0, yGoal + 1);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal + 1, yGoal + 1);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal + 1, yGoal + 0);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal + 1, yGoal - 1);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			alloy.getOrigin(xGoal + 0, yGoal - 1);
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return count;
	}

}
