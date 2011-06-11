package org.modelgoon.classes.editor;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.classes.editparts.AssociationEditPart;
import org.modelgoon.classes.editparts.AttributeEditPart;
import org.modelgoon.classes.editparts.ClassCompartmentModel;
import org.modelgoon.classes.editparts.ClassDiagramEditPart;
import org.modelgoon.classes.editparts.ClassElementEditPart;
import org.modelgoon.classes.editparts.ExtensionEditPart;
import org.modelgoon.classes.editparts.ListEditPart;
import org.modelgoon.classes.editparts.MethodEditPart;
import org.modelgoon.classes.model.Association;
import org.modelgoon.classes.model.Attribute;
import org.modelgoon.classes.model.ClassDiagram;
import org.modelgoon.classes.model.ClassElement;
import org.modelgoon.classes.model.Extension;
import org.modelgoon.classes.model.Method;
import org.modelgoon.classes.model.Visibility;
import org.modelgoon.core.ModelLoader;
import org.modelgoon.core.Note;
import org.modelgoon.core.editparts.NoteEditPart;
import org.modelgoon.core.ui.Diagram;

public class ClassDiagramEditor extends Diagram<ClassDiagram> {

	ModelLoader modelLoader = new ModelLoader();

	public ClassDiagramEditor() {
		super(new ClassDiagram());
		registerEditPart(ClassDiagram.class, ClassDiagramEditPart.class);
		registerEditPart(ClassElement.class, ClassElementEditPart.class);
		registerEditPart(ClassCompartmentModel.class, ListEditPart.class);
		registerEditPart(Attribute.class, AttributeEditPart.class);
		registerEditPart(Method.class, MethodEditPart.class);
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

		IJavaProject javaProject = this.modelLoader.getContainer(file);

		ClassDiagram diagram = new ClassDiagram();

		ClassElement node = new ClassElement();
		node.setQualifiedName("Node");
		node.setAbstract(true);
		node.setLocation(200, 50);
		diagram.addClass(node);

		Attribute nameAttribute = new Attribute();
		nameAttribute.setName("name");
		nameAttribute.setVisibility(Visibility.PRIVATE);
		nameAttribute.setType("String");
		node.addAttribute(nameAttribute);

		Method method = new Method();
		method.setName("toString");
		method.setType("String");
		method.setVisibility(Visibility.PUBLIC);
		node.addMethod(method);

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
