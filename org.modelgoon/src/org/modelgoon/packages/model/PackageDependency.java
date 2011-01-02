package org.modelgoon.packages.model;

public class PackageDependency {

	Package source;

	Package target;

	boolean cyclic;

	public PackageDependency(final Package source, final Package target) {
		super();
		this.source = source;
		this.target = target;
	}

	public Package getSource() {
		return this.source;
	}

	public void setSource(final Package source) {
		this.source = source;
	}

	public Package getTarget() {
		return this.target;
	}

	public void setTarget(final Package target) {
		this.target = target;
	}

	public boolean isCyclic() {
		return this.cyclic;
	}

	public void setCyclic(final boolean cyclic) {
		this.cyclic = cyclic;
	}

}
