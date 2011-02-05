package org.modelgoon.classdiagram.model;

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
