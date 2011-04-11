package org.modelgoon.core.ui;

import org.eclipse.gef.commands.Command;
import org.modelgoon.core.ModelElement;

public class LinkCreationCommand extends Command {

	ModelElement source;

	ModelElement target;

	public void setSource(final ModelElement source) {
		this.source = source;
	}

	public void setTarget(final ModelElement target) {
		this.target = target;
	}

	public ModelElement getSource() {
		return this.source;
	}

	public ModelElement getTarget() {
		return this.target;
	}

}
