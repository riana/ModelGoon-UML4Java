package org.modelgoon.packages.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.core.ui.AbstractLinkEditPart;
import org.modelgoon.packages.model.DependencyLink;

public class DependencyEditPart extends AbstractLinkEditPart<DependencyLink> {

	PolylineConnection polylineConnection = new PolylineConnection();

	@Override
	protected IFigure createFigure() {

		this.polylineConnection = new PolylineConnection();

		PolylineDecoration decoration = new PolylineDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(-1, 1);
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-1, -1);

		decoration.setTemplate(decorationPointList);
		decoration.setBackgroundColor(new Color(null, 255, 255, 255));
		this.polylineConnection.setTargetDecoration(decoration);

		this.polylineConnection.setForegroundColor(ClassFigure.borderColor);

		return this.polylineConnection;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
	}

	@Override
	protected void doRefreshVisuals(final DependencyLink model) {
		if (model.isCyclic()) {
			this.polylineConnection.setForegroundColor(ColorConstants.red);
		} else {
			this.polylineConnection.setForegroundColor(ClassFigure.borderColor);
		}
	}

}
