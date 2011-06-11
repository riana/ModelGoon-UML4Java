package org.modelgoon.sequencediagram.model;

import java.util.ArrayList;
import java.util.List;

public class StatementBlock implements Statement {

	BlockType type = BlockType.UNDEFINED;

	String expression = "";

	private final List<Statement> statements = new ArrayList<Statement>();

	private final List<CombinedStatementBlock> combinedStatementBlocks = new ArrayList<CombinedStatementBlock>();

	public final void addStatement(final Statement statement) {
		this.statements.add(statement);
	}

	public final void insertStatementAt(final int index,
			final Statement statement) {
		this.statements.add(index, statement);
	}

	public final List<Statement> getStatements() {
		return this.statements;
	}

	public boolean hasStatements() {
		return !this.statements.isEmpty();
	}

	public final void addCombinedStatementBlock(
			final CombinedStatementBlock combinedStatementBlock) {
		this.combinedStatementBlocks.add(combinedStatementBlock);
	}

	public List<CombinedStatementBlock> getCombinedStatementBlocks() {
		return this.combinedStatementBlocks;
	}

	public void setType(final BlockType type) {
		this.type = type;
	}

	public BlockType getType() {
		return this.type;
	}

	public void setExpression(final String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return this.expression;
	}

}
