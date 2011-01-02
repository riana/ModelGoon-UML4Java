package org.modelgoon.classdiagram.editParts;

import org.eclipse.draw2d.IFigure;
import org.modelgoon.classdiagram.figures.CommunicationClassFigure;
import org.modelgoon.classdiagram.model.ClassModel;

public class CommunicationClassEditPart extends ClassModelEditPart {

	CommunicationClassFigure figure;

	@Override
	protected IFigure createFigure() {
		this.figure = new CommunicationClassFigure();
		return this.figure;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		ClassModel model = (ClassModel) getModel();
		this.figure.setName(model.getName().substring(
				model.getName().lastIndexOf(".") + 1));
		refreshParentLayout();
	}

}
