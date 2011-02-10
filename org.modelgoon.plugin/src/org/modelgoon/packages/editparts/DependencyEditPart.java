package org.modelgoon.packages.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.modelgoon.classdiagram.figures.ClassFigure;
import org.modelgoon.core.ui.AbstractLinkEditPart;
import org.modelgoon.packages.model.DependencyLink;

public class DependencyEditPart extends AbstractLinkEditPart<DependencyLink> {

	PolylineConnection polylineConnection = new PolylineConnection();

	Label targetLabel;

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

		ConnectionEndpointLocator targetLabelLocator = new ConnectionEndpointLocator(
				this.polylineConnection, true);
		targetLabelLocator.setVDistance(10);
		targetLabelLocator.setUDistance(10);
		this.targetLabel = new Label();
		FontData fd = new FontData("Arial", 8, SWT.ITALIC);
		this.targetLabel.setFont(new Font(null, fd));
		this.targetLabel.setForegroundColor(new Color(null, 0, 0, 0));
		this.polylineConnection.add(this.targetLabel, targetLabelLocator);
		this.targetLabel.setVisible(false);
		return this.polylineConnection;
	}

	@Override
	protected void doRefreshVisuals(final DependencyLink model) {
		if (model.isCyclic()) {
			this.polylineConnection.setForegroundColor(ColorConstants.red);
		} else {
			this.polylineConnection.setForegroundColor(ClassFigure.borderColor);
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (String message : model.getImportedClasses()) {
			stringBuffer.append(message);
			stringBuffer.append("\n");
		}
		stringBuffer.append("\n");
		this.targetLabel.setText(stringBuffer.toString());
	}

	@Override
	protected void handlerLinkSelection(final boolean linkSelected) {
		// TODO Auto-generated method stub
		super.handlerLinkSelection(linkSelected);
		this.targetLabel.setVisible(linkSelected);
	}

}
