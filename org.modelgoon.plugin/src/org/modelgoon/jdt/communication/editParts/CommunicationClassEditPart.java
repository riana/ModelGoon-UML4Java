package org.modelgoon.jdt.communication.editParts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.modelgoon.core.ui.AbstractComponentEditPart;
import org.modelgoon.jdt.communication.figures.CommunicationClassFigure;
import org.modelgoon.jdt.model.UMLClass;

public class CommunicationClassEditPart extends
		AbstractComponentEditPart<UMLClass> {

	CommunicationClassFigure figure;

	private ChopboxAnchor anchor;

	public CommunicationClassEditPart() {
		super();
		setDeleteCommand(new Command() {
			@Override
			public void execute() {
				getModelElement().removeFromDiagram();
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		this.figure = new CommunicationClassFigure();
		this.anchor = new ChopboxAnchor(this.figure);
		return this.figure;
	}

	@Override
	protected void doRefreshVisuals(final UMLClass model) {
		this.figure.setName(model.getName());
		this.figure.setLocation(model.getLocation());
		this.figure.setSize(this.figure.getPreferredSize());
	}

	@Override
	public List getModelSourceConnections() {
		List outgoingConnexions = new ArrayList();
		outgoingConnexions.addAll(getModelElement()
				.getCommunicationRelationships());
		return outgoingConnexions;
	}

	@Override
	public List getModelTargetConnections() {
		List incomingConnexion = new ArrayList();
		incomingConnexion.addAll(getModelElement()
				.getIncomingCommunicationRelationships());
		return incomingConnexion;
	}

	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		// TODO Auto-generated method stub
		return this.anchor;
	}

}
