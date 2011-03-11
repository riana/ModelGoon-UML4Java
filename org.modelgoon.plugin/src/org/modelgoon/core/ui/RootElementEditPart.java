package org.modelgoon.core.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.draw2d.AutomaticRouter;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.core.CreateNoteCommand;
import org.modelgoon.core.ModelElement;
import org.modelgoon.core.Note;
import org.modelgoon.core.RootModelElement;

public abstract class RootElementEditPart<T extends RootModelElement> extends
		AbstractGraphicalEditPart implements Observer {

	T model;

	private AutomaticRouter router;

	private final Map<Class<?>, CreationCommand> creationCommands = new HashMap<Class<?>, CreationCommand>();

	public RootElementEditPart() {
		addCreationCommand(Note.class, new CreateNoteCommand(this));
	}

	protected void addCreationCommand(
			final Class<? extends ModelElement> elementClass,
			final CreationCommand creationCommand) {
		this.creationCommands.put(elementClass, creationCommand);
	}

	public CreationCommand getCreationCommand(final ModelElement newObject) {
		Class<?> newObjectType = newObject.getClass();
		CreationCommand creationCommand = this.creationCommands
				.get(newObjectType);
		if (creationCommand != null) {
			creationCommand.setNewElement(newObject);
		}
		return creationCommand;
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
	protected IFigure createFigure() {
		FreeformLayer freeformLayer = new FreeformLayer();
		freeformLayer.setLayoutManager(new FreeformLayout());
		freeformLayer.setBorder(new MarginBorder(5));
		return freeformLayer;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.NODE_ROLE, null);
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new RootElementEditPolicy(
				this));
	}

	@Override
	public void refreshVisuals() {
		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		cLayer.setConnectionRouter(getConnectionRouter());
	}

	public AutomaticRouter getConnectionRouter() {
		if (this.router == null) {
			this.router = new FanRouter();
			this.router.setNextRouter(new BendpointConnectionRouter());
		}
		return this.router;
	}

	@Override
	public final void activate() {
		super.activate();
		this.model.addObserver(this);
	}

	@Override
	public final void deactivate() {
		super.deactivate();
		this.model.deleteObserver(this);
	}

	@Override
	protected abstract List<?> getModelChildren();

	public void update(final Observable o, final Object arg) {
		refreshVisuals();
		refreshChildren();
		refreshSourceConnections();
		refreshTargetConnections();

	}
}
