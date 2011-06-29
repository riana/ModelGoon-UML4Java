package org.modelgoon.jdt.editparts;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classes.figures.ClassFigure;
import org.modelgoon.core.ui.AbstractLinkEditPart;
import org.modelgoon.jdt.model.ExtensionRelationShip;

public class ExtensionEditPart extends
		AbstractLinkEditPart<ExtensionRelationShip> {

	PolylineConnection polylineConnection = new PolylineConnection();

	ConnectionEndpointLocator targetLabelLocator;

	Label targetLabel;

	@Override
	protected IFigure createFigure() {

		this.polylineConnection = new PolylineConnection();

		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-2, -2);
		decorationPointList.addPoint(-2, 2);
		decoration.setTemplate(decorationPointList);
		decoration.setBackgroundColor(new Color(null, 255, 255, 255));
		this.polylineConnection.setTargetDecoration(decoration);

		this.polylineConnection.setForegroundColor(ClassFigure.borderColor);

		return this.polylineConnection;
	}

	@Override
	protected void doRefreshVisuals(final ExtensionRelationShip model) {
		if (model.isRealization()) {
			this.polylineConnection.setLineStyle(SWT.LINE_DASH);
		}
	}

}
