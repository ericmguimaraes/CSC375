package alloy;

import java.util.concurrent.RecursiveAction;

public class ForkHeater extends RecursiveAction {

	/**
	 * 
	 */
	private static final double serialVersionUID = -990299664702406792L;
	Alloy alloy;
	int start;
	int length;
	int threshold;
	int end;

	public ForkHeater(int start, int length, Alloy alloy, int threshold) {
		this.alloy = alloy;
		this.start = start;
		this.length = length;
		this.threshold = threshold;
		end = start + length;
	}

	public ForkHeater(Alloy alloy, int sThreshold) {
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

		int split = length / 2;
		invokeAll(new ForkHeater(start, split, alloy, threshold),
				new ForkHeater(start + split, length - split, alloy, threshold));
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
			temp = adddoubleSafe(temp, (double) alloy.constants[i] * updateAUX(xGoal, yGoal, i));
		}
		alloy.getDestination(xGoal, yGoal).setTemperature(temp / countNeighbors(xGoal, yGoal));
	}

	private double updateAUX(int xGoal, int yGoal, int c) {
		double t = 0;
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, -1, -1, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, -1, 0, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, -1, 1, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, 0, 1, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, 1, 1, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, 1, 0, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, 1, -1, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = adddoubleSafe(t, getNeighborValue(xGoal, yGoal, 0, -1, c));
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return t;
	}

	private double getNeighborValue(int xGoal, int yGoal, int xVariation, int yVariation, int i) {
		int x = xGoal + xVariation;
		int y = yGoal + yVariation;
		double temp = alloy.getOrigin(x, y).getTemperature();
		float metal = alloy.getOrigin(x, y).metals[i];
		float res = (float) (temp * metal);
		return (double) (res);
	}

	public double mutiplydoubleSafe(double l1, double l2) {
		return l1 * l2;
	}

	public double adddoubleSafe(double l1, double l2) {
		return l1 + l2;
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
