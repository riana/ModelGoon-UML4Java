package org.modelgoon.sequencediagram.ui;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;

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

	@Override
	public void paint(final Graphics graphics) {
		super.paint(graphics);
		graphics.translate(getLocation());
		graphics.drawString(this.guard, 10, 5);
	}

	@Override
	protected void layout() {
		Dimension guardSize = TextUtilities.INSTANCE.getTextExtents(this.guard,
				getFont());
		int minimunWidth = 10 + guardSize.width + 10;
		setMinimumSize(new Dimension(minimunWidth, getMinimumSize().height));
		super.layout();

	}

}
