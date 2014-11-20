package com.chiefs.client;

import com.chiefs.client.Nokta;

public class Forme {

	private float radius;

	public Forme(float r) {
		this.radius = r;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setRadius(float r) {
		this.radius = r;
	}

	public boolean test(Nokta nokta) {
		double x = nokta.getAxisX();
		double y = nokta.getAxisY();

		return (x > 0 && y > 0 && Double.compare(x, radius) < 0 && Double.compare(y, radius / 2) < 0)
				|| (x < 0 && y > 0 && Double.compare((x + radius / 2) / (radius / 2), y / radius) > 0)
				|| (x > 0 && y < 0 && Double.compare(Math.sqrt(x * x + y * y), radius / 2) < 0)
				|| (x == 0 && y > 0 && Double.compare(y, radius / 2) < 0)
				|| (y == 0 && x > 0 && Double.compare(x, radius / 2) < 0);
	}
}
