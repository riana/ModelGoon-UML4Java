package org.modelgoon.core.ui;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class GraphicalNodeEditPolicyImpl extends GraphicalNodeEditPolicy {

	AbstractComponentEditPart<?> editPart;

	public GraphicalNodeEditPolicyImpl(
			final AbstractComponentEditPart<?> editPart) {
		super();
		this.editPart = editPart;
	}

	@Override
	protected Command getConnectionCompleteCommand(
			final CreateConnectionRequest request) {
		Object target = getHost().getModel();
		LinkCreationCommand command = (LinkCreationCommand) request
				.getStartCommand();
		if (command != null) {
			command.setTarget(this.editPart.getModelElement());

		}
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand(
			final CreateConnectionRequest request) {

		Object source = getHost().getModel();
		LinkCreationCommand linkCreationCommand = this.editPart
				.getLinkCreationCommand(request.getNewObject().getClass());
		if (linkCreationCommand != null) {
			request.setStartCommand(linkCreationCommand);
			linkCreationCommand.setSource(this.editPart.getModelElement());
		}

		return linkCreationCommand;
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return new Command() {
		};
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return new Command() {
		};
	}

}
