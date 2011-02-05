package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.modelgoon.classdiagram.editParts.ClassDiagramEditPart;
import org.modelgoon.classdiagram.editParts.ClassEditPart;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.UmlClass;
import org.modelgoon.core.ui.Diagram;
import org.modelgoon.core.ui.IPersistenceEventHandler;

public class PackageDiagramEditor extends Diagram {

	public PackageDiagramEditor() {
		super(new ClassDiagram());
		setPersistenceEventHandler(new IPersistenceEventHandler() {

			public void save(final IProgressMonitor monitor) {
				System.out
						.println("PackageDiagramEditor.PackageDiagramEditor().new IPersistenceEventHandler() {...}.save()");

			}

			public void load(final String file) {
				System.out
						.println("PackageDiagramEditor.PackageDiagramEditor().new IPersistenceEventHandler() {...}.load()");
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
		registerEditPart(ClassDiagram.class, ClassDiagramEditPart.class);
		registerEditPart(UmlClass.class, ClassEditPart.class);

	}

	@Override
	protected void registerActions() {

	}

}
