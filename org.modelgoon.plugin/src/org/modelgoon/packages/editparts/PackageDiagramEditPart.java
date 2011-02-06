package org.modelgoon.packages.editparts;

import java.util.List;

import org.modelgoon.core.ui.RootElementEditPart;
import org.modelgoon.packages.model.PackageDiagram;

public class PackageDiagramEditPart extends RootElementEditPart<PackageDiagram> {

	@Override
	protected List<?> getModelChildren() {
		PackageDiagram packageDiagram = getModelElement();
		return packageDiagram.getPackages();
	}

}
