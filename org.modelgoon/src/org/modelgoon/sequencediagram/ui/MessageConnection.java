package org.modelgoon.sequencediagram.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.modelgoon.classdiagram.figures.ClassFigure;

public class MessageConnection extends PolylineConnection implements
		SequenceElement {

	LifelineFigure source;

	LifelineFigure destination;

	String message;

	int yPosition;

	Point sourceAnchorLocation = new Point();

	XYAnchor sourceAnchor = new XYAnchor(this.sourceAnchorLocation);

	Point destAnchorLocation = new Point();

	XYAnchor destAnchor = new XYAnchor(this.destAnchorLocation);

	private final Label targetLabel;

	int selfOffset = 20;

	ConnectionLocator targetLabelLocator;

	public MessageConnection() {

		setForegroundColor(ClassFigure.borderColor);

		PolylineDecoration targetDecoration = new PolylineDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(-1, -1);
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-1, 1);
		targetDecoration.setTemplate(decorationPointList);
		targetDecoration.setBackgroundColor(new Color(null, 255, 255, 255));
		setTargetDecoration(targetDecoration);

		setSourceAnchor(this.sourceAnchor);
		setTargetAnchor(this.destAnchor);

		this.targetLabelLocator = new ConnectionLocator(this);
		this.targetLabelLocator.setRelativePosition(PositionConstants.NORTH);
		this.targetLabelLocator.setGap(2);
		this.targetLabel = new Label();
		FontData fd = new FontData("Arial", 8, SWT.NORMAL);
		this.targetLabel.setFont(new Font(null, fd));
		this.targetLabel.setForegroundColor(new Color(null, 0, 0, 0));
		this.targetLabel.setText(this.message);
		add(this.targetLabel, this.targetLabelLocator);
	}

	public void setDashed(final boolean dashed) {
		if (dashed) {
			setLineStyle(SWT.LINE_DASH);
		} else {
			setLineStyle(SWT.LINE_SOLID);
		}
	}

	public MessageConnection(final String message, final LifelineFigure source,
			final LifelineFigure destination) {
		this();
		setMessage(message);
		setSource(source);
		setDestination(destination);
	}

	public void setSource(final LifelineFigure source) {
		this.source = source;
	}

	public void setDestination(final LifelineFigure destination) {
		this.destination = destination;
	}

	public void setMessage(final String message) {
		this.targetLabel.setText(message);
	}

	private void updatePosition() {
		if ((this.source != null) && (this.destination != null)) {
			if ((this.source == this.destination)
					&& ((this.sourceAnchorLocation.y != this.yPosition) || (this.source
							.getCenterXPosition() != this.sourceAnchorLocation.x))) {
				int xPosition = this.source.getCenterXPosition();
				List<Bendpoint> constraints = new ArrayList<Bendpoint>();
				constraints.add(new AbsoluteBendpoint(xPosition
						+ this.selfOffset, this.yPosition));
				constraints.add(new AbsoluteBendpoint(xPosition
						+ this.selfOffset, this.yPosition + this.selfOffset));

				BendpointConnectionRouter router = new BendpointConnectionRouter();
				router.setConstraint(this, constraints);

				setConnectionRouter(router);

				this.sourceAnchorLocation
						.setLocation(xPosition, this.yPosition);
				this.sourceAnchor.setLocation(this.sourceAnchorLocation);

				this.destAnchorLocation.setLocation(xPosition, this.yPosition
						+ this.selfOffset);
				this.destAnchor.setLocation(this.destAnchorLocation);
				setSourceAnchor(new XYAnchor(this.sourceAnchorLocation));
				setTargetAnchor(new XYAnchor(this.destAnchorLocation));

			} else {
				if ((this.sourceAnchorLocation.x != this.source
						.getCenterXPosition())
						|| (this.sourceAnchorLocation.y != this.yPosition)) {
					this.sourceAnchorLocation.setLocation(
							this.source.getCenterXPosition(), this.yPosition);
					this.sourceAnchor.setLocation(this.sourceAnchorLocation);
				}
				if ((this.destAnchorLocation.x != this.destination
						.getCenterXPosition())
						|| (this.destAnchorLocation.y != this.yPosition)) {
					this.destAnchorLocation.setLocation(
							this.destination.getCenterXPosition(),
							this.yPosition);
					this.destAnchor.setLocation(this.destAnchorLocation);
				}
			}
		}

	}

	@Override
	public int getVerticalExtent() {
		if (this.source == this.destination) {
			return this.selfOffset;
		}
		return 1;
	}

	@Override
	public void layout() {
		updatePosition();
		this.targetLabelLocator
				.setRelativePosition(this.source == this.destination ? PositionConstants.EAST
						: PositionConstants.NORTH);
		this.targetLabelLocator.relocate(this.targetLabel);

		super.layout();
	}

	@Override
	public void setYPosition(final int yPosition) {
		this.yPosition = yPosition;
		updatePosition();
	}

	@Override
	public int getMinX() {
		return this.sourceAnchorLocation.x < this.destAnchorLocation.x ? this.sourceAnchorLocation.x
				: this.destAnchorLocation.x;
	}

	@Override
	public int getMaxX() {
		if (this.source != this.destination) {
			return this.sourceAnchorLocation.x > this.destAnchorLocation.x ? this.sourceAnchorLocation.x
					: this.destAnchorLocation.x;
		} else {
			return this.targetLabel.getLocation().x
					+ this.targetLabel.getSize().width + this.selfOffset;
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
