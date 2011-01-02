package org.modelgoon.packages.editor;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.modelgoon.packages.model.PackageAnalysis;


public class PackagesViewContentProvider implements IGraphEntityContentProvider {

	PackageAnalysis packageAnalysis;

	@Override
	public Object[] getElements(Object input) {
		if (input instanceof PackageAnalysis) {
			PackageAnalysis packageAnalysis = (PackageAnalysis) input;
			Object[] elements = packageAnalysis.getPackages().toArray();
			System.out.println("PackagesViewContentProvider.getElements() : "
					+ elements.length + " / "
					+ packageAnalysis.getPackages().size());
			return elements;
		}
		return null;
	}

	public double getWeight(Object connection) {
		return 0;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getConnectedTo(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
