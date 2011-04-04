package org.modelgoon.sequencediagram.commands;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;

public class DefaultContextMenuProvider extends ContextMenuProvider {

	private final ActionRegistry actionRegistry;

	public DefaultContextMenuProvider(final EditPartViewer viewer,
			final ActionRegistry actionRegistry) {
		super(viewer);
		this.actionRegistry = actionRegistry;
	}

	@Override
	public void buildContextMenu(final IMenuManager menuManager) {
		GEFActionConstants.addStandardActionGroups(menuManager);
		IAction action = this.actionRegistry.getAction(ExportImageCommand.ID);

		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_SAVE, action);
		}

		action = this.actionRegistry.getAction(GEFActionConstants.ZOOM_IN);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
		}

		action = this.actionRegistry.getAction(GEFActionConstants.ZOOM_OUT);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
		}

	}

}
