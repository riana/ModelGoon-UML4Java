package org.modelgoon.classdiagram.command;

import org.eclipse.gef.commands.Command;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.ClassModel;

public class RemoveClassFromDiagramCommand extends Command {

	private final ClassModel umlClass;

	private final ClassDiagram diagram;

	public RemoveClassFromDiagramCommand(final ClassModel umlClass,
			final ClassDiagram diagram) {
		super();
		this.umlClass = umlClass;
		this.diagram = diagram;
	}

	@Override
	public void execute() {
		this.diagram.removeClass(this.umlClass);
	}

}
