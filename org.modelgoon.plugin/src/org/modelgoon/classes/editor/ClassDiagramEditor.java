package org.modelgoon.classes.editor;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.classes.editparts.AssociationEditPart;
import org.modelgoon.classes.editparts.ClassDiagramEditPart;
import org.modelgoon.classes.editparts.ClassElementEditPart;
import org.modelgoon.classes.editparts.ExtensionEditPart;
import org.modelgoon.classes.model.Association;
import org.modelgoon.classes.model.ClassDiagram;
import org.modelgoon.classes.model.ClassElement;
import org.modelgoon.classes.model.Extension;
import org.modelgoon.core.Note;
import org.modelgoon.core.editparts.NoteEditPart;
import org.modelgoon.core.ui.Diagram;

public class ClassDiagramEditor extends Diagram<ClassDiagram> {

	public ClassDiagramEditor() {
		super(new ClassDiagram());
		registerEditPart(ClassDiagram.class, ClassDiagramEditPart.class);
		registerEditPart(ClassElement.class, ClassElementEditPart.class);
		registerEditPart(Note.class, NoteEditPart.class);
		registerEditPart(Extension.class, ExtensionEditPart.class);
		registerEditPart(Association.class, AssociationEditPart.class);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot paletteRoot = new PaletteRoot();
		PaletteGroup group = new PaletteGroup("Creation tools");

		ImageDescriptor imgDesc = ModelGoonActivator
				.getImageDescriptor("icons/sticky_notes-16x16.png");

		group.add(new CreationToolEntry("Note", "Insert a new Note in ",
				new SimpleFactory(Note.class), imgDesc, imgDesc));
		paletteRoot.add(group);
		return paletteRoot;
	}

	@Override
	protected void registerActions() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ClassDiagram load(final String file) {
		ClassDiagram diagram = new ClassDiagram();
		ClassElement node = new ClassElement();
		node.setQualifiedName("Node");
		node.setAbstract(true);
		node.setLocation(200, 50);
		diagram.addClass(node);

		ClassElement folder = new ClassElement();
		folder.setQualifiedName("Folder");
		folder.setLocation(300, 150);
		diagram.addClass(folder);

		ClassElement fileClass = new ClassElement();
		fileClass.setQualifiedName("File");
		fileClass.setLocation(130, 150);
		diagram.addClass(fileClass);

		Extension extension = new Extension();
		extension.setChild(fileClass);
		extension.setParent(node);

		extension = new Extension();
		extension.setChild(folder);
		extension.setParent(node);

		Association association = new Association();
		association.addPoint(new Point(321, 72));
		association.setChild(folder);
		association.setParent(node);
		association.setName("itsChildren");
		association.setMultiplicity("0..*");
		association.setContainment(true);

		return diagram;
	}

	@Override
	protected void save(final ClassDiagram model, final String filePath) {
		// TODO Auto-generated method stub

	}

}
