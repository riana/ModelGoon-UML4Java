package org.modelgoon.core.ui;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;

public interface ModelElementFactory {
	public abstract List createObjectFromDroppedResources(
			final List<IResource> resources, Point location);
}
