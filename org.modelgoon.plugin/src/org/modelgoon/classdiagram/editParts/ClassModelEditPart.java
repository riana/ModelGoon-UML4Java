package org.modelgoon.classdiagram.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.modelgoon.classdiagram.model.ClassModel;
import org.modelgoon.classes.figures.ClassFigure;

public abstract class ClassModelEditPart extends JavaModelElementEditPart
		implements PropertyChangeListener {

	public ClassModelEditPart() {
		super();
	}

	@Override
	public void showSourceFeedback(final Request request) {
		super.showSourceFeedback(request);
	}

	@Override
	public IFigure getContentPane() {
		return ((ClassFigure) getFigure()).getFeaturesCompartment();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ClassEditPolicy());
	}

	@Override
	public void activate() {
		super.activate();
		((ClassModel) getModel()).addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		((ClassModel) getModel()).removePropertyChangeListener(this);
	}

	protected void refreshParentLayout() {
		ClassModel model = (ClassModel) getModel();
		Point location = model.getLocation();
		Rectangle bounds = new Rectangle(location, getFigure()
				.getPreferredSize());
		getFigure().setLocation(location);
		bounds.setLocation(location);
		AbstractGraphicalEditPart parent = (AbstractGraphicalEditPart) getParent();
		IFigure parentContentPane = (parent).getContentPane();
		LayoutManager parentLayoutManager = parentContentPane
				.getLayoutManager();
		parentLayoutManager.setConstraint(getFigure(), bounds);
		parentContentPane.revalidate();
	}

	@Override
	public List getModelSourceConnections() {
		return ((ClassModel) getModel()).getOutgoingRelationships();
	}

	@Override
	public List getModelTargetConnections() {
		return ((ClassModel) getModel()).getIncomingRelationships();
	}

	public void propertyChange(final PropertyChangeEvent arg0) {
		refreshVisuals();
		refreshChildren();
		refreshSourceConnections();
		refreshTargetConnections();
		refreshParentLayout();
	}

}