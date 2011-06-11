/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.modelgoon.core.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.modelgoon.core.ui.AbstractComponentEditPolicy;

public class NotelEditPolicy extends AbstractComponentEditPolicy {

	public static final String ID = "$Native Drop Request";

	@Override
	public Command getCommand(final Request request) {
		// if (NotelEditPolicy.ID.equals(request.getType())) {
		// return getDropTextCommand(request);
		// }
		return super.getCommand(request);
	}

	// protected Command getDropTextCommand(final Request request) {
	// LogicLabelCommand command = new LogicLabelCommand(
	// (LogicLabel) getHost().getModel(), (String) request.getData());
	// return command;
	// }

	@Override
	public EditPart getTargetEditPart(final Request request) {
		// if (NativeDropRequest.ID.equals(request.getType())) {
		// return getHost();
		// }
		System.out.println("NotelEditPolicy.getTargetEditPart() : "
				+ request.getType());
		return super.getTargetEditPart(request);
	}

}
