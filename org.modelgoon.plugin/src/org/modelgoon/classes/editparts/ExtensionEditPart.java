package org.modelgoon.classes.editparts;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.classes.model.Extension;
import org.modelgoon.core.ui.AbstractLinkEditPart;

public class ExtensionEditPart extends AbstractLinkEditPart<Extension> {

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
	protected void doRefreshVisuals(final Extension model) {
		if (model.isRealization()) {
			this.polylineConnection.setLineStyle(SWT.LINE_DASH);
		}
	}

}
