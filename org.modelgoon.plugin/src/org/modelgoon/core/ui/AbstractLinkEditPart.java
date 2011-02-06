package org.modelgoon.core.ui;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;

public class AbstractLinkEditPart extends AbstractConnectionEditPart {

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

}
