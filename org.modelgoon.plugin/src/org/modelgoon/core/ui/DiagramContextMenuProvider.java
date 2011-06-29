package org.modelgoon.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

public class DiagramContextMenuProvider extends ContextMenuProvider {

	List<GroupedAction> actions = new ArrayList<GroupedAction>();

	ActionRegistry actionRegistry;

	public DiagramContextMenuProvider(final EditPartViewer viewer,
			final ActionRegistry actionRegistry) {
		super(viewer);
		this.actionRegistry = actionRegistry;
	}

	public void addAction(final IAction action, final String group) {
		this.actions.add(new GroupedAction(action, group));
	}

	@Override
	public void buildContextMenu(final IMenuManager menuManager) {
		GEFActionConstants.addStandardActionGroups(menuManager);

		IAction deleteAction = this.actionRegistry
				.getAction(ActionFactory.DELETE.getId());
		if (deleteAction.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_EDIT,
					deleteAction);
		}

		for (GroupedAction groupedAction : this.actions) {
			IAction action = groupedAction.getAction();
			if (action.isEnabled()) {
				menuManager.appendToGroup(groupedAction.getGroup(), action);
			}
		}

	}

	private class GroupedAction {

		IAction action;

		String group;

		public GroupedAction(final IAction action, final String group) {
			super();
			this.action = action;
			this.group = group;
		}

		public IAction getAction() {
			return this.action;
		}

		public String getGroup() {
			return this.group;
		}

	}

}
