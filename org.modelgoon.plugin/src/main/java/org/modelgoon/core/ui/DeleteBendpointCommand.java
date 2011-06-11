package org.modelgoon.core.ui;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.gef.commands.Command;

public class DeleteBendpointCommand extends Command {
	private int index;
	private AbstractLinkEditPart model;
	private Bendpoint bendpoint;

	public DeleteBendpointCommand(final AbstractLinkEditPart model,
			final int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		this.bendpoint = this.model.removeBendpoint(this.index);
	}

	@Override
	public void redo() {
		this.model.removeBendpoint(this.index);
	}

	@Override
	public void undo() {
		this.model.addBendpoint(this.bendpoint, this.index);
	}
}
