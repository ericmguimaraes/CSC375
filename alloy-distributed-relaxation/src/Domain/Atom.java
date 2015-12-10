package Domain;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class Atom implements Cloneable, Serializable {

	private double temperature;
	public boolean tag;
	public float metals[];

	public Atom() {
		tag = false;
		metals = new float[3];
		initMetals();
	}

	public Atom(double temp) {
		tag = false;
		temperature = temp;
		initMetals();
	}

	public Atom(double temperature, float metals[]) {
		tag = false;
		this.metals = metals;
		this.temperature = temperature;
	}

	private void initMetals() {
		metals = new float[3];
		metals[0] = randomInRange((float) 0.45,(float) 0.60);
		metals[1] = randomInRange((float) 0.10,(float) 0.40);
		metals[2] = 1 - metals[1] - metals[0];
	}

	public float randomInRange(float min, float max) {
		float range = max - min;
		float scaled = ThreadLocalRandom.current().nextFloat() * range;
		float shifted = scaled + min;
		return shifted;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

}
