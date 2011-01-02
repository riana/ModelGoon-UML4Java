package org.modelgoon.classdiagram.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.ClassModel;

public class ClassDiagramEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		FreeformLayer freeformLayer = new FreeformLayer();
		freeformLayer.setLayoutManager(new FreeformLayout());
		return freeformLayer;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ClassDiagramEditPolicies(
				this));
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

	@Override
	protected List<ClassModel> getModelChildren() {
		ClassDiagram classDiagram = (ClassDiagram) getModel();
		return classDiagram.getClasses();
	}

	@Override
	public void activate() {
		super.activate();
		((ClassDiagram) getModel()).addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		((ClassDiagram) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public void propertyChange(final PropertyChangeEvent arg0) {
		refreshChildren();
	}

}
