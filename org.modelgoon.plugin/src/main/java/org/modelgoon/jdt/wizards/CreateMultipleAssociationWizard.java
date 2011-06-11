package org.modelgoon.jdt.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

public class CreateMultipleAssociationWizard extends Wizard {

	public CreateMultipleAssociationWizard() {
		super();
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(new MultipleAssociationWizardPage());
		setWindowTitle("Create Multiple Association");
		setDefaultPageImageDescriptor(WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_NEW_WIZ));

	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}

}
