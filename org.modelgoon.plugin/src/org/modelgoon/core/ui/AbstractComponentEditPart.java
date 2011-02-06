package org.modelgoon.core.ui;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.tools.DragEditPartsTracker;

public abstract class AbstractComponentEditPart<T> extends
		AbstractGraphicalEditPart implements NodeEditPart {

	T model;

	Command deleteCommand;

	public AbstractComponentEditPart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		return new DragEditPartsTracker(this);
	}

	public final void setDeleteCommand(final Command deleteCommand) {
		this.deleteCommand = deleteCommand;
	}

	public Command getDeleteCommand() {
		return this.deleteCommand;
	}

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		this.model = (T) model;
	}

	public T getModelElement() {
		return this.model;
	}

	@Override
	protected final void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new AbstractComponentEditPolicy());
		installEditPolicy(EditPolicy.NODE_ROLE,
				new GraphicalNodeEditPolicyImpl(this));
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
				new SelectionEditPolicy() {

					@Override
					protected void showSelection() {
						System.out.println("EditPart selected : " + getModel());
					}

					@Override
					protected void hideSelection() {
						// TODO Auto-generated method stub

					}
				});

		doCreateEditPolicies();
	}

	protected void doCreateEditPolicies() {

	}

	@Override
	protected void refreshVisuals() {
		doRefreshVisuals(this.model);
		refreshChildren();
		refreshSourceConnections();
		refreshTargetConnections();
		refreshParentLayout();
	}

	private void refreshParentLayout() {
		AbstractGraphicalEditPart parent = (AbstractGraphicalEditPart) getParent();
		IFigure parentContentPane = (parent).getContentPane();
		LayoutManager parentLayoutManager = parentContentPane
				.getLayoutManager();
		parentLayoutManager.setConstraint(getFigure(), getFigure().getBounds());
		parentContentPane.revalidate();
	}

	protected abstract void doRefreshVisuals(T model);

	public Rectangle getCurrentConstraints() {
		return getFigure().getBounds();
	}

	// public void modelChanged() {
	// refreshVisuals();
	// refreshChildren();
	// refreshSourceConnections();
	// refreshTargetConnections();
	// refreshParentLayout();
	// }

	public abstract void handleConstraintsChange(final Rectangle newConstraint);
}
