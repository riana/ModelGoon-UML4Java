package org.modelgoon.jdt.communication.editor;

import org.modelgoon.core.Note;
import org.modelgoon.core.editparts.NoteEditPart;
import org.modelgoon.jdt.communication.editParts.CommunicationClassEditPart;
import org.modelgoon.jdt.communication.editParts.CommunicationEditPart;
import org.modelgoon.jdt.editor.ClassModelElementFactory;
import org.modelgoon.jdt.editor.JDTDiagramEditor;
import org.modelgoon.jdt.editparts.JDTClassDiagramEditPart;
import org.modelgoon.jdt.model.CommunicationRelationship;
import org.modelgoon.jdt.model.UMLClass;
import org.modelgoon.jdt.model.UMLModel;

public class JDTCommunicationDiagramEditor extends JDTDiagramEditor {

	public JDTCommunicationDiagramEditor() {
		super(new UMLModel());
		this.modelLoader.addMapping("org/modelgoon/jdt/xml/UMLModel.cas");
		this.modelLoader
				.addMapping("org/modelgoon/jdt/xml/CommunicationDiagram.cas");
		setModelElementFactory(new ClassModelElementFactory(this));
		registerEditPart(UMLModel.class, JDTClassDiagramEditPart.class);
		registerEditPart(UMLClass.class, CommunicationClassEditPart.class);
		registerEditPart(CommunicationRelationship.class,
				CommunicationEditPart.class);

		registerEditPart(Note.class, NoteEditPart.class);
	}

}
