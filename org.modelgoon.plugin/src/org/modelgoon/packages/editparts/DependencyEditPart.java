package org.modelgoon.packages.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.core.ui.AbstractLinkEditPart;

public class DependencyEditPart extends AbstractLinkEditPart {

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
	protected void createEditPolicies() {
		super.createEditPolicies();
	}

}
