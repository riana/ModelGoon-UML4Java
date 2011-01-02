package org.modelgoon.classdiagram.command;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.modelgoon.classdiagram.model.ClassModel;

public class ClassChangeConstraintsCommand extends Command {

	private final ClassModel element;
	private Point oldConstraint;
	private final Point newConstraint;

	public ClassChangeConstraintsCommand(final ClassModel element,
			final Point newConstraint) {
		this.newConstraint = newConstraint;
		this.element = element;
	}

	@Override
	public void execute() {
		this.oldConstraint = this.element.getLocation();
		this.element.setLocation(this.newConstraint);
	}

	@Override
	public void redo() {
		this.element.setLocation(this.newConstraint);
	}

	@Override
	public void undo() {
		this.element.setLocation(this.oldConstraint);
	}
}
