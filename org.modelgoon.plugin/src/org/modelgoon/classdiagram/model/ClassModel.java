package org.modelgoon.classdiagram.model;

import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;

public abstract class ClassModel extends RootJavaModelElement<IType> {

	private ClassDiagram diagram;

	protected UmlClassBuilder umlClassBuilder;

	public ClassModel() {
		super();
		this.umlClassBuilder = new UmlClassBuilder(this);
	}

	public void setDiagram(final ClassDiagram diagram) {
		this.diagram = diagram;
		this.umlClassBuilder.setDiagram(diagram);
	}

	public void consolidate() {
		String qualifiedName = getName();
		qualifiedName = qualifiedName.replace("$", ".");
		setName(qualifiedName);
		if ((this.umlClassBuilder != null) && (getJavaElement() != null)) {
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(getJavaElement().getCompilationUnit());
			parser.setResolveBindings(true);
			ASTNode root = parser.createAST(null);
			root.accept(this.umlClassBuilder);
		}
		firePropertyChange(PositionnedModelElement.LOCATION);
	}

	@Override
	protected void updateJavaElement(final IType javaElement) {
		if (javaElement != null) {
			String qualifiedName = javaElement.getFullyQualifiedName();
			qualifiedName = qualifiedName.replace("$", ".");
			setName(qualifiedName);
		}
	}

	public boolean isEnum() {
		return this.umlClassBuilder.isEnum();
	}

	public boolean isInternal() {
		return this.umlClassBuilder.isInternal();
	}

	public boolean isSingleton() {
		return this.umlClassBuilder.isSingleton();
	}

	public boolean isInterface() {
		return this.umlClassBuilder.isInterface();
	}

	public boolean isAbstract() {
		return this.umlClassBuilder.isAbstract();
	}

	public void removeIncomingRelationship(final Relationship relationship) {
		this.umlClassBuilder.removeIncomingRelationship(relationship);
		firePropertyChange(ModelElement.NAME);
	}

	public void addIncomingRelationship(final Relationship relationship) {
		this.umlClassBuilder.addIncomingRelationship(relationship);
		firePropertyChange(ModelElement.NAME);
	}

	public abstract List<Relationship> getIncomingRelationships();

	public void removeOutgoingRelationship(final Relationship relationship) {
		this.umlClassBuilder.removOutgoingeRelationship(relationship);
		firePropertyChange(PositionnedModelElement.LOCATION);
	}

	public abstract List<Relationship> getOutgoingRelationships();

}