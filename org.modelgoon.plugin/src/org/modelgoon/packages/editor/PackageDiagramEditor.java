package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.modelgoon.core.ui.Diagram;
import org.modelgoon.packages.editparts.DependencyEditPart;
import org.modelgoon.packages.editparts.PackageDiagramEditPart;
import org.modelgoon.packages.editparts.PackageEditPart;
import org.modelgoon.packages.model.DependencyLink;
import org.modelgoon.packages.model.PackageDiagram;
import org.modelgoon.packages.model.PackageElement;

public class PackageDiagramEditor extends Diagram {

	public PackageDiagramEditor() {
		super(new PackageDiagram());
		PackageDiagramLoader loader = new PackageDiagramLoader();
		setPersistenceEventHandler(loader);

		setModelElementFactory(new ModelElementFactory() {

			public List createObjectFromDroppedResources(
					final List<IResource> resources) {
				System.out
						.println("PackageDiagramEditor.PackageDiagramEditor().new ModelElementFactory() {...}.createObjectFromDroppedResources()");
				return null;
			}
		});
		registerEditPart(PackageDiagram.class, PackageDiagramEditPart.class);
		registerEditPart(PackageElement.class, PackageEditPart.class);
		registerEditPart(DependencyLink.class, DependencyEditPart.class);

	}

	@Override
	protected void registerActions() {

	}

}
