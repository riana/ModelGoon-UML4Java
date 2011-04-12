package org.modelgoon.jdt.editparts;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.corext.util.CodeFormatterUtil;
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
			// astRootNode.recordModifications();

			AST ast = astRootNode.getAST();
			ASTRewrite astRewrite = ASTRewrite.create(ast);

			TypeDeclaration typeDecl = CreateInheritanceCommand.getNode(
					source.getName(), astRootNode);

			// Perform AST transformation
			if (associationRelationShip.getMultiplicity().equals(
					AssociationRelationShip.UNIQUE)) {
				addUniqueAssociation(astRewrite, astRootNode, typeDecl, target);
			} else if (associationRelationShip.getMultiplicity().equals(
					AssociationRelationShip.MULTIPLE)) {
				addMultipleAssociation(astRewrite, astRootNode, typeDecl,
						target);
			}
			if (!source.getPackageName().equals(target.getPackageName())
					&& (cu.getImport(target.getQualifiedName()) != null)) {
				ImportDeclaration importDeclaration = ast
						.newImportDeclaration();
				importDeclaration
						.setName(ast.newName(target.getQualifiedName()));
				// astRootNode.imports().add(importDeclaration);
				ListRewrite lrw = astRewrite.getListRewrite(astRootNode,
						CompilationUnit.IMPORTS_PROPERTY);
				lrw.insertLast(importDeclaration, null);
			}

			// computation of the text edits
			TextEdit edits = astRewrite.rewriteAST();

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

	private void addMultipleAssociation(final ASTRewrite astRewrite,
			final CompilationUnit compilationUnit,
			final TypeDeclaration typeDecl, final UMLClass target) {

	}

	private void addUniqueAssociation(final ASTRewrite astRewrite,
			final CompilationUnit compilationUnit,
			final TypeDeclaration typeDecl, final UMLClass target) {
		String endpointName = "its" + target.getName();
		StringBuilder builder = new StringBuilder();
		builder.append(target.getName());
		builder.append(" ");
		builder.append(endpointName);
		builder.append(";");

		final FieldDeclaration declaration = (FieldDeclaration) astRewrite
				.createStringPlaceholder(builder.toString(),
						ASTNode.FIELD_DECLARATION);

		builder = new StringBuilder();

		builder.append("public ");
		builder.append(target.getName());
		builder.append(" get");
		builder.append(endpointName.substring(0, 1).toUpperCase());
		builder.append(endpointName.substring(1));
		builder.append("(){\n");
		builder.append("return this.");
		builder.append(endpointName);
		builder.append(";\n}");

		String getterBody = CodeFormatterUtil.format(
				CodeFormatter.K_CLASS_BODY_DECLARATIONS, builder.toString(), 0,
				"\n", target.getJavaType().getJavaProject());

		builder = new StringBuilder();
		builder.append("public void set");
		builder.append(endpointName.substring(0, 1).toUpperCase());
		builder.append(endpointName.substring(1));
		builder.append("(");
		builder.append(target.getName());
		builder.append(" ");
		builder.append(endpointName);
		builder.append("){\n");
		builder.append("this.");
		builder.append(endpointName);
		builder.append(" = ");
		builder.append(endpointName);
		builder.append(";\n}");

		String setterBody = CodeFormatterUtil.format(
				CodeFormatter.K_CLASS_BODY_DECLARATIONS, builder.toString(), 0,
				"\n", target.getJavaType().getJavaProject());

		final MethodDeclaration getterDeclaration = (MethodDeclaration) astRewrite
				.createStringPlaceholder(getterBody, ASTNode.METHOD_DECLARATION);

		final MethodDeclaration setterDeclaration = (MethodDeclaration) astRewrite
				.createStringPlaceholder(setterBody, ASTNode.METHOD_DECLARATION);

		ListRewrite bodyDeclarations = astRewrite.getListRewrite(typeDecl,
				typeDecl.getBodyDeclarationsProperty());

		bodyDeclarations.insertAt(declaration, 0, null);
		bodyDeclarations.insertLast(getterDeclaration, null);
		bodyDeclarations.insertLast(setterDeclaration, null);

	}

}
