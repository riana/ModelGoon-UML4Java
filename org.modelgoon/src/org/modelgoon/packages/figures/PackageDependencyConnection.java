package org.modelgoon.packages.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.swt.graphics.Color;

public class PackageDependencyConnection extends PolylineConnection {

	final static Color CYCLIC_DEPENDENCY_COLOR = new Color(null, 255, 0, 0);

	final static Color STANDARD_DEPENDENCY_COLOR = new Color(null, 255, 0, 0);

	final PolylineDecoration sourceArrow = new PolylineDecoration();

	public PackageDependencyConnection(PackageFigure source, PackageFigure target) {
		super();
		ChopboxAnchor sourceAnchor = new ChopboxAnchor(source);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(target);
		setSourceAnchor(sourceAnchor);
		setTargetAnchor(targetAnchor);
		setTargetDecoration(new PolylineDecoration());
		boolean cyclic = false;
		if (cyclic) {
			setSourceDecoration(this.sourceArrow);
			setForegroundColor(CYCLIC_DEPENDENCY_COLOR);
		}

		ConnectionLocator relationshipLocator = new ConnectionLocator(this);
		Label relationshipLabel = new Label("uses");
		relationshipLabel.setOpaque(true);
		add(relationshipLabel, relationshipLocator);
	}

}
