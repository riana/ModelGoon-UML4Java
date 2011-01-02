package org.modelgoon.sequencediagram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.modelgoon.sequencediagram.model.BlockType;
import org.modelgoon.sequencediagram.model.ColloboratingObject;
import org.modelgoon.sequencediagram.model.CombinedStatementBlock;
import org.modelgoon.sequencediagram.model.InteractionModel;
import org.modelgoon.sequencediagram.model.MessageExchange;
import org.modelgoon.sequencediagram.model.StatementBlock;

public class InteractionModelBuilder {

	// public void addInvocation(final ParticipatingObject source,
	// final ParticipatingObject destination, final Message message) {
	// this.invocations.add(new MessageExchange(source, destination, message));
	// }
	//
	// public ParticipatingObject getParticipatingObject(final String name) {
	// return this.participants.get(name);
	// }
	//
	// public void addParticipationObject(final ParticipatingObject object) {
	// this.participants.put(object.getName(), object);
	// }

	// public String getDeclarativeSequence() {
	// StringBuffer sequenceDeclaration = new StringBuffer();
	// for (ParticipatingObject object : this.participants.values()) {
	// sequenceDeclaration.append("Participant : " + object.getName()
	// + ":" + object.getType() + "\n");
	// }
	//
	// for (MessageExchange invocation : this.invocations) {
	// sequenceDeclaration.append("message : from "
	// + invocation.getSource().getName() + " to "
	// + invocation.getDestination().getName() + " => \""
	// + invocation.getMessage().getName() + "\"\n");
	// }
	//
	// return sequenceDeclaration.toString();
	// }

	final Stack<StatementBlock> blockStack = new Stack<StatementBlock>();

	ColloboratingObject rootObject;

	final Map<String, ColloboratingObject> objects = new HashMap<String, ColloboratingObject>();

