package org.modelgoon.jdt.wizards;

import org.modelgoon.jdt.model.Visibility;

public class SimpleAssociationWizardModel {

	String name;

	Visibility visibility = Visibility.PRIVATE;

	boolean getterGenerationRequired;

	boolean setterGenerationRequired;

	public final String getName() {
		return this.name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final Visibility getVisibility() {
		return this.visibility;
	}

	public final void setVisibility(final Visibility visibility) {
		this.visibility = visibility;
	}

	public final boolean isGetterGenerationRequired() {
		return this.getterGenerationRequired;
	}

	public final void setGetterGenerationRequired(
			final boolean getterGenerationRequired) {
		this.getterGenerationRequired = getterGenerationRequired;
	}

	public final boolean isSetterGenerationRequired() {
		return this.setterGenerationRequired;
	}

	public final void setSetterGenerationRequired(
			final boolean setterGenerationRequired) {
		this.setterGenerationRequired = setterGenerationRequired;
	}

}
