package org.modelgoon.jdt.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.modelgoon.jdt.model.Visibility;

public class MultipleAssociationWizardPage extends WizardPage {

	public MultipleAssociationWizardPage() {
		super("Create Multiple Association");
		setTitle("Multiple Association");
		setDescription("Creates a Multiple Association");
	}

	public void createControl(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);

		composite.setLayout(new GridLayout(2, false));
		Label endPointlabel = new Label(composite, SWT.NONE);
		endPointlabel.setText("Endpoint Name:");

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		endPointlabel.setLayoutData(gridData);

		final Text text = new Text(composite, SWT.NONE);
		text.setText("");
		text.addModifyListener(new ModifyListener() {

			public void modifyText(final ModifyEvent e) {

			}

		});

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		text.setLayoutData(gridData);

		Label visibilitylabel = new Label(composite, SWT.NONE);
		visibilitylabel.setText("Modifiers:");

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		visibilitylabel.setLayoutData(gridData);

		Composite modifierGroups = new Composite(composite, SWT.NONE);
		modifierGroups.setLayout(new GridLayout(4, true));

		SelectionAdapter visibilitySelectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Button button = (Button) e.getSource();
				if (button.getSelection()) {
					String text = button.getText();
					Visibility v = Visibility.valueOf(text.toUpperCase());
				}
			}
		};

		Button publicButton = new Button(modifierGroups, SWT.RADIO);
		publicButton.setText("public");
		publicButton.addSelectionListener(visibilitySelectionAdapter);
		Button defaultButton = new Button(modifierGroups, SWT.RADIO);
		defaultButton.setText("default");
		defaultButton.addSelectionListener(visibilitySelectionAdapter);
		Button privateButton = new Button(modifierGroups, SWT.RADIO);
		privateButton.setText("private");
		privateButton.addSelectionListener(visibilitySelectionAdapter);
		privateButton.setSelection(true);
		Button protectedButton = new Button(modifierGroups, SWT.RADIO);
		protectedButton.setText("protected");
		protectedButton.addSelectionListener(visibilitySelectionAdapter);

		Label type = new Label(composite, SWT.NONE);
		type.setText("Type:");

		Combo typeSelector = new Combo(composite, SWT.READ_ONLY);
		typeSelector.add("Simple");
		typeSelector.add("Ordered");
		typeSelector.add("Type Indexed");
		typeSelector.add("Attribute Indexed");
		typeSelector.select(0);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		typeSelector.setLayoutData(gridData);

		Label key = new Label(composite, SWT.NONE);
		key.setText("Key:");

		Combo keySelector = new Combo(composite, SWT.READ_ONLY);
		keySelector.add("attribute1");
		keySelector.add("attribute2");
		keySelector.select(0);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		keySelector.setLayoutData(gridData);

		Label implementation = new Label(composite, SWT.NONE);
		implementation.setText("Container implementation:");
		Combo implemenationCombo = new Combo(composite, SWT.READ_ONLY);
		implemenationCombo.add("java.util.ArrayList");
		implemenationCombo.add("java.util.Vector");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		implemenationCombo.setLayoutData(gridData);

		Label accessorsLabel = new Label(composite, SWT.NONE);
		accessorsLabel.setText("Do you want to add Getters/Setters?");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		accessorsLabel.setLayoutData(gridData);

		new Label(composite, SWT.NONE);
		final Button generateGetter = new Button(composite, SWT.CHECK);
		generateGetter.setText("Generate getter");
		generateGetter.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {

			}

		});
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		generateGetter.setLayoutData(gridData);

		new Label(composite, SWT.NONE);
		final Button generateSetter = new Button(composite, SWT.CHECK);
		generateSetter.setText("Generate setter");
		generateSetter.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {

			}

		});
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		generateSetter.setLayoutData(gridData);

		new Label(composite, SWT.NONE);
		final Button generateAdd = new Button(composite, SWT.CHECK);
		generateAdd.setText("Generate Add method");
		generateAdd.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {

			}

		});
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		generateAdd.setLayoutData(gridData);

		new Label(composite, SWT.NONE);
		final Button generateGetByKey = new Button(composite, SWT.CHECK);
		generateGetByKey.setText("Generate Get(key) method");
		generateAdd.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {

			}

		});
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		generateGetByKey.setLayoutData(gridData);

		setControl(composite);

	}

}
