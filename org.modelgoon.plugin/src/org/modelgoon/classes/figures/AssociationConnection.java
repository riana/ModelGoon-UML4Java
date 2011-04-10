package org.modelgoon.classes.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Color;

public class AssociationConnection extends PolylineConnection {

	Label targetLabel;

	PolygonDecoration containmentDecoration;

	PolylineDecoration targetDecoration;

	Label targetMultiplicityLabel;

	public AssociationConnection() {

		setForegroundColor(ClassFigure.borderColor);

		this.targetDecoration = new PolylineDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(-1, -1);
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-1, 1);
		this.targetDecoration.setTemplate(decorationPointList);
		setTargetDecoration(this.targetDecoration);
		this.targetDecoration
				.setBackgroundColor(new Color(null, 255, 255, 255));

		this.containmentDecoration = new PolygonDecoration();
		decorationPointList = new PointList();
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-1, -1);
		decorationPointList.addPoint(-2, 0);
		decorationPointList.addPoint(-1, 1);
		this.containmentDecoration.setTemplate(decorationPointList);
		this.containmentDecoration.setBackgroundColor(ClassFigure.borderColor);
		setSourceDecoration(this.containmentDecoration);

		this.containmentDecoration.setVisible(false);

		ConnectionEndpointLocator targetEndpointLocator = new ConnectionEndpointLocator(
				this, true);
		targetEndpointLocator.setVDistance(5);
		targetEndpointLocator.setUDistance(3);
		this.targetMultiplicityLabel = new Label();
		this.targetMultiplicityLabel
				.setForegroundColor(new Color(null, 0, 0, 0));
		add(this.targetMultiplicityLabel, targetEndpointLocator);

		ConnectionEndpointLocator targetLabelLocator = new ConnectionEndpointLocator(
				this, true);
		targetLabelLocator.setVDistance(-5);
		targetLabelLocator.setUDistance(3);
		this.targetLabel = new Label();
		this.targetLabel.setForegroundColor(new Color(null, 0, 0, 0));
		add(this.targetLabel, targetLabelLocator);
	}

	public void setClasses(final ClassFigure source, final ClassFigure target) {
		ChopboxAnchor sourceAnchor = new ChopboxAnchor(source);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(target);
		setSourceAnchor(sourceAnchor);
		setTargetAnchor(targetAnchor);
	}

	public void setMultiplicity(final String multiplicity) {
		this.targetMultiplicityLabel.setText(multiplicity);
	}

	public void setDestinationName(final String destinationName) {
		this.targetLabel.setText(destinationName);
	}

	public void setContainment(final boolean containment) {
		this.containmentDecoration.setVisible(containment);
	}

	public void setTargetArrowVisible(final boolean targetArrowVisible) {
		this.targetDecoration.setVisible(targetArrowVisible);
	}

}
