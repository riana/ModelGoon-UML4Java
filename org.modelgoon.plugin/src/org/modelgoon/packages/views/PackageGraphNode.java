package org.modelgoon.packages.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.packages.figures.PackageFigure;
import org.modelgoon.packages.model.Package;


public class PackageGraphNode extends GraphNode {

	Package pkg;

	PackageFigure packageFigure;

	public PackageGraphNode(final IContainer graphModel) {
		super(graphModel, SWT.NONE);
		setBackgroundColor(PackageFigure.packageColor);
		setImage(ModelGoonActivator.getImageDescriptor("icons/package_obj.gif")
				.createImage());
	}

	public void setPkg(final Package pkg) {
		this.pkg = pkg;
		refresh();
	}

	@Override
	public void setLocation(final double x, final double y) {
		super.setLocation(x, y);
		this.pkg.setLocation(getLocation().x, getLocation().y);
	}

	public void refresh() {
		setText(this.pkg.getName());
		Point location = this.pkg.getLocation();
		setLocation(location.x, location.y);
	}

	public Package getPackage() {
		return this.pkg;
	}
}
