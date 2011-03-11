package org.modelgoon.core;

import org.modelgoon.core.ui.CreationCommand;
import org.modelgoon.core.ui.RootElementEditPart;

public class CreateNoteCommand extends CreationCommand {

	RootElementEditPart<?> packageDiagramEditPart;

	public CreateNoteCommand(final RootElementEditPart<?> packageDiagramEditPart) {
		super();
		this.packageDiagramEditPart = packageDiagramEditPart;
	}

	@Override
	public void attachNewElement(final ModelElement newObject) {
		this.packageDiagramEditPart.getModelElement().addNote((Note) newObject);

	}

}
