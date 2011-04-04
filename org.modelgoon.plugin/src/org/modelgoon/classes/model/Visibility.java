package org.modelgoon.classes.model;

public enum Visibility {

	DEFAULT("~"),

	PRIVATE("-"),

	PROTECTED("#"),

	PUBLIC("+");

	private final String visibilityString;

	private Visibility(final String visibilityString) {
		this.visibilityString = visibilityString;
	}

	public String getVisibilityString() {
		return this.visibilityString;
	}

}
