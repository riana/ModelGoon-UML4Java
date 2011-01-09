package org.modelgoon.sequencediagram.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.modelgoon.sequencediagram.model.ColloboratingObject;
import org.modelgoon.sequencediagram.model.CombinedStatementBlock;
import org.modelgoon.sequencediagram.model.InteractionModel;
import org.modelgoon.sequencediagram.model.MessageExchange;
import org.modelgoon.sequencediagram.model.StatementBlock;

public class SequenceDiagramEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(final EditPart context, final Object model) {
		EditPart editPart = null;
		// System.out.print("SequenceDiagramEditPartFactory");
		// System.out.print(" : " + model);
		if (model instanceof InteractionModel) {
			editPart = new SequenceDiagramEditPart();
		} else if (model instanceof ColloboratingObject) {
			editPart = new CollaboratingObjectEditPart();
		} else if (model instanceof MessageExchange) {
			editPart = new MessageExchangeEditPart();
		} else if (model instanceof CombinedStatementBlock) {
			editPart = new CombinedStatementBlockEditPart();
		} else if (model instanceof StatementBlock) {
			editPart = new StatementBlockEditPart();
		}

		// System.out.println(" => " + editPart);

		if (editPart != null) {
			editPart.setModel(model);
		}
		return editPart;
	}

}
