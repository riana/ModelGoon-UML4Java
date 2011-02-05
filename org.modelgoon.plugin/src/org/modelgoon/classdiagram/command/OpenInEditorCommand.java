package org.modelgoon.classdiagram.command;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.PartInitException;
import org.modelgoon.classdiagram.editParts.JavaModelElementEditPart;
import org.modelgoon.classdiagram.editor.DiagramEditor;

public class OpenInEditorCommand extends SelectionAction {

	public static final String ID = "Open in Editor";

	public OpenInEditorCommand(final DiagramEditor classDiagramEditor) {
		super(classDiagramEditor);
	}

	@Override
	protected void init() {
		super.init();
		setId(OpenInEditorCommand.ID);
		setText(OpenInEditorCommand.ID);
	}

	@Override
	protected boolean calculateEnabled() {
		// we only want enabled if is single selection
		if (getSelectedObjects().size() != 1) {
			return false;
		}
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof JavaModelElementEditPart) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof JavaModelElementEditPart) {
			JavaModelElementEditPart cep = (JavaModelElementEditPart) selection;
			IJavaElement javaElement = cep.getJavaModelElement()
					.getJavaElement();
			if (javaElement != null) {
				try {
					JavaUI.openInEditor(javaElement, true, true);
				} catch (PartInitException e) {
					e.printStackTrace();
				} catch (JavaModelException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
