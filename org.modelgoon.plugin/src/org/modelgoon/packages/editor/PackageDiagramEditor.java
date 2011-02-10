package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.IJavaProject;
import org.modelgoon.core.ModelLoader;
import org.modelgoon.core.ui.Diagram;
import org.modelgoon.dao.DAOException;
import org.modelgoon.packages.editparts.DependencyEditPart;
import org.modelgoon.packages.editparts.PackageDiagramEditPart;
import org.modelgoon.packages.editparts.PackageEditPart;
import org.modelgoon.packages.model.DependencyLink;
import org.modelgoon.packages.model.PackageDiagram;
import org.modelgoon.packages.model.PackageElement;

public class PackageDiagramEditor extends Diagram<PackageDiagram> implements
		ModelElementFactory {

	ModelLoader modelLoader = new ModelLoader();

	public PackageDiagramEditor() {
		super(new PackageDiagram());
		this.modelLoader
				.addMapping("org/modelgoon/packages/xml/PackageAnalysis.cas");

		setModelElementFactory(this);

		registerEditPart(PackageDiagram.class, PackageDiagramEditPart.class);
		registerEditPart(PackageElement.class, PackageEditPart.class);
		registerEditPart(DependencyLink.class, DependencyEditPart.class);

	}

	@Override
	protected void registerActions() {

	}

	public List createObjectFromDroppedResources(
			final List<IResource> resources, final Point location) {
		PackageDiagram model = getModel();
		for (IResource resource : resources) {
			model.addPackageFromResource(resource, location);
		}
		model.consolidate();
		return null;
	}

	@Override
	protected PackageDiagram load(final String file) {
		IJavaProject javaProject = this.modelLoader.getContainer(file);
		PackageDiagram model = new PackageDiagram();
		try {
			model = this.modelLoader.loadData(file);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// PackageDiagram diagram = new PackageDiagram();
		model.setJavaProject(javaProject);
		// model.setJavaProject(javaProject);
		// for (Package pkg : model.getPackages()) {
		// PackageElement p = new PackageElement();
		// p.setQualifiedName(pkg.getName());
		// diagram.addPackage(p);
		// p.setLocation(pkg.getX(), pkg.getY());
		// }
		model.consolidate();
		return model;
	}

	@Override
	protected void save(final PackageDiagram model, final String filePath) {
		try {
			this.modelLoader.saveData(model, filePath);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
