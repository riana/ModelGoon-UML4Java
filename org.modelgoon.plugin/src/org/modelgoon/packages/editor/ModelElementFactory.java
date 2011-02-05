package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.resources.IResource;

public interface ModelElementFactory {
	public abstract List createObjectFromDroppedResources(
			final List<IResource> resources);
}
