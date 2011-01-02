package org.modelgoon.classdiagram.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.classdiagram.model.CommunicationRelationship;

public class CommunicationEditPart extends AbstractConnectionEditPart implements
		PropertyChangeListener {

	Label targetLabel;

	@Override
	protected IFigure createFigure() {
		PolylineConnection polylineConnection = new PolylineConnection();

		PolylineDecoration decoration = new PolylineDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-2, -2);
		decorationPointList.addPoint(-2, 2);
		decoration.setPoints(decorationPointList);
		decoration.setBackgroundColor(new Color(null, 255, 255, 255));
		polylineConnection.setTargetDecoration(decoration);

		polylineConnection.setForegroundColor(ClassFigure.borderColor);

		// ConnectionLocator targetLabelLocator = new ConnectionLocator(
		// polylineConnection);
		ConnectionEndpointLocator targetLabelLocator = new ConnectionEndpointLocator(
				polylineConnection, true);
		targetLabelLocator.setVDistance(10);
		targetLabelLocator.setUDistance(10);
		this.targetLabel = new Label();
		FontData fd = new FontData("Arial", 8, SWT.ITALIC);
		this.targetLabel.setFont(new Font(null, fd));
		this.targetLabel.setForegroundColor(new Color(null, 0, 0, 0));
		polylineConnection.add(this.targetLabel, targetLabelLocator);

		return polylineConnection;
	}

	@Override
	protected void refreshVisuals() {
		CommunicationRelationship comm = (CommunicationRelationship) getModel();
		StringBuffer stringBuffer = new StringBuffer();
		for (String message : comm.getMessages()) {
			stringBuffer.append(message);
			stringBuffer.append("()\n");
		}
		stringBuffer.append("\n");
		this.targetLabel.setText(stringBuffer.toString());
	}

	@Override
	public void propertyChange(final PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createEditPolicies() {

	}

}
