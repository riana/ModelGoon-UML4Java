package org.modelgoon.core.ui;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.modelgoon.core.ModelElement;

public class RootElementEditPolicy extends XYLayoutEditPolicy {

	RootElementEditPart<?> editPart;

	public RootElementEditPolicy(final RootElementEditPart<?> editPart) {
		super();
		this.editPart = editPart;
	}

	@Override
	protected Command createChangeConstraintCommand(final EditPart child,
			final Object constraint) {
		Rectangle rect = (Rectangle) constraint;
		return new ChangeElementConstraintsCommand(
				(AbstractComponentEditPart<?>) child, rect);
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		Command createCommand = null;

		ModelElement modelElement = (ModelElement) request.getNewObject();
		if (modelElement != null) {
			modelElement.setLocation(request.getLocation().x,
					request.getLocation().y);
			createCommand = this.editPart.getCreationCommand(modelElement);
		}
		return createCommand;
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		CompoundCommand cc = new CompoundCommand();
		List<EditPart> editParts = ((GroupRequest) request).getEditParts();
		for (EditPart editPart : editParts) {
			AbstractComponentEditPart<?> componentEditPart = (AbstractComponentEditPart<?>) editPart;
			Command deleteCommand = componentEditPart.getDeleteCommand();
			if (deleteCommand != null) {
				cc.add(deleteCommand);
			}
		}
		return cc;
	}
}