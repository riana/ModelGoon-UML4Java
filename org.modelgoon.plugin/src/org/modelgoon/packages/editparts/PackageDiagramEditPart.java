package org.modelgoon.packages.editparts;

import java.util.ArrayList;
import java.util.List;

import org.modelgoon.core.ModelElement;
import org.modelgoon.core.ui.RootElementEditPart;
import org.modelgoon.packages.model.PackageDiagram;

public class PackageDiagramEditPart extends RootElementEditPart<PackageDiagram> {

	List<ModelElement> children = new ArrayList<ModelElement>();

	public PackageDiagramEditPart() {

	}

	@Override
	protected List<?> getModelChildren() {
		this.children.clear();
		PackageDiagram packageDiagram = getModelElement();
		this.children.addAll(packageDiagram.getPackages());
		this.children.addAll(packageDiagram.getNotes());
		return this.children;
	}

}
