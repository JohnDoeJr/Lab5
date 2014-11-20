package com.chiefs.client;

public class Nokta {
	private final double x;
	private final double y;
	
	public Nokta(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getAxisY() {
		return y;
	}

	public double getAxisX() {
		return x;
	}
	
	@Override
	public String toString() {
		return "{" + x + ", " + y + "}"; 
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Nokta)) {
			return false;
		} else {
			Nokta nokta = (Nokta) o;
			return (Double.compare(x, nokta.getAxisX()) == 0) && (Double.compare(y, nokta.getAxisY()) == 0);
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) Double.doubleToLongBits(x);
		result = 31 * result + (int) Double.doubleToLongBits(y);
		
		return result;
	}	
}

