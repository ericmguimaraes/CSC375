package alloy;

import java.util.concurrent.ThreadLocalRandom;

import alloy.Alloy.Matrix;

public class Atom implements Cloneable {

	private long temperature;
	public boolean tag;
	float metals[];

	public Atom() {
		tag = false;
		metals = new float[3];
		initMetals();
	}

	public Atom(long temp) {
		tag = false;
		temperature = temp;
		initMetals();
	}

	public Atom(long temperature, float metals[]) {
		tag = false;
		this.metals = metals;
		this.temperature = temperature;
	}

	private void initMetals() {
		metals = new float[3];
		metals[0] = randomInRange((float) 0.45,(float) 0.5);
		metals[1] = randomInRange((float) 0.35,(float) 0.4);
		metals[2] = 1 - metals[1] - metals[0];
	}

	public float randomInRange(float min, float max) {
		float range = max - min;
		float scaled = ThreadLocalRandom.current().nextFloat() * range;
		float shifted = scaled + min;
		return shifted;
	}

	public long getTemperature() {
		return temperature;
	}

	public void setTemperature(long temperature) {
		this.temperature = temperature;
	}

}
