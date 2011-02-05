package org.modelgoon.classdiagram.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class ExtensionConnection extends PolylineConnection {

	public ExtensionConnection(final ClassFigure parent,
			final ClassFigure child, final boolean implementation) {

		ChopboxAnchor sourceAnchor = new ChopboxAnchor(child);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(parent);
		setSourceAnchor(sourceAnchor);
		setTargetAnchor(targetAnchor);
		setForegroundColor(ClassFigure.borderColor);

		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0, 0);
		decorationPointList.addPoint(-2, -2);
		decorationPointList.addPoint(-2, 2);
		decoration.setTemplate(decorationPointList);
		setTargetDecoration(decoration);
		decoration.setBackgroundColor(new Color(null, 255, 255, 255));
		if (implementation) {
			setLineStyle(SWT.LINE_DASH);
			decoration.setLineStyle(SWT.LINE_DASH);
		}
	}

}
