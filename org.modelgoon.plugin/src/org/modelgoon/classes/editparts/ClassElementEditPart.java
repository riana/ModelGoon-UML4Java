package org.modelgoon.classes.editparts;

import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.classes.model.ClassElement;
import org.modelgoon.core.ui.AbstractComponentEditPart;

public class ClassElementEditPart extends
		AbstractComponentEditPart<ClassElement> {

	ClassFigure classFigure;

	private ChopboxAnchor anchor;

	public ClassElementEditPart() {
		setDeleteCommand(new Command() {
			@Override
			public void execute() {
				// getModelElement().removeFromDiagram();
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		this.classFigure = new ClassFigure();
		this.anchor = new ChopboxAnchor(this.classFigure);
		return this.classFigure;
	}

	@Override
	protected void doRefreshVisuals(final ClassElement model) {
		String name = model.getQualifiedName();
		if (name.contains(".")) {
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		this.classFigure.setClassName(name);
		this.classFigure.setLocation(model.getLocation());
		this.classFigure.setAbstract(model.isAbstract());
		this.classFigure.setInterface(model.isInterface());
		this.classFigure.setInternal(model.isInternal());

		this.classFigure.setSize(this.classFigure.getPreferredSize());
	}

	@Override
	public List getModelSourceConnections() {
		return getModelElement().getOutgoingConnections();
	}

	@Override
	public List getModelTargetConnections() {
		return getModelElement().getIncomingConnections();
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
