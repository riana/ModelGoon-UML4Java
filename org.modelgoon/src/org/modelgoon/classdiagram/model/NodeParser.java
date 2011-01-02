package org.modelgoon.classdiagram.model;

import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class NodeParser extends ASTVisitor {

	Diagram diagram;

	JavaType owner;

	public abstract void parsingStarted();

	public abstract void parsingStopped();

	public void setOwner(final JavaType owner) {
		this.owner = owner;
	}

	public JavaType getOwner() {
		return this.owner;
	}

	public boolean isOwnerValid(final String qualifiedName) {
		return qualifiedName.equals(this.owner.getName());
	}

	public void setDiagram(final Diagram diagram) {
		this.diagram = diagram;
	}

	public Diagram getDiagram() {
		return this.diagram;
	}

}
