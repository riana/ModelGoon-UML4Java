package org.modelgoon.classes.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classes.model.AssociationKind;

public class AssociationConnection extends PolylineConnection {

	Label targetLabel;

	PolygonDecoration compositionDecoration;

	PolygonDecoration aggregationDecoration;

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

		this.compositionDecoration = new PolygonDecoration();
		decorationPointList = new PointList();
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-1, -1);
		decorationPointList.addPoint(-2, 0);
		decorationPointList.addPoint(-1, 1);
		this.compositionDecoration.setTemplate(decorationPointList);
		this.compositionDecoration.setBackgroundColor(ClassFigure.borderColor);

		this.aggregationDecoration = new PolygonDecoration();
		// decorationPointList = new PointList();
		// decorationPointList.addPoint(0, 0);
		// decorationPointList.addPoint(-1, -1);
		// decorationPointList.addPoint(-2, 0);
		// decorationPointList.addPoint(-1, 1);
		this.aggregationDecoration.setBackgroundColor(new Color(null, 255, 255,
				255));
		this.aggregationDecoration.setTemplate(decorationPointList);

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
		this.compositionDecoration.setVisible(containment);
	}

	public void setTargetArrowVisible(final boolean targetArrowVisible) {
		this.targetDecoration.setVisible(targetArrowVisible);
	}

	public void setKind(final AssociationKind associationKind) {
		switch (associationKind) {
		case Simple:
			setSourceDecoration(null);
			break;
		case Aggregation:
			setSourceDecoration(this.aggregationDecoration);
			break;
		case Composition:
			setSourceDecoration(this.compositionDecoration);
			break;

		default:
			break;
		}

	}

}
