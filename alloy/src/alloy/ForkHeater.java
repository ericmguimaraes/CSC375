package alloy;

import java.util.concurrent.RecursiveAction;

public class ForkHeater extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -990299664702406792L;
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
				update(x, y);
				alloy.getOrigin(x, y).tag = true;
			}
		}
	}

	private void update(int xGoal, int yGoal) {
		long temp = 0;
		for (int i = 0; i < 3; i++) {
			temp = addLongSafe(temp, (long) alloy.constants[i] * updateAUX(xGoal, yGoal, i));
		}
		alloy.getDestination(xGoal, yGoal).setTemperature(temp / countNeighbors(xGoal, yGoal));
	}

	private long updateAUX(int xGoal, int yGoal, int c) {
		long t = 0, temp = 0;
		int count = 0;
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, -1, -1, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, -1, 0, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, -1, 1, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, 0, 1, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, 1, 1, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, 1, 0, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, 1, -1, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			t = addLongSafe(t, getNeighborValue(xGoal, yGoal, 0, -1, c));
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return t;
	}

	private long getNeighborValue(int xGoal, int yGoal, int xVariation, int yVariation, int i) {
		int x = xGoal + xVariation;
		int y = yGoal + yVariation;
		return alloy.getOrigin(x, y).getTemperature()*(alloy.getOrigin(x, y).metals[i]/100);
	}

	public Long mutiplyLongSafe(long l1, long l2) {
		try {
			l1 = Math.multiplyExact(l1, l2);
		} catch (ArithmeticException e) {
			if (l1 > 0 && l2 > 0) {
				l1 = Long.MAX_VALUE;
			} else {
				l1 = Long.MIN_VALUE;
			}
		}
		return l1;
	}

	public Long addLongSafe(long l1, long l2) {
		try {
			l1 = Math.addExact(l1, l2);
		} catch (ArithmeticException e) {
			if (l1 > 0 && l2 > 0) {
				l1 = Long.MAX_VALUE;
			} else {
				l1 = Long.MIN_VALUE;
			}
		}
		return l1;
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
