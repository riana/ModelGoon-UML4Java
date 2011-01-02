package org.modelgoon.classdiagram.editor;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;
import org.modelgoon.classdiagram.command.EditVisualPreferencesCommand;
import org.modelgoon.classdiagram.command.ExportImageCommand;
import org.modelgoon.classdiagram.command.FitDiagramToPageCommand;
import org.modelgoon.classdiagram.command.OpenInEditorCommand;
import org.modelgoon.classdiagram.command.ShowMethodSequenceCommand;
import org.modelgoon.classdiagram.command.UpdateDiagramCommand;

public class ClassContextMenuProvider extends ContextMenuProvider {

	private final ActionRegistry actionRegistry;

	public ClassContextMenuProvider(final EditPartViewer viewer,
			final ActionRegistry actionRegistry) {
		super(viewer);
		this.actionRegistry = actionRegistry;
	}

	@Override
	public void buildContextMenu(final IMenuManager menuManager) {
		GEFActionConstants.addStandardActionGroups(menuManager);
		IAction action = this.actionRegistry.getAction(ActionFactory.DELETE
				.getId());
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		action = this.actionRegistry.getAction(OpenInEditorCommand.ID);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		action = this.actionRegistry.getAction(ShowMethodSequenceCommand.ID);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		action = this.actionRegistry.getAction(UpdateDiagramCommand.ID);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
		}

		action = this.actionRegistry.getAction(ExportImageCommand.ID);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_SAVE, action);
		}

		action = this.actionRegistry.getAction(EditVisualPreferencesCommand.ID);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
		}

		action = this.actionRegistry.getAction(FitDiagramToPageCommand.ID);
		if (action.isEnabled()) {
			menuManager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
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
