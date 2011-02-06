package org.modelgoon.core.ui;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class GraphicalNodeEditPolicyImpl extends GraphicalNodeEditPolicy {

	AbstractComponentEditPart editPart;

	public GraphicalNodeEditPolicyImpl(final AbstractComponentEditPart editPart) {
		super();
		this.editPart = editPart;
	}

	@Override
	protected Command getConnectionCompleteCommand(
			final CreateConnectionRequest request) {
		return new Command() {
		};
	}

	@Override
	protected Command getConnectionCreateCommand(
			final CreateConnectionRequest request) {
		return new Command() {
		};
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
