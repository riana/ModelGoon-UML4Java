package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.ModelGoonIcons;
import org.modelgoon.core.ModelLoader;
import org.modelgoon.core.Note;
import org.modelgoon.core.editparts.NoteEditPart;
import org.modelgoon.core.ui.Diagram;
import org.modelgoon.core.ui.ModelElementFactory;
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
		registerEditPart(Note.class, NoteEditPart.class);
		registerEditPart(PackageElement.class, PackageEditPart.class);
		registerEditPart(DependencyLink.class, DependencyEditPart.class);

	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot paletteRoot = new PaletteRoot();
		PaletteGroup group = new PaletteGroup("Creation tools");

		ImageDescriptor imgDesc = ModelGoonActivator
				.getImageDescriptor(ModelGoonIcons.COMMENT_ICON);

		group.add(new CreationToolEntry("Note", "Insert a new Note in ",
				new SimpleFactory(Note.class), imgDesc, imgDesc));
		paletteRoot.add(group);
		return paletteRoot;
	}

	@Override
	protected void registerActions() {
		addAction(new UpdateDiagramCommand(this), GEFActionConstants.GROUP_VIEW);
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
