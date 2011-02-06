package org.modelgoon.core.ui;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

public class BendpointEditPolicyImpl extends BendpointEditPolicy {

	AbstractLinkEditPart linkEditPart;

	public BendpointEditPolicyImpl(final AbstractLinkEditPart linkEditPart) {
		super();
		this.linkEditPart = linkEditPart;
	}

	@Override
	protected Command getCreateBendpointCommand(final BendpointRequest request) {
		int index = request.getIndex();
		Point loc = request.getLocation();
		return new CreateBendpointCommand(this.linkEditPart, index, loc);
	}

	@Override
	protected Command getDeleteBendpointCommand(final BendpointRequest request) {
		int index = request.getIndex();
		return new DeleteBendpointCommand(this.linkEditPart, index);
	}

	@Override
	protected Command getMoveBendpointCommand(final BendpointRequest request) {
		int index = request.getIndex();
		Point loc = request.getLocation();
		return new MoveBendpointCommand(this.linkEditPart, index, loc);
	}

}
