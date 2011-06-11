package org.modelgoon.jdt.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

public class CreateSimpleAssociationWizard extends Wizard {

	SimpleAssociationWizardModel simpleAssociationWizardModel;

	public CreateSimpleAssociationWizard(
			final SimpleAssociationWizardModel simpleAssociationWizardModel) {
		super();
		this.simpleAssociationWizardModel = simpleAssociationWizardModel;
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(new SimpleAssociationWizardPage(
				this.simpleAssociationWizardModel));
		setWindowTitle("Create Unique Association");
		setDefaultPageImageDescriptor(WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_NEW_WIZ));

	}

	public SimpleAssociationWizardModel getModel() {
		return this.simpleAssociationWizardModel;
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}

}
