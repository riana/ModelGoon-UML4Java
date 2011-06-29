package org.modelgoon.core.ui;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class ChangeElementConstraintsCommand extends Command {

	AbstractComponentEditPart<?> editPart;

	Rectangle newConstraint;

	Rectangle oldConstraint;

	public ChangeElementConstraintsCommand(
			final AbstractComponentEditPart<?> editPart,
			final Rectangle newConstraint) {
		super();
		this.editPart = editPart;
		this.newConstraint = newConstraint;
	}

	@Override
	public void execute() {
		this.oldConstraint = this.editPart.getCurrentConstraints();
		this.editPart.handleConstraintsChange(this.newConstraint);
	}

	@Override
	public void redo() {
		this.editPart.handleConstraintsChange(this.newConstraint);
	}

	@Override
	public void undo() {
		this.editPart.handleConstraintsChange(this.oldConstraint);
	}
}
