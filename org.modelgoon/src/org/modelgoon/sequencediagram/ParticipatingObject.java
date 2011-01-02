package org.modelgoon.sequencediagram;

public class ParticipatingObject {

	String name;

	String type;

	public ParticipatingObject(final String name, final String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

}
