package org.modelgoon.classes.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.modelgoon.classes.figures.FeatureFigure;
import org.modelgoon.classes.model.Attribute;
import org.modelgoon.core.ui.AbstractComponentEditPart;

public class AttributeEditPart extends AbstractComponentEditPart<Attribute> {

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
	protected void doRefreshVisuals(final Attribute model) {
		String attributeString = model.getVisibility().getVisibilityString()
				+ model.getName() + ":" + model.getType();
		this.figure.setFeatureSummary(attributeString);
	}

	@Override
	protected IFigure createFigure() {
		this.figure = new FeatureFigure();
		return this.figure;
	}

}
