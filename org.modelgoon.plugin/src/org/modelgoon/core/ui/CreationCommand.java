package org.modelgoon.core.ui;

import org.eclipse.gef.commands.Command;
import org.modelgoon.core.ModelElement;

public abstract class CreationCommand extends Command {

	ModelElement newObject;

	public void setNewElement(final ModelElement newObject) {
		this.newObject = newObject;
	}

	@Override
	public void execute() {
		attachNewElement(this.newObject);
	}

	public abstract void attachNewElement(ModelElement newObject);

}
