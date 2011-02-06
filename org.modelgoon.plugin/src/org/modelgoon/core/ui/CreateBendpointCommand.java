package org.modelgoon.core.ui;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class CreateBendpointCommand extends Command {
	private Point location;
	private int index;
	AbstractLinkEditPart model;

	private AbsoluteBendpoint bendpoint;

	public CreateBendpointCommand(final AbstractLinkEditPart linkEditPart,
			final int index, final Point location) {
		this.model = linkEditPart;
		this.index = index;
		this.location = location;
	}

	@Override
	public void execute() {
		this.bendpoint = new AbsoluteBendpoint(this.location);
		this.bendpoint.setLocation(this.location);
		this.model.addBendpoint(this.bendpoint, this.index);
	}

	@Override
	public void redo() {
		this.model.addBendpoint(this.bendpoint, this.index);
	}

	@Override
	public void undo() {
		this.model.removeBendpoint(this.bendpoint);
	}
}
