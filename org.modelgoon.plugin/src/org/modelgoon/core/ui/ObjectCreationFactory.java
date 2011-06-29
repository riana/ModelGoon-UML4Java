package org.modelgoon.core.ui;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreationFactory;

public class ObjectCreationFactory implements CreationFactory {

	List<IResource> resources;

	private final Point location = new Point();

	ModelElementFactory modelElementFactory;

	Diagram<?> diagram;

	public ObjectCreationFactory(final Diagram<?> diagram) {
		this.diagram = diagram;
	}

	public void setModelElementFactory(
			final ModelElementFactory modelElementFactory) {
		this.modelElementFactory = modelElementFactory;
	}

	public final void setResources(final List<IResource> javaResources) {
		this.resources = javaResources;
	}

	public final Point setLocation(final Point point) {
		this.location.setLocation(point);
		this.diagram.getRootFigure().translateToRelative(this.location);
		return this.location;
	}

	public final Object getObjectType() {
		return List.class;
	}

	public final Object getNewObject() {
		if (this.modelElementFactory != null) {
			return this.modelElementFactory.createObjectFromDroppedResources(
					this.resources, this.location);
		} else {
			return null;
		}
	}

}
