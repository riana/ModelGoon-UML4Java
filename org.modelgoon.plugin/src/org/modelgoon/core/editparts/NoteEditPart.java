package org.modelgoon.core.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.modelgoon.core.Note;
import org.modelgoon.core.NoteFigure;
import org.modelgoon.core.ui.AbstractComponentEditPart;

public class NoteEditPart extends AbstractComponentEditPart<Note> {

	NoteFigure noteFigure;

	@Override
	protected void doCreateEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new NoteDirectEditPolicy());

		setDeleteCommand(new Command() {

			@Override
			public void execute() {
				getModelElement().removeFromDiagram();
			}

		});
	}

	private void performDirectEdit() {
		new LogicLabelEditManager(this, new LabelCellEditorLocator(
				this.noteFigure)).show();
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			performDirectEdit();
		}
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
	protected void doRefreshVisuals(final Note model) {
		this.noteFigure.setLocation(model.getLocation());
		this.noteFigure.setContent(model.getContent());
		this.noteFigure.setSize(this.noteFigure.getPreferredSize());
	}

	@Override
	protected IFigure createFigure() {
		this.noteFigure = new NoteFigure();
		return this.noteFigure;
	}

}
