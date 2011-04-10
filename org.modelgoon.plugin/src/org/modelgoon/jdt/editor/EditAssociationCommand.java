package org.modelgoon.jdt.editor;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.modelgoon.jdt.editparts.AssociationEditPart;
import org.modelgoon.jdt.model.AssociationRelationShip;

public class EditAssociationCommand extends SelectionAction {

	JDTDiagramEditor classDiagramEditor;

	public static final String ID = "Toggle Containment";

	public EditAssociationCommand(final JDTDiagramEditor classDiagramEditor) {
		super(classDiagramEditor);
		this.classDiagramEditor = classDiagramEditor;
	}

	@Override
	protected void init() {
		super.init();
		setId(EditAssociationCommand.ID);
		setText(EditAssociationCommand.ID);
	}

	@Override
	protected boolean calculateEnabled() {
		// we only want enabled if is single selection
		if ((getSelectedObjects().size() != 1)
				|| (!(getSelectedObjects().get(0) instanceof AssociationEditPart))) {
			return false;
		}
		AssociationEditPart cep = (AssociationEditPart) getSelectedObjects()
				.get(0);
		if (cep.getModel() instanceof AssociationRelationShip) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {

		AssociationEditPart cep = (AssociationEditPart) getSelectedObjects()
				.get(0);
		AssociationRelationShip association = cep.getModelElement();
		association.setContainment(!association.isContainment());
		getCommandStack().execute(new Command() {
		});
	}

}
