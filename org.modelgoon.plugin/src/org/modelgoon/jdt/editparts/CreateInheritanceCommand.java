package org.modelgoon.jdt.editparts;

import org.modelgoon.core.ModelElement;
import org.modelgoon.core.ui.LinkCreationCommand;

public class CreateInheritanceCommand extends LinkCreationCommand {

	@Override
	public void execute() {
		ModelElement source = getSource();
		ModelElement target = getTarget();
		System.out.println("CreateInheritanceCommand.execute() : " + source
				+ " => " + target);
	}

}
