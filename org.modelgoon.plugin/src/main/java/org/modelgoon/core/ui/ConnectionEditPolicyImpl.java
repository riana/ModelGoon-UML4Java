package org.modelgoon.core.ui;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class ConnectionEditPolicyImpl extends GraphicalNodeEditPolicy {

	@Override
	public void activate() {
		super.activate();
	}

	// @Override
	// protected Command getDeleteCommand(final GroupRequest request) {
	// System.out.println("ConnectionEditPolicyImpl.getDeleteCommand()");
	// return new Command() {
	// };
	// }

	@Override
	protected IFigure getFeedbackLayer() {
		System.out.println("ConnectionEditPolicyImpl.getFeedbackLayer()");
		/*
		 * Fix for Bug# 66590 Feedback needs to be added to the scaled feedback
		 * layer
		 */
		return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
	}

	@Override
	protected Command getConnectionCompleteCommand(
			final CreateConnectionRequest request) {
		return null;
	}

	@Override
	protected Command getConnectionCreateCommand(
			final CreateConnectionRequest request) {
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
