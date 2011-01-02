package org.modelgoon.packages.editor;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

public class PackageViewDropHandler extends ViewerDropAdapter {

	protected PackageViewDropHandler(final Viewer viewer) {
		super(viewer);
	}

	@Override
	public boolean performDrop(final Object data) {
		System.out.println("PackageViewDropHandler.performDrop() : " + data);
		return false;
	}

	@Override
	public boolean validateDrop(final Object target, final int operation,
			final TransferData transferType) {
		System.out.println("PackageViewDropHandler.validateDrop() : "
				+ transferType.type);
		return true;
	}

}
