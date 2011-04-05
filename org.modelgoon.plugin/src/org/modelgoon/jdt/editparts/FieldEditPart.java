package org.modelgoon.jdt.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.jdt.core.IJavaElement;
import org.modelgoon.classes.figures.FeatureFigure;
import org.modelgoon.core.ui.AbstractComponentEditPart;
import org.modelgoon.jdt.model.Field;

public class FieldEditPart extends AbstractComponentEditPart<Field> implements
		JavaElementProvider {

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
	protected void doRefreshVisuals(final Field model) {
		String attributeString = model.getVisibility().getVisibilityString()
				+ model.getName() + ":" + model.getType().getName();
		this.figure.setFeatureSummary(attributeString);
	}

	@Override
	protected IFigure createFigure() {
		this.figure = new FeatureFigure();
		return this.figure;
	}

	public IJavaElement getJavaElement() {
		return getModelElement().getJdtField();
	}

}
