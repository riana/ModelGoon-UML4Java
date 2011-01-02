package org.modelgoon.sequencediagram.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;

public class SequenceDiagramFigure extends FragmentFigure {

	Map<String, LifelineFigure> lifelinesRegistry = new HashMap<String, LifelineFigure>();

	int preferedWidth = 0;

	public SequenceDiagramFigure() {
		super();
		setFirstOffset(30);
	}

	public void addLifeline(final String objectName) {
		LifelineFigure lifelineFigure = new LifelineFigure();
		lifelineFigure.setLabel(objectName);
		add(lifelineFigure);
		this.lifelinesRegistry.put(objectName, lifelineFigure);
	}

	public void addMessage(final String message, final String source,
			final String destination) {
		MessageConnection messageConnection = createMessageConnection(message,
				source, destination);
		add(messageConnection);
	}

	public MessageConnection createMessageConnection(final String message,
			final String source, final String destination) {
		LifelineFigure sourceFigure = this.lifelinesRegistry.get(source);
		LifelineFigure destFigure = this.lifelinesRegistry.get(destination);
		return new MessageConnection(message, sourceFigure, destFigure);
	}

	@Override
	protected void layout() {
		super.layout();
		int x = 5;
		int lifelineLength = getVerticalExtent() == 0 ? 40 + this.firstOffset
				: getVerticalExtent();
		for (Object children : getChildren()) {
			if (children instanceof LifelineFigure) {
				LifelineFigure lifelineFigure = (LifelineFigure) children;
				lifelineFigure.setLocation(new Point(x, 0));
				lifelineFigure.setSize(lifelineFigure.getPreferredSize().width,
						lifelineLength);
				x += lifelineFigure.getPreferredSize().width + 5;
				lifelineFigure.layout();
			}

		}

		updateElementsLayout();

		setSize(getParent().getSize().width, getPreferredSize().height);
	}

	// @Override
	// public void paint(final Graphics graphics) {
	// super.paint(graphics);
	// Rectangle bounds = new Rectangle(getBounds());
	// graphics.drawRectangle(bounds.crop(new Insets(1)));
	// }

}
