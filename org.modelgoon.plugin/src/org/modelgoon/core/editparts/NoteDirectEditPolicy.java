package org.modelgoon.core.editparts;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.modelgoon.core.NoteFigure;

public class NoteDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		String labelText = (String) request.getCellEditor().getValue();
		NoteEditPart label = (NoteEditPart) getHost();
		SetNoteContentsCommand command = new SetNoteContentsCommand(
				label.getModelElement(), labelText);
		return command;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		((NoteFigure) getHostFigure()).setContent(value);
		System.out.println("NoteDirectEditPolicy.showCurrentEditValue()");
		// hack to prevent async layout from placing the cell editor twice.
		getHostFigure().getUpdateManager().performUpdate();
	}

}
