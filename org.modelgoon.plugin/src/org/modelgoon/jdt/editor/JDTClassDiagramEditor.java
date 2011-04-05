package org.modelgoon.jdt.editor;

import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.classes.editparts.ClassCompartmentModel;
import org.modelgoon.classes.editparts.ListEditPart;
import org.modelgoon.core.ModelLoader;
import org.modelgoon.core.Note;
import org.modelgoon.core.editparts.NoteEditPart;
import org.modelgoon.core.ui.Diagram;
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

public class JDTClassDiagramEditor extends Diagram<UMLModel> {

	ModelLoader modelLoader = new ModelLoader();

	public JDTClassDiagramEditor() {
		super(new UMLModel());
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
		addAction(new UpdateDiagramCommand(this), GEFActionConstants.GROUP_VIEW);
		addSelectionAction(new EditVisualPreferencesCommand(this),
				GEFActionConstants.GROUP_VIEW);
		addSelectionAction(new ShowMethodSequenceCommand(this),
				GEFActionConstants.GROUP_EDIT);
		addSelectionAction(new OpenInEditorCommand(this),
				GEFActionConstants.GROUP_EDIT);
	}

	@Override
	protected UMLModel load(final String file) {

		IJavaProject javaProject = this.modelLoader.getContainer(file);

		UMLModel model = new UMLModel();
		model.setJavaProject(javaProject);

		return model;
	}

	@Override
	protected void save(final UMLModel model, final String filePath) {
		// TODO Auto-generated method stub

	}

}
