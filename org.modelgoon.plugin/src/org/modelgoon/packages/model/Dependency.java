package org.modelgoon.packages.model;

public class Dependency {

	String packageName;

	int weight;

	public Dependency(final String packageName) {
		super();
		this.packageName = packageName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void incrementWeight() {
		this.weight++;
	}

	public int getWeight() {
		return this.weight;
	}

}
