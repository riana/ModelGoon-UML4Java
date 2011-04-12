package org.modelgoon.jdt.editparts;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.modelgoon.core.ui.LinkCreationCommand;
import org.modelgoon.jdt.model.UMLClass;

public class CreateInheritanceCommand extends LinkCreationCommand {

	@Override
	public void execute() {
		try {
			UMLClass source = (UMLClass) getSource();
			UMLClass target = (UMLClass) getTarget();
			ICompilationUnit cu = source.getJavaType().getCompilationUnit()
					.getWorkingCopy(null);

			Document document = new Document(cu.getSource());

			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(source.getJavaType().getCompilationUnit());

			CompilationUnit astRootNode = (CompilationUnit) parser
					.createAST(null);
			astRootNode.recordModifications();

			AST ast = astRootNode.getAST();

			SimpleType parentType = ast.newSimpleType(ast.newName(target
					.getName()));

			TypeDeclaration typeDecl = CreateInheritanceCommand.getNode(
					source.getName(), astRootNode);
			if (target.isInterface()) {
				typeDecl.superInterfaceTypes().add(parentType);
			} else {
				typeDecl.setSuperclassType(parentType);
			}

			if (!source.getPackageName().equals(target.getPackageName())
					&& (cu.getImport(target.getQualifiedName()) != null)) {
				ImportDeclaration importDeclaration = ast
						.newImportDeclaration();
				importDeclaration
						.setName(ast.newName(target.getQualifiedName()));
				astRootNode.imports().add(importDeclaration);

			}

			// computation of the text edits
			TextEdit edits = astRootNode.rewrite(document, cu.getJavaProject()
					.getOptions(true));

			// computation of the new source code
			edits.apply(document);
			String newSource = document.get();

			// update of the compilation unit
			cu.getBuffer().setContents(newSource);

			cu.reconcile(ICompilationUnit.NO_AST, true, null, null);
			cu.commitWorkingCopy(true, null);

			source.consolidate();
			target.consolidate();

		} catch (JavaModelException e) {
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

	public static TypeDeclaration getNode(final String typeName,
			final CompilationUnit cu) {

		for (Object declaredType : cu.types()) {
			AbstractTypeDeclaration typeDeclaration = (AbstractTypeDeclaration) declaredType;
			if (typeDeclaration.getName().getFullyQualifiedName()
					.equals(typeName)) {
				if (typeDeclaration instanceof TypeDeclaration) {
					return (TypeDeclaration) typeDeclaration;
				}
			}
		}
		return null;
	}
}
