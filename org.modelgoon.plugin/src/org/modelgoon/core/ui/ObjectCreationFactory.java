package org.modelgoon.core.ui;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreationFactory;
import org.modelgoon.packages.editor.ModelElementFactory;

public class ObjectCreationFactory implements CreationFactory {

	List<IResource> resources;

	private final Point location = new Point();

	ModelElementFactory modelElementFactory;

	public ObjectCreationFactory() {
		super();
	}

	public void setModelElementFactory(
			final ModelElementFactory modelElementFactory) {
		this.modelElementFactory = modelElementFactory;
	}

	public final void setResources(final List<IResource> javaResources) {
		this.resources = javaResources;
	}

	public final Point setLocation(final Point point) {
		return this.location.setLocation(point);
	}

	public final Object getObjectType() {
		return List.class;
	}

	public final Object getNewObject() {
		if (this.modelElementFactory != null) {
			return this.modelElementFactory
					.createObjectFromDroppedResources(this.resources);
		} else {
			return null;
		}
	}

}