	public InteractionModel buildInteractionModel(final IMethod iMethod) {
		this.objects.clear();
		this.blockStack.clear();
		final InteractionModel interactionModel = new InteractionModel();
		this.blockStack.push(interactionModel);
		try {
			String source = iMethod.getSource();
			// System.out.println(source);
			final IType parent = iMethod.getDeclaringType();

			this.rootObject = new ColloboratingObject();
			this.rootObject.setName(parent.getElementName());

			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(iMethod.getCompilationUnit());
			parser.setResolveBindings(true);
			ASTNode root = parser.createAST(null);
			System.out.println("ShowMethodSequenceCommand.run() : "
					+ root.getClass());

			root.accept(new ASTVisitor() {

				@Override
				public boolean visit(final FieldDeclaration node) {
					// TODO Auto-generated method stub
					String simpleName = null;
					if (node != null) {
						String declaredName = node.fragments().get(0)
								.toString();
						if (declaredName.lastIndexOf('=') != -1) {
							declaredName = declaredName.substring(0,
									declaredName.indexOf('='));
						}
						simpleName = declaredName;
					}
					createCollaboratingObject(simpleName, node.getType());
					return super.visit(node);
				}

				@Override
				public boolean visit(final MethodDeclaration node) {
					try {
						IMethodBinding methodBinding = node.resolveBinding();
						IMethod resolvedMethod = ((IMethod) methodBinding
								.getJavaElement());
						String signature = resolvedMethod.getSignature();

						if (resolvedMethod.getElementName().equals(
								iMethod.getElementName())
								&& signature.equals(iMethod.getSignature())
								&& parent.getFullyQualifiedName().equals(
										resolvedMethod.getDeclaringType()
												.getFullyQualifiedName())) {
							System.out.println("# Found : "
									+ resolvedMethod.getElementName() + " => "
									+ signature);

							String[] parameterNames = resolvedMethod
									.getParameterNames();
							int index = 0;
							for (ITypeBinding typeParameter : methodBinding
									.getParameterTypes()) {
								createCollaboratingObject(
										parameterNames[index], typeParameter);
								index++;
							}

							Block body = node.getBody();
							processBlock(body);
						}
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}
			});

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		interactionModel.addObject(this.rootObject);
		for (String objectKey : this.objects.keySet()) {
			ColloboratingObject collaboratingObject = this.objects
					.get(objectKey);
			if (collaboratingObject.hasIncomingMessages()) {
				interactionModel.addObject(collaboratingObject);
			}
		}
		return interactionModel;
	}

	protected void handleStatement(final IfStatement statement) {
		System.out.println("#START IF : " + statement.getExpression());
		handleExpression(statement.getExpression());
		StatementBlock conditionnalStatement = new StatementBlock();
		conditionnalStatement.setExpression("[ "
				+ statement.getExpression().toString() + " ]");
		conditionnalStatement.setType(BlockType.IF);
		this.blockStack.peek().addStatement(conditionnalStatement);
		this.blockStack.push(conditionnalStatement);
		handleStatement(statement.getThenStatement());
		if (statement.getElseStatement() != null) {
			System.out.println("#ELSE");
			CombinedStatementBlock elseBlock = new CombinedStatementBlock();
			elseBlock.setExpression("[ Else ]");
			conditionnalStatement.addCombinedStatementBlock(elseBlock);
			this.blockStack.push(elseBlock);
			handleStatement(statement.getElseStatement());
			this.blockStack.pop();
		}
		this.blockStack.pop();
		System.out.println("#END IF");
	}

	protected void handleStatement(final ForStatement statement) {
		System.out.println("#START FOR : " + statement.getExpression());
		handleExpression(statement.getExpression());
		StatementBlock loopStatement = new StatementBlock();
		loopStatement.setType(BlockType.LOOP);
		this.blockStack.peek().addStatement(loopStatement);
		this.blockStack.push(loopStatement);
		handleStatement(statement.getBody());
		this.blockStack.pop();
		System.out.println("#END FOR");
	}

	protected void handleStatement(final EnhancedForStatement statement) {
		System.out.println("#START FOREACH : " + statement.getExpression());
		handleExpression(statement.getExpression());
		StatementBlock loopStatement = new StatementBlock();
		loopStatement.setType(BlockType.LOOP);
		this.blockStack.peek().addStatement(loopStatement);
		this.blockStack.push(loopStatement);
		handleStatement(statement.getBody());
		this.blockStack.pop();
		System.out.println("#END FOR");
	}

	protected void handleStatement(final SwitchStatement statement) {
		System.out.println("#START SWITCH");
		handleExpression(statement.getExpression());
		StatementBlock switchStatement = new StatementBlock();
		switchStatement.setType(BlockType.SWITCH);
		this.blockStack.peek().addStatement(switchStatement);
		this.blockStack.push(switchStatement);
		findInvocations(statement);
		this.blockStack.pop();
		System.out.println("#END SWITCH");
	}

	protected void handleStatement(final TryStatement statement) {
		System.out.println("#START TRY");

		StatementBlock tryCatchStatement = new StatementBlock();
		tryCatchStatement.setType(BlockType.TRY);
		this.blockStack.peek().addStatement(tryCatchStatement);
		this.blockStack.push(tryCatchStatement);
		processBlock(statement.getBody());

		List catches = statement.catchClauses();
		for (Object object : catches) {
			CatchClause catchClause = (CatchClause) object;

			System.out.println("#CATCH : " + catchClause.getException());
			CombinedStatementBlock catchBlock = new CombinedStatementBlock();
			catchBlock.setExpression("[ Catch ]");
			tryCatchStatement.addCombinedStatementBlock(catchBlock);
			this.blockStack.push(catchBlock);
			;
			createCollaboratingObject(catchClause.getException().getName()
					.toString(), catchClause.getException().getType());
			processBlock(catchClause.getBody());
			this.blockStack.pop();

		}

		Block finallyBlock = statement.getFinally();
		if (finallyBlock != null) {
			System.out.println("#FINALLY");
			CombinedStatementBlock catchBlock = new CombinedStatementBlock();
			catchBlock.setExpression("[ Finally ]");
			tryCatchStatement.addCombinedStatementBlock(catchBlock);
			this.blockStack.push(catchBlock);
			processBlock(finallyBlock);
			this.blockStack.pop();
		}

		this.blockStack.pop();
		System.out.println("END TRY");
	}

	protected void handleStatement(final WhileStatement statement) {
		System.out.println("#START WHILE : " + statement.getExpression());
		handleExpression(statement.getExpression());
		StatementBlock loopStatement = new StatementBlock();
		loopStatement.setType(BlockType.LOOP);
		this.blockStack.peek().addStatement(loopStatement);
		this.blockStack.push(loopStatement);
		handleStatement(statement.getBody());
		this.blockStack.pop();
		System.out.println("#END WHILE");
	}

	protected void handleStatement(final ExpressionStatement statement) {
		// System.out.println("START EXPRESSION : "
		// + statement.getExpression().getClass());
		handleExpression(statement.getExpression());
		// System.out.println("END EXPRESSION");
	}

	private void handleExpression(final Expression expression) {
		// System.out.println("InteractionModelBuilder.handleExpression() : "
		// + expression.getClass());
		switch (expression.getNodeType()) {
		case ASTNode.ASSIGNMENT:
			handleAssignment((Assignment) expression);
			break;
		case ASTNode.METHOD_INVOCATION:
			handleInvocation((MethodInvocation) expression);
			break;
		case ASTNode.INFIX_EXPRESSION:
			handleInvocation((InfixExpression) expression);
			break;
		default:
			break;
		}

	}

	private void handleInvocation(final InfixExpression expression) {
		System.out.println("InteractionModelBuilder.handleInvocation() : "
				+ expression.getLeftOperand() + " op "
				+ expression.getRightOperand());
		handleExpression(expression.getLeftOperand());
		handleExpression(expression.getRightOperand());
		if (expression.hasExtendedOperands()) {
			for (Object ext : expression.extendedOperands()) {
				handleExpression((Expression) ext);
			}
		}
	}

	private void handleInvocation(final MethodInvocation invocation) {
		String receiver = null;
		Expression expression = invocation.getExpression();
		MessageExchange messageExchange = null;
		if (expression != null) {
			receiver = expression.toString();
			ColloboratingObject receiverObject = this.objects.get(receiver);
			if (receiverObject != null) {
				messageExchange = new MessageExchange();
				messageExchange.setSource(this.rootObject);
				messageExchange.setDestination(receiverObject);
				messageExchange.setMessageName(invocation.getName().toString());
			} else {
				System.out
						.println("InteractionModelBuilder.handleInvocation() : NO RECEIVER FOUND in candidate objects");
			}

		} else {
			messageExchange = new MessageExchange();
			messageExchange.setSource(this.rootObject);
			messageExchange.setDestination(this.rootObject);
			messageExchange.setMessageName(invocation.getName().toString());
		}

		System.out.print("invocation : " + invocation.getName() + " => "
				+ expression + " ; args = ");
		for (Object arg : invocation.arguments()) {
			if (arg instanceof Expression) {
				Expression argExpression = (Expression) arg;
				handleExpression(argExpression);
			}
			System.out.print(arg.getClass() + "   ");
		}
		System.out.println();

		if (messageExchange != null) {
			System.out.println("#Invocation : "
					+ messageExchange.getDestination().getName() + "."
					+ messageExchange.getMessageName());
			this.blockStack.peek().addStatement(messageExchange);
		}

	}

	private void handleAssignment(final Assignment expression) {
		// System.out.print("variable : " + expression.getLeftHandSide());
		// System.out.println(" => expression : " +
		// expression.getRightHandSide()
		// + " => " + expression.getRightHandSide().getClass());
		handleExpression(expression.getRightHandSide());
	}

	private void findInvocations(final Statement statement) {
		statement.accept(new ASTVisitor() {
			@Override
			public boolean visit(final MethodInvocation invocation) {
				handleInvocation(invocation);
				return false;
			}
		});
	}

	public void processBlock(final Block block) {
		for (Object objectStatements : block.statements()) {
			Statement statement = (Statement) objectStatements;
			handleStatement(statement);
		}
	}

	protected void handleStatement(final Statement statement) {
		// System.out.println("InteractionModelBuilder.handleStatement() : "
		// + statement.getClass() + " : " + statement.getNodeType());
		switch (statement.getNodeType()) {
		case ASTNode.EXPRESSION_STATEMENT:
			handleStatement((ExpressionStatement) statement);
			break;
		case ASTNode.FOR_STATEMENT:
			handleStatement((ForStatement) statement);
			break;
		case ASTNode.ENHANCED_FOR_STATEMENT:
			handleStatement((EnhancedForStatement) statement);
			break;
		case ASTNode.IF_STATEMENT:
			handleStatement((IfStatement) statement);
			break;
		case ASTNode.SWITCH_STATEMENT:
			handleStatement((SwitchStatement) statement);
			break;
		case ASTNode.TRY_STATEMENT:
			handleStatement((TryStatement) statement);
			break;
		case ASTNode.WHILE_STATEMENT:
			handleStatement((WhileStatement) statement);
			break;
		case ASTNode.BLOCK:
			processBlock((Block) statement);
			break;
		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			handleStatement((VariableDeclarationStatement) statement);
			break;
		case ASTNode.RETURN_STATEMENT:
			handleStatement((ReturnStatement) statement);
			break;
		default:
			System.out.println("No processor : " + statement.getClass());
			break;

		}

	}

	protected void handleStatement(final ReturnStatement statement) {
		System.out.println("RETURN " + statement.getExpression());
		handleExpression(statement.getExpression());
	}

	protected void handleStatement(final VariableDeclarationStatement statement) {
		for (Object fragment : statement.fragments()) {
			VariableDeclarationFragment variableDeclarationFragment = (VariableDeclarationFragment) fragment;
			System.out.print("NEW VARIABLE : "
					+ variableDeclarationFragment.getName() + " => type : "
					+ statement.getType());

			String objectName = variableDeclarationFragment.getName()
					.toString();

			createCollaboratingObject(objectName, statement.getType());
			Expression initializer = variableDeclarationFragment
					.getInitializer();
			initializer.accept(new ASTVisitor() {

				@Override
				public boolean visit(final ArrayCreation node) {
					System.out.print(" => New Array : " + node.toString());
					return super.visit(node);
				}

				@Override
				public boolean visit(final ClassInstanceCreation node) {
					System.out.print(" => New Object : " + node.toString());
					return false;
				}

				@Override
				public boolean visit(final MethodInvocation node) {
					System.out.print(" => Returned from method : "
							+ node.toString());
					handleInvocation(node);
					return false;
				}
			});
			System.out.println();
		}

	}

	private void createCollaboratingObject(final String name,
			final ITypeBinding type) {
		if (!type.isPrimitive()) {
			createCollaboratingObject(name, type.getName());
		}
	}

	private void createCollaboratingObject(final String name, final String type) {
		ColloboratingObject collaboratingObject = new ColloboratingObject();
		collaboratingObject.setName(name);
		collaboratingObject.setType(type);
		this.objects.put(name, collaboratingObject);
	}

	private void createCollaboratingObject(final String name, final Type type) {
		if (!type.isPrimitiveType()) {
			createCollaboratingObject(name, type.toString());
		}
	}
}
