package org.modelgoon.packages.editor;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.modelgoon.packages.model.Package;


public class PackagesViewLabelProvider extends LabelProvider {

	final Image image = Display.getDefault().getSystemImage(SWT.ICON_WARNING);

	@Override
	public Image getImage(final Object element) {
		return this.image;
	}

	@Override
	public String getText(final Object element) {
		System.out.println("PackagesViewLabelProvider.getText() : " + element);
		if (element instanceof Package) {
			Package pkg = (Package) element;
			return pkg.getName();
		}
		return element.toString();
	}

}