package org.modelgoon.core.ui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.modelgoon.core.AbstractConnection;

public abstract class AbstractLinkEditPart<T extends AbstractConnection>
		extends AbstractConnectionEditPart implements Observer {

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
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
				new BendpointEditPolicyImpl(this));
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
				new SelectionEditPolicy() {

					@Override
					protected void showSelection() {
						System.out.println("Node selected : " + getModel());
					}

					@Override
					protected void hideSelection() {
						// TODO Auto-generated method stub

					}
				});

	}

	public void addBendpoint(final Bendpoint bendpoint, final int index) {
		System.out.println("AbstractLinkEditPart.addBendpoint()");
	}

	public Bendpoint removeBendpoint(final int index) {
		return null;
	}

	public void removeBendpoint(final AbsoluteBendpoint bendpoint) {
		// TODO Auto-generated method stub

	}

	public AbsoluteBendpoint getBendpoint(final int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public void replaceBendpoint(final int index,
			final AbsoluteBendpoint bendpoint) {
		// TODO Auto-generated method stub

	}

	@Override
	protected final void refreshVisuals() {
		doRefreshVisuals(this.model);
		refreshChildren();
		refreshSourceConnections();
		refreshTargetConnections();
	}

	protected abstract void doRefreshVisuals(T model);

	public void update(final Observable o, final Object arg) {
		refreshVisuals();
	}

}
