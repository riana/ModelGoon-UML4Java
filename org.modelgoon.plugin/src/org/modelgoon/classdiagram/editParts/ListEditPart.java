package org.modelgoon.classdiagram.editParts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.modelgoon.classdiagram.figures.CompartmentFigure;

public class ListEditPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
		return new CompartmentFigure(0);
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
	}

	@Override
	public List getModelChildren() {
		return (List) getModel();
	}

}
