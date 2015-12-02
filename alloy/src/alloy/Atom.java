package alloy;

import java.util.concurrent.ThreadLocalRandom;

import alloy.Alloy.Matrix;

public class Atom implements Cloneable {

	private long temperature;
	public boolean tag;
	int metals[];

	public Atom() {
		tag = false;
		metals = new int[3];
		initMetals();
	}
	
	public Atom(long temp) {
		tag = false;
		temperature = temp;
		initMetals();
	}
	
	public Atom(long temperature, int metals[]) {
		tag = false;
		this.metals = metals;
		this.temperature = temperature;
	}
	
	private void initMetals() {
		metals = new int[3];
		metals[0] = ThreadLocalRandom.current().nextInt(40);
		metals[1] = ThreadLocalRandom.current().nextInt(40);
		metals[2] = 100-metals[1]-metals[0];
	}

	public long getTemperature() {
		return temperature;
	}
	
	public void setTemperature(long temperature) {
		this.temperature = temperature;
	}
	
	public void changeTemperature(long variation) {
		try {
			temperature = Math.addExact(temperature, variation);
		} catch (ArithmeticException e) {
			if (variation > 0) {
				temperature = Long.MAX_VALUE;
			} else {
				temperature = Long.MIN_VALUE;
			}
		}
	}

}
