package org.modelgoon.jdt.editparts;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.modelgoon.core.ui.LinkCreationCommand;
import org.modelgoon.jdt.model.UMLClass;

public class CreateInheritanceCommand extends LinkCreationCommand {

	@Override
	public void execute() {
		UMLClass source = (UMLClass) getSource();
		UMLClass target = (UMLClass) getTarget();

		try {

			ASTParser parser = ASTParser.newParser(AST.JLS3);

			parser.setSource(source.getJavaType().getCompilationUnit());
			parser.setResolveBindings(false);
			CompilationUnit astRootNode = (CompilationUnit) parser
					.createAST(null);
			AST ast = astRootNode.getAST();

			ASTRewrite astRewrite = ASTRewrite.create(ast);

			TypeDeclaration typeDecl = (TypeDeclaration) astRootNode.types()
					.get(0);

			SimpleType parentType = ast.newSimpleType(ast.newName(target
					.getName()));

			if (target.isInterface()) {
				ListRewrite superInterfacesRewrite = astRewrite.getListRewrite(
						typeDecl,
						TypeDeclaration.SUPER_INTERFACE_TYPES_PROPERTY);
				superInterfacesRewrite.insertLast(parentType, null);
			} else {

				astRewrite.set(typeDecl,
						TypeDeclaration.SUPERCLASS_TYPE_PROPERTY, parentType,
						null);
			}

			if (!source.getPackageName().equals(target.getPackageName())) {
				ImportDeclaration importDeclaration = ast
						.newImportDeclaration();
				importDeclaration
						.setName(ast.newName(target.getQualifiedName()));

				ListRewrite lrw = astRewrite.getListRewrite(astRootNode,
						CompilationUnit.IMPORTS_PROPERTY);
				lrw.insertLast(importDeclaration, null);

			}
			TextEdit edits = astRewrite.rewriteAST();

			Document document = new Document(source.getJavaType()
					.getCompilationUnit().getSource());
			edits.apply(document);
			source.getJavaType().getCompilationUnit().getBuffer()
					.setContents(document.get());

			source.consolidate();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private AbstractTypeDeclaration getNode(final IType type) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);

		parser.setSource(type.getCompilationUnit());
		parser.setResolveBindings(false);
		CompilationUnit astRootNode = (CompilationUnit) parser.createAST(null);
		for (Object declaredType : astRootNode.types()) {
			AbstractTypeDeclaration typeDeclaration = (AbstractTypeDeclaration) declaredType;
			if (typeDeclaration.getName().equals(type.getElementName())) {
				return typeDeclaration;
			}
		}
		return null;
	}
}
