package org.modelgoon.classes.editparts;

import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.modelgoon.classes.figures.CompartmentFigure;
import org.modelgoon.core.ui.AbstractComponentEditPart;

public class ListEditPart extends
		AbstractComponentEditPart<ClassCompartmentModel> {

	@Override
	protected IFigure createFigure() {
		return new CompartmentFigure(0);
	}

	@Override
	public List getModelChildren() {
		return getModelElement().getContents();
	}

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
	protected void doRefreshVisuals(final ClassCompartmentModel model) {
		// TODO Auto-generated method stub

	}

}
