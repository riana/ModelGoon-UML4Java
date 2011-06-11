package org.modelgoon.sequencediagram.ui;

public interface SequenceElement {

	public abstract int getVerticalExtent();

	public abstract void setYPosition(int yPosition);

	public abstract int getMinX();

	public abstract int getMaxX();

	public boolean isEmpty();

}
