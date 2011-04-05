package org.modelgoon.jdt.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.modelgoon.classes.figures.FeatureFigure;
import org.modelgoon.core.ui.AbstractComponentEditPart;
import org.modelgoon.jdt.model.Method;

public class MethodEditPart extends AbstractComponentEditPart<Method> {

	FeatureFigure figure;

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
		this.figure.setFeatureSummary(model.toString());
	}

	@Override
	protected IFigure createFigure() {
		this.figure = new FeatureFigure();
		return this.figure;
	}

}
