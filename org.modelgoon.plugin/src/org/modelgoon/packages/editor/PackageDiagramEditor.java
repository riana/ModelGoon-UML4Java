package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.modelgoon.core.ui.Diagram;
import org.modelgoon.core.ui.IPersistenceEventHandler;
import org.modelgoon.packages.editparts.PackageDiagramEditPart;
import org.modelgoon.packages.editparts.PackageEditPart;
import org.modelgoon.packages.model.PackageDiagram;
import org.modelgoon.packages.model.PackageElement;

public class PackageDiagramEditor extends Diagram {

	public PackageDiagramEditor() {
		super(new PackageDiagram());
		setPersistenceEventHandler(new IPersistenceEventHandler() {

			public void save(final IProgressMonitor monitor) {
				System.out
						.println("PackageDiagramEditor.PackageDiagramEditor().new IPersistenceEventHandler() {...}.save()");

			}

			public Object load(final String file) {
				System.out
						.println("PackageDiagramEditor.PackageDiagramEditor().new IPersistenceEventHandler() {...}.load()");
				PackageDiagram diagram = new PackageDiagram();
				PackageElement pkg1 = new PackageElement();
				pkg1.setQualifiedName("com.test.package1");
				diagram.addPackage(pkg1);

				PackageElement pkg2 = new PackageElement();
				pkg2.setQualifiedName("com.test.package2");
				diagram.addPackage(pkg2);
				return diagram;
			}
		});

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

	}

	@Override
	protected void registerActions() {

	}

}
