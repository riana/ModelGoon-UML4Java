package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public abstract class JavaType extends PositionnedModelElement {

	IJavaProject project;

	private IType javaType;

	private Diagram diagram;

	private NodeParser nodeParser;

	List<JavaType> incomingConnections = new ArrayList<JavaType>();

	public JavaType() {
		super();
	}

	public final void setNodeParser(final NodeParser nodeParser) {
		this.nodeParser = nodeParser;
		nodeParser.setOwner(this);
		if (this.diagram != null) {
			nodeParser.setDiagram(this.diagram);
		}
	}

	public final void setDiagram(final Diagram diagram) {
		this.diagram = diagram;
		if (this.nodeParser != null) {
			this.nodeParser.setDiagram(this.diagram);
		}
	}

	public Diagram getDiagram() {
		return this.diagram;
	}

	public final void setJavaType(final IType javaType) {
		this.javaType = javaType;
		System.out.println("JavaType.setJavaType() : "
				+ javaType.getElementName());
		try {
			for (IField field : javaType.getFields()) {
				String source = field.getSource();
				System.out.println("Field : " + source);
				ASTParser parser = ASTParser.newParser(AST.JLS3);
				parser.setSource(source.toCharArray());
				parser.setProject(this.project);
				parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
				parser.setResolveBindings(true);
				ASTNode node = parser.createAST(null);

				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(final FieldDeclaration node) {
						Field f = new Field();
						f.update(node);
						System.out.println("======> Node " + f.toString());
						return super.visit(node);
					}
				});

			}

			// for (IMethod method : javaType.getMethods()) {
			// String source = method.getSource();
			// System.out.println("\tMethod : " + source);
			// }
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (javaType != null) {
			String qualifiedName = this.javaType.getFullyQualifiedName();
			qualifiedName = qualifiedName.replace("$", ".");
			setName(qualifiedName);
		}
	}

	public IType getJavaType() {
		return this.javaType;
	}

	public void consolidate() {
		String qualifiedName = getName();
		qualifiedName = qualifiedName.replace("$", ".");
		setName(qualifiedName);
		if ((this.nodeParser != null) && (this.javaType != null)) {
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(this.javaType.getCompilationUnit());
			parser.setResolveBindings(true);
			ASTNode root = parser.createAST(null);
			this.nodeParser.parsingStarted();
			root.accept(this.nodeParser);
			this.nodeParser.parsingStopped();
		}
		if (this.javaType != null) {
			refreshed(this.javaType);
		}
	}

	public void addIncomingConnection(final JavaType javaType) {
		this.incomingConnections.add(javaType);
	}

	public void removeIncomingConnection(final JavaType javaType) {
		this.incomingConnections.remove(javaType);
	}

	public abstract List<JavaType> getOutgoingConnections();

	protected abstract void refreshed(IType javaType);

	public final List<JavaType> getIncomingConnections() {
		return this.incomingConnections;
	}

}
