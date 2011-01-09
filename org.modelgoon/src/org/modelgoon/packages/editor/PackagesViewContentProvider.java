package org.modelgoon.packages.editor;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.modelgoon.packages.model.PackageAnalysis;

public class PackagesViewContentProvider implements IGraphEntityContentProvider {

	PackageAnalysis packageAnalysis;

	public Object[] getElements(final Object input) {
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

	public double getWeight(final Object connection) {
		return 0;
	}

	public void dispose() {
	}

	public void inputChanged(final Viewer viewer, final Object oldInput,
			final Object newInput) {
	}

	public Object[] getConnectedTo(final Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
