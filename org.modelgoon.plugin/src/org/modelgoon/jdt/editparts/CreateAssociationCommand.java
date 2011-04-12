package org.modelgoon.jdt.editparts;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.modelgoon.core.ui.LinkCreationCommand;
import org.modelgoon.jdt.model.AssociationRelationShip;
import org.modelgoon.jdt.model.UMLClass;

public class CreateAssociationCommand extends LinkCreationCommand {

	@Override
	public void execute() {
		UMLClass source = (UMLClass) getSource();
		UMLClass target = (UMLClass) getTarget();
		AssociationRelationShip associationRelationShip = (AssociationRelationShip) getNewObject();
		try {

			ICompilationUnit cu = source.getJavaType().getCompilationUnit()
					.getWorkingCopy(null);

			Document document = new Document(cu.getSource());

			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(source.getJavaType().getCompilationUnit());

			CompilationUnit astRootNode = (CompilationUnit) parser
					.createAST(null);
			astRootNode.recordModifications();

			AST ast = astRootNode.getAST();

			TypeDeclaration typeDecl = CreateInheritanceCommand.getNode(
					source.getName(), astRootNode);

			// Perform AST transformation
			if (associationRelationShip.getMultiplicity().equals(
					AssociationRelationShip.UNIQUE)) {
				addUniqueAssociation(astRootNode, typeDecl, target);
			} else if (associationRelationShip.getMultiplicity().equals(
					AssociationRelationShip.MULTIPLE)) {
				addMultipleAssociation(astRootNode, typeDecl, target);
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

	private void addMultipleAssociation(final CompilationUnit compilationUnit,
			final TypeDeclaration typeDecl, final UMLClass target) {
		AST ast = typeDecl.getAST();

		String endpointName = "its" + target.getName() + "s";
		String containerTypeName = "List";

		ImportDeclaration importDeclaration = ast.newImportDeclaration();
		importDeclaration.setName(ast.newName("java.util.List"));
		compilationUnit.imports().add(importDeclaration);

		VariableDeclarationFragment variable = typeDecl.getAST()
				.newVariableDeclarationFragment();
		variable.setName(ast.newSimpleName(endpointName));
		FieldDeclaration fieldDeclaration = typeDecl.getAST()
				.newFieldDeclaration(variable);
		SimpleType containerType = ast.newSimpleType(ast
				.newName(containerTypeName));
		ParameterizedType parameterizedType = ast
				.newParameterizedType(containerType);
		SimpleType associationType = ast.newSimpleType(ast.newName(target
				.getName()));
		parameterizedType.typeArguments().add(associationType);
		fieldDeclaration.setType(parameterizedType);

		typeDecl.bodyDeclarations().add(fieldDeclaration);
	}

	private void addUniqueAssociation(final CompilationUnit compilationUnit,
			final TypeDeclaration typeDecl, final UMLClass target) {
		String endpointName = "its" + target.getName();

		AST ast = typeDecl.getAST();
		VariableDeclarationFragment variable = typeDecl.getAST()
				.newVariableDeclarationFragment();
		variable.setName(ast.newSimpleName(endpointName));
		FieldDeclaration fieldDeclaration = typeDecl.getAST()
				.newFieldDeclaration(variable);
		SimpleType type = ast.newSimpleType(ast.newName(target.getName()));
		fieldDeclaration.setType(type);

		typeDecl.bodyDeclarations().add(fieldDeclaration);
	}

	// private void addGetter(final TypeDeclaration typeDecl,
	// final FieldDeclaration fieldDeclaration) {
	// AST ast = typeDecl.getAST();
	// MethodDeclaration getterMethod = ast.newMethodDeclaration();
	//
	// ASTRewrite astRewrite = ASTRewrite.create(ast);
	//
	// final MethodDeclaration declaration = (MethodDeclaration) rewrite
	// .getASTRewrite()
	// .createStringPlaceholder(
	// CodeFormatterUtil.format(
	// CodeFormatter.K_CLASS_BODY_DECLARATIONS,
	// contents, 0, delimiter, field.getJavaProject()),
	// ASTNode.METHOD_DECLARATION);
	// if (insertion != null) {
	// rewrite.insertBefore(declaration, insertion, null);
	// } else {
	// rewrite.insertLast(declaration, null);
	// }
	//
	// }

}
