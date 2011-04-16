package org.modelgoon.jdt.editparts;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.window.Window;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.core.ui.LinkCreationCommand;
import org.modelgoon.jdt.model.AssociationRelationShip;
import org.modelgoon.jdt.model.UMLClass;
import org.modelgoon.jdt.model.Visibility;
import org.modelgoon.jdt.wizards.CreateMultipleAssociationWizard;
import org.modelgoon.jdt.wizards.CreateSimpleAssociationWizard;
import org.modelgoon.jdt.wizards.SimpleAssociationWizardModel;

public class CreateAssociationCommand extends LinkCreationCommand {

	CodeFormatter formatter;

	public CreateAssociationCommand() {
		this.formatter = ToolFactory.createCodeFormatter(JavaCore.getOptions());
	}

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
				ListRewrite lrw = astRewrite.getListRewrite(astRootNode,
						CompilationUnit.IMPORTS_PROPERTY);
				lrw.insertLast(importDeclaration, null);
			}

			// computation of the text edits
			TextEdit edits = astRewrite.rewriteAST();

			// computation of the new source code
			edits.apply(document);
			String newSource = document.get();

			// formatting the new source code
			edits = this.formatter.format(CodeFormatter.K_COMPILATION_UNIT,
					newSource, 0, newSource.length(), 0,
					System.getProperty("line.separator"));
			edits.apply(document);
			newSource = document.get();

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
		CreateMultipleAssociationWizard wizard = new CreateMultipleAssociationWizard();
		int result = ModelGoonActivator.openWizardDialog(wizard);

		if (result == Window.OK) {

		}
	}

	private void addUniqueAssociation(final ASTRewrite astRewrite,
			final CompilationUnit compilationUnit,
			final TypeDeclaration typeDecl, final UMLClass target) {

		SimpleAssociationWizardModel wizardModel = new SimpleAssociationWizardModel();
		wizardModel.setName("its" + target.getName());
		CreateSimpleAssociationWizard wizard = new CreateSimpleAssociationWizard(
				wizardModel);

		int result = ModelGoonActivator.openWizardDialog(wizard);

		if (result == Window.OK) {
			String endpointName = wizardModel.getName();

			ListRewrite bodyDeclarations = astRewrite.getListRewrite(typeDecl,
					typeDecl.getBodyDeclarationsProperty());

			String fieldDeclarationString = buildFieldDeclaration(
					target.getName(), endpointName, wizardModel.getVisibility());

			final FieldDeclaration declaration = (FieldDeclaration) astRewrite
					.createStringPlaceholder(fieldDeclarationString,
							ASTNode.FIELD_DECLARATION);
			bodyDeclarations.insertAt(declaration, 0, null);

			if (wizardModel.isGetterGenerationRequired()) {
				String getterMethod = buildGetter(target, endpointName);
				final MethodDeclaration getterDeclaration = (MethodDeclaration) astRewrite
						.createStringPlaceholder(getterMethod,
								ASTNode.METHOD_DECLARATION);
				bodyDeclarations.insertLast(getterDeclaration, null);
			}

			if (wizardModel.isSetterGenerationRequired()) {
				String setterMethod = buildSetter(target, endpointName);
				final MethodDeclaration setterDeclaration = (MethodDeclaration) astRewrite
						.createStringPlaceholder(setterMethod,
								ASTNode.METHOD_DECLARATION);
				bodyDeclarations.insertLast(setterDeclaration, null);
			}

		}

	}

	public String buildFieldDeclaration(final String type,
			final String variable, final Visibility visibility) {
		StringBuilder builder = new StringBuilder();
		if (visibility != Visibility.DEFAULT) {
			builder.append(visibility.toString().toLowerCase());
			builder.append(" ");
		}
		builder.append(type);
		builder.append(" ");
		builder.append(variable);
		builder.append(";");
		return builder.toString();
	}

	public String buildGetter(final UMLClass target, final String variableName) {

		String setterTemplate = "public $type $methodName(){\nreturn this.$fieldName;\n}";

		String methodName = "get" + variableName.substring(0, 1).toUpperCase()
				+ variableName.substring(1);

		setterTemplate = setterTemplate.replaceAll("\\$type", target.getName());
		setterTemplate = setterTemplate
				.replaceAll("\\$fieldName", variableName);
		setterTemplate = setterTemplate.replaceAll("\\$methodName", methodName);

		return setterTemplate;
	}

	public String buildSetter(final UMLClass target, final String variableName) {

		String getterTemplate = "public void $methodName($type $fieldName){\nthis.$fieldName = $fieldName;\n}";

		String methodName = "set" + variableName.substring(0, 1).toUpperCase()
				+ variableName.substring(1);

		getterTemplate = getterTemplate.replaceAll("\\$type", target.getName());
		getterTemplate = getterTemplate
				.replaceAll("\\$fieldName", variableName);
		getterTemplate = getterTemplate.replaceAll("\\$methodName", methodName);

		return getterTemplate;
	}
}
