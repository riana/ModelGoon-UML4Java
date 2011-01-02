package org.modelgoon.classdiagram.editParts;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class ClassEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command getDeleteCommand(final GroupRequest request) {
		Command childCommand = createDeleteCommand(request);
		Command parentCommand = getParentDeleteCommand(request);
		CompoundCommand com = new CompoundCommand();
		if (childCommand != null) {
			com.add(childCommand);
		}
		if (parentCommand != null) {
			com.add(parentCommand);
		}

		if ((childCommand == null) && (parentCommand == null)) {
			return null;
		} else {
			return com;
		}
	}

	@Override
	protected Command createDeleteCommand(final GroupRequest deleteRequest) {
		// TODO Auto-generated method stub
		return super.createDeleteCommand(deleteRequest);
	}

	protected Command getParentDeleteCommand(final GroupRequest request) {
		request.setEditParts(getHost());
		request.setType(RequestConstants.REQ_DELETE_DEPENDANT);
		return getHost().getParent().getCommand(request);
	}
}
