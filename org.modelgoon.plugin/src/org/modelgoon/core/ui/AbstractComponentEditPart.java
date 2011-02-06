package org.modelgoon.core.ui;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class AbstractComponentEditPart<T> extends
		AbstractGraphicalEditPart {

	T model;

	Command deleteCommand;

	public AbstractComponentEditPart() {
		// TODO Auto-generated constructor stub
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
		doCreateEditPolicies();
	}

	protected void doCreateEditPolicies() {

	}

	@Override
	protected void refreshVisuals() {
		doRefreshVisuals();
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

	protected abstract void doRefreshVisuals();

	public Rectangle getCurrentConstraints() {
		return getFigure().getBounds();
	}

	public abstract void handleConstraintsChange(final Rectangle newConstraint);
}
