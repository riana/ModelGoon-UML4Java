package org.modelgoon.jdt.editor;

import org.eclipse.gef.SharedImages;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.modelgoon.classes.editparts.ClassCompartmentModel;
import org.modelgoon.classes.editparts.ListEditPart;
import org.modelgoon.core.Note;
import org.modelgoon.core.editparts.NoteEditPart;
import org.modelgoon.jdt.editparts.AssociationEditPart;
import org.modelgoon.jdt.editparts.ExtensionEditPart;
import org.modelgoon.jdt.editparts.FieldEditPart;
import org.modelgoon.jdt.editparts.JDTClassDiagramEditPart;
import org.modelgoon.jdt.editparts.MethodEditPart;
import org.modelgoon.jdt.editparts.UMLClassEditPart;
import org.modelgoon.jdt.model.AssociationRelationShip;
import org.modelgoon.jdt.model.ExtensionRelationShip;
import org.modelgoon.jdt.model.Field;
import org.modelgoon.jdt.model.Method;
import org.modelgoon.jdt.model.UMLClass;
import org.modelgoon.jdt.model.UMLModel;

public class JDTClassDiagramEditor extends JDTDiagramEditor {

	public JDTClassDiagramEditor() {
		super(new UMLModel());
		this.modelLoader.addMapping("org/modelgoon/jdt/xml/UMLModel.cas");
		this.modelLoader.addMapping("org/modelgoon/jdt/xml/ClassDiagram.cas");
		setModelElementFactory(new ClassModelElementFactory(this));
		registerEditPart(UMLModel.class, JDTClassDiagramEditPart.class);
		registerEditPart(UMLClass.class, UMLClassEditPart.class);
		registerEditPart(ClassCompartmentModel.class, ListEditPart.class);
		registerEditPart(Field.class, FieldEditPart.class);
		registerEditPart(Method.class, MethodEditPart.class);
		registerEditPart(ExtensionRelationShip.class, ExtensionEditPart.class);
		registerEditPart(AssociationRelationShip.class,
				AssociationEditPart.class);

		registerEditPart(Note.class, NoteEditPart.class);
	}

	@Override
	protected void fillPalette(final PaletteRoot paletteRoot,
			final PaletteGroup group) {
		ConnectionCreationToolEntry connectionToolEntry = new ConnectionCreationToolEntry(
				"Inheritance", "Creates inheritance", new SimpleFactory(
						ExtensionRelationShip.class),
				SharedImages.DESC_SELECTION_TOOL_16,
				SharedImages.DESC_SELECTION_TOOL_16);
		group.add(connectionToolEntry);
	}

}
