package org.modelgoon.classdiagram.editParts;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.modelgoon.classdiagram.command.ClassChangeConstraintsCommand;
import org.modelgoon.classdiagram.command.RemoveClassFromDiagramCommand;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.ClassModel;

public class ClassDiagramEditPolicies extends XYLayoutEditPolicy {

	ClassDiagramEditPart editPart;

	public ClassDiagramEditPolicies(final ClassDiagramEditPart editPart) {
		super();
		this.editPart = editPart;
	}

	@Override
	protected Command createChangeConstraintCommand(final EditPart child,
			final Object constraint) {
		Rectangle rect = (Rectangle) constraint;
		return new ClassChangeConstraintsCommand((ClassModel) child.getModel(),
				rect.getLocation());
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		Command createCommand = new Command() {
			@Override
			public void execute() {
				ClassDiagram classDiagram = (ClassDiagram) ClassDiagramEditPolicies.this.editPart
						.getModel();
				List<ClassModel> classes = (List<ClassModel>) request
						.getNewObject();
				for (ClassModel umlClass : classes) {
					classDiagram.addClass(umlClass);
				}
			}
		};
		return createCommand;
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		CompoundCommand cc = new CompoundCommand();
		ClassDiagram classDiagram = (ClassDiagram) getHost().getModel();
		List<EditPart> editParts = ((GroupRequest) request).getEditParts();
		for (EditPart editPart : editParts) {
			cc.add(new RemoveClassFromDiagramCommand((ClassModel) editPart
					.getModel(), classDiagram));
		}
		return cc;
	}

}
