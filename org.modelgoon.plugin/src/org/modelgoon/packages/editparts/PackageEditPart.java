package org.modelgoon.packages.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.modelgoon.core.ui.AbstractComponentEditPart;
import org.modelgoon.packages.figures.PackageFigure;
import org.modelgoon.packages.model.PackageElement;

public class PackageEditPart extends AbstractComponentEditPart<PackageElement> {

	PackageFigure packageFigure;

	public PackageEditPart() {
		setDeleteCommand(new Command() {
			@Override
			public void execute() {
				System.out
						.println("PackageEditPart.PackageEditPart().new Command() {...}.execute()");
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		this.packageFigure = new PackageFigure();
		this.packageFigure.setLocation(new Point(200, 200));
		return this.packageFigure;
	}

	@Override
	protected void doRefreshVisuals() {
		PackageElement packageElement = getModelElement();
		this.packageFigure.setPackageName(packageElement.getQualifiedName());
	}

	@Override
	public void handleConstraintsChange(final Rectangle newConstraint) {
		this.packageFigure.setLocation(newConstraint.getLocation());
	}

}
