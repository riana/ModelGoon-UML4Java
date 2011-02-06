package org.modelgoon.core.ui;

import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public abstract class RootElementEditPart<T> extends AbstractGraphicalEditPart {

	T model;

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		this.model = (T) model;
	}

	public T getModelElement() {
		return this.model;
	}

	@Override
	protected IFigure createFigure() {
		FreeformLayer freeformLayer = new FreeformLayer();
		freeformLayer.setLayoutManager(new FreeformLayout());
		freeformLayer.setBorder(new LineBorder());
		return freeformLayer;
	}

	@Override
	protected void createEditPolicies() {
		// installEditPolicy(EditPolicy.LAYOUT_ROLE, new
		// ClassDiagramEditPolicies(
		// this));
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new RootElementEditPolicy(
				this));
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

	@Override
	protected abstract List<?> getModelChildren();

}
