package org.modelgoon.jdt.editor;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.classes.model.AssociationKind;
import org.modelgoon.jdt.model.AssociationRelationShip;

public class AssociationCustomizationDialog extends Dialog {

	AssociationRelationShip associationRelationShip;

	AssociationKind selectedKind;

	public AssociationCustomizationDialog(
			final AssociationRelationShip associationRelationShip) {
		super(ModelGoonActivator.getDefault().getWorkbench()
				.getModalDialogShellProvider().getShell());
		this.associationRelationShip = associationRelationShip;
		this.selectedKind = associationRelationShip.getAssociationKind();
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setText("Customize Association");
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, true));
		Label label = new Label(composite, SWT.NONE);
		label.setText("Type : ");
		final Combo combo = new Combo(composite, SWT.READ_ONLY);
		for (AssociationKind kind : AssociationKind.values()) {
			combo.add(kind.toString());
		}

		combo.select(combo.indexOf(this.associationRelationShip
				.getAssociationKind().toString()));

		combo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(final SelectionEvent e) {
				AssociationCustomizationDialog.this.selectedKind = AssociationKind
						.values()[combo.getSelectionIndex()];

			}

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return composite;
	}

	public AssociationKind getSelectedKind() {
		return this.selectedKind;
	}
}
