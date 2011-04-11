package org.modelgoon.core.ui;

import org.eclipse.gef.commands.Command;

public class CreateConnectionCommand extends Command {

	Object source;

	Object destination;

	public CreateConnectionCommand(final Object source) {
		super();
		this.source = source;
	}

	public void setDestination(final Object destination) {
		this.destination = destination;
	}

	@Override
	public void execute() {
		System.out.println("CreateConnectionCommand.execute() : " + this.source
				+ "/" + this.destination);
	}

}
