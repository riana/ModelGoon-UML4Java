package org.modelgoon.sequencediagram.ui;

public class CombinedFragmentFigure extends FragmentFigure {

	String guard = "";

	public CombinedFragmentFigure() {
		super();
		setBorderInsets(0);
		setFirstOffset(25);
	}

	public void setGuard(final String guard) {
		this.guard = guard;
	}

	public String getGuard() {
		return this.guard;
	}

}
