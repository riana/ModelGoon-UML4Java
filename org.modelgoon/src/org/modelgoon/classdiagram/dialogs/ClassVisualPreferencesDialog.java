package org.modelgoon.classdiagram.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.classdiagram.model.ClassModel;
import org.modelgoon.classdiagram.model.MembersDisplayFilter;
import org.modelgoon.classdiagram.model.MethodDisplayFilter;
import org.modelgoon.classdiagram.model.UmlClass;

public class ClassVisualPreferencesDialog extends Dialog {

	ClassModel umlClass;

	MembersDisplayFilter attributeDisplayFilter;

	MethodDisplayFilter methodDisplayFilter;

	public ClassVisualPreferencesDialog(final UmlClass umlClass) {
		super(ModelGoonActivator.getDefault().getWorkbench()
				.getModalDialogShellProvider().getShell());
		this.umlClass = umlClass;
		this.attributeDisplayFilter = new MembersDisplayFilter();
		this.attributeDisplayFilter.setValue(umlClass
				.getAttributesDisplayFilter());
		this.methodDisplayFilter = new MethodDisplayFilter();
		this.methodDisplayFilter.setValue(umlClass.getMethodDisplayFilter());
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setText("Customize displayed members");
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, true));
		Group attributesGroup = new Group(composite, SWT.NONE);
		attributesGroup.setLayout(new GridLayout(1, true));
		attributesGroup.setText("Attributes");

		final Button attributesStaticCheck = new Button(attributesGroup,
				SWT.CHECK);
		attributesStaticCheck.setText("Static");
		attributesStaticCheck.setSelection(this.attributeDisplayFilter
				.isStaticAccepted());
		attributesStaticCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.attributeDisplayFilter
						.setStaticAccepted(attributesStaticCheck.getSelection());
			}
		});

		final Button attributesPublicCheck = new Button(attributesGroup,
				SWT.CHECK);
		attributesPublicCheck.setText("Public");
		attributesPublicCheck.setSelection(this.attributeDisplayFilter
				.isPublicAccepted());
		attributesPublicCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.attributeDisplayFilter
						.setPublicAccepted(attributesPublicCheck.getSelection());
			}
		});

		final Button attributesPrivateCheck = new Button(attributesGroup,
				SWT.CHECK);
		attributesPrivateCheck.setText("Private");
		attributesPrivateCheck.setSelection(this.attributeDisplayFilter
				.isPrivateAccepted());
		attributesPrivateCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.attributeDisplayFilter
						.setPrivateAccepted(attributesPrivateCheck
								.getSelection());
			}
		});

		final Button attributesProtectedCheck = new Button(attributesGroup,
				SWT.CHECK);
		attributesProtectedCheck.setText("Protected");
		attributesProtectedCheck.setSelection(this.attributeDisplayFilter
				.isProtectedAccepted());
		attributesProtectedCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.attributeDisplayFilter
						.setProtectedAccepted(attributesProtectedCheck
								.getSelection());
			}
		});

		final Button attributesDefaultCheck = new Button(attributesGroup,
				SWT.CHECK);
		attributesDefaultCheck.setText("Default");
		attributesDefaultCheck.setSelection(this.attributeDisplayFilter
				.isDefaultAccepted());
		attributesDefaultCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.attributeDisplayFilter
						.setDefaultAccepted(attributesDefaultCheck
								.getSelection());
			}
		});

		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.widthHint = 150;
		attributesGroup.setLayoutData(gridData);

		Group methodsGroup = new Group(composite, SWT.NONE);
		methodsGroup.setLayout(new GridLayout(1, true));
		methodsGroup.setText("Methods");

		final Button methodsStaticCheck = new Button(methodsGroup, SWT.CHECK);
		methodsStaticCheck.setText("Static");
		methodsStaticCheck.setSelection(this.methodDisplayFilter
				.isStaticAccepted());
		methodsStaticCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setStaticAccepted(methodsStaticCheck.getSelection());
			}
		});

		final Button methodsPublicCheck = new Button(methodsGroup, SWT.CHECK);
		methodsPublicCheck.setText("Public");
		methodsPublicCheck.setSelection(this.methodDisplayFilter
				.isPublicAccepted());
		methodsPublicCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setPublicAccepted(methodsPublicCheck.getSelection());
			}
		});

		final Button methodsPrivateCheck = new Button(methodsGroup, SWT.CHECK);
		methodsPrivateCheck.setText("Private");
		methodsPrivateCheck.setSelection(this.methodDisplayFilter
				.isPrivateAccepted());
		methodsPrivateCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setPrivateAccepted(methodsPrivateCheck.getSelection());
			}
		});

		final Button methodsProtectedCheck = new Button(methodsGroup, SWT.CHECK);
		methodsProtectedCheck.setText("Protected");
		methodsProtectedCheck.setSelection(this.methodDisplayFilter
				.isProtectedAccepted());
		methodsProtectedCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setProtectedAccepted(methodsProtectedCheck
								.getSelection());
			}
		});

		final Button methodsDefaultCheck = new Button(methodsGroup, SWT.CHECK);
		methodsDefaultCheck.setText("Default");
		methodsDefaultCheck.setSelection(this.methodDisplayFilter
				.isDefaultAccepted());
		methodsDefaultCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setDefaultAccepted(methodsDefaultCheck.getSelection());
			}
		});

		final Button constructorsStaticCheck = new Button(methodsGroup,
				SWT.CHECK);
		constructorsStaticCheck.setText("Constructors");
		constructorsStaticCheck.setSelection(this.methodDisplayFilter
				.isConstructorsAccepted());
		constructorsStaticCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setConstructorsAccepted(constructorsStaticCheck
								.getSelection());
			}
		});

		final Button accessorsStaticCheck = new Button(methodsGroup, SWT.CHECK);
		accessorsStaticCheck.setText("Accessors");
		accessorsStaticCheck.setSelection(this.methodDisplayFilter
				.isAccessorsAccepted());

		accessorsStaticCheck.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				ClassVisualPreferencesDialog.this.methodDisplayFilter
						.setAccessorsAccepted(accessorsStaticCheck
								.getSelection());
			}
		});

		gridData = new GridData();
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.widthHint = 150;
		methodsGroup.setLayoutData(gridData);

		return composite;
	}

	public MembersDisplayFilter getAttributeDisplayFilter() {
		return this.attributeDisplayFilter;
	}

	public MethodDisplayFilter getMethodDisplayFilter() {
		return this.methodDisplayFilter;
	}
}
