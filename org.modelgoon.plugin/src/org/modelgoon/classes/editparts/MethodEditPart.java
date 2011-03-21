package org.modelgoon.classes.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.modelgoon.classdiagram.figures.FieldFigure;
import org.modelgoon.classes.model.Method;
import org.modelgoon.core.ui.AbstractComponentEditPart;

public class MethodEditPart extends AbstractComponentEditPart<Method> {

	FieldFigure figure;

	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doRefreshVisuals(final Method model) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(model.getVisibility().getVisibilityString());
		buffer.append(model.getName());
		buffer.append("(");
		for (int i = 0; i < model.getParameterTypes().size(); i++) {
			String parametersType = model.getParameterTypes().get(i);
			buffer.append(parametersType);
			if (i < model.getParameterTypes().size() - 1) {
				buffer.append(", ");
			}

		}
		buffer.append(")");
		if (model.getType() != null) {
			buffer.append(":");
			buffer.append(model.getType());
		}
		this.figure.setTextualDescription(buffer.toString());
	}

	@Override
	protected IFigure createFigure() {
		this.figure = new FieldFigure();
		return this.figure;
	}

}
