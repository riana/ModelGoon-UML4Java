package org.modelgoon.jdt.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SimpleAssociationWizardPage extends WizardPage {

	SimpleAssociationWizardModel simpleAssociationWizardModel;

	public SimpleAssociationWizardPage(
			final SimpleAssociationWizardModel simpleAssociationWizardModel) {
		super("Create Simple Association");
		this.simpleAssociationWizardModel = simpleAssociationWizardModel;
	}

	public void createControl(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);

		composite.setLayout(new RowLayout());
		Label label = new Label(composite, SWT.NONE);
		label.setText("Endpoint Name");

		final Text text = new Text(composite, SWT.NONE);
		text.setText(this.simpleAssociationWizardModel.getName());
		text.addModifyListener(new ModifyListener() {

			public void modifyText(final ModifyEvent e) {
				SimpleAssociationWizardPage.this.simpleAssociationWizardModel
						.setName(text.getText());
			}
		});
		setControl(composite);
	}
}
