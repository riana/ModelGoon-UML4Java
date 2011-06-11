package org.modelgoon.core.ui;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class MoveBendpointCommand extends Command {
	private final Point newLocation;
	private Point oldLocation;
	private final int index;
	private final AbstractLinkEditPart model;
	private AbsoluteBendpoint bendpoint;

	public MoveBendpointCommand(final AbstractLinkEditPart model,
			final int index, final Point newLocation) {
		this.model = model;
		this.index = index;
		this.newLocation = newLocation;
	}

	@Override
	public void execute() {
		AbsoluteBendpoint bendpoint = (AbsoluteBendpoint) this.model
				.getBendpoint(this.index);
		this.oldLocation = bendpoint.getLocation();
		bendpoint = new AbsoluteBendpoint(this.newLocation);
		// bendpoint.setLocation(this.newLocation);
		this.model.replaceBendpoint(this.index, bendpoint);
	}

	@Override
	public void redo() {
		this.bendpoint = new AbsoluteBendpoint(this.newLocation);
		this.model.replaceBendpoint(this.index, this.bendpoint);
	}

	@Override
	public void undo() {
		this.bendpoint = new AbsoluteBendpoint(this.oldLocation);
		this.model.replaceBendpoint(this.index, this.bendpoint);
	}
}