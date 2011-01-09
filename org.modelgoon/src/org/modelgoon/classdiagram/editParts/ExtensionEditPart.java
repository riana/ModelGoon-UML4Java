package org.modelgoon.classdiagram.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.classdiagram.model.ExtensionRelationShip;

public class ExtensionEditPart extends AbstractConnectionEditPart implements
		PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		PolylineConnection polylineConnection = new PolylineConnection();

		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-2, -2);
		decorationPointList.addPoint(-2, 2);
		decoration.setTemplate(decorationPointList);
		decoration.setBackgroundColor(new Color(null, 255, 255, 255));
		polylineConnection.setTargetDecoration(decoration);

		polylineConnection.setForegroundColor(ClassFigure.borderColor);

		return polylineConnection;
	}

	@Override
	protected void refreshVisuals() {
		ExtensionRelationShip extensionRelationShip = (ExtensionRelationShip) getModel();
		PolylineConnection polylineConnection = (PolylineConnection) getFigure();
		if (extensionRelationShip.isRealization()) {
			polylineConnection.setLineStyle(SWT.LINE_DASH);
		}
	}

	public void propertyChange(final PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createEditPolicies() {

	}

}
