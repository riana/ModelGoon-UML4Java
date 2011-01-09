package org.modelgoon.sequencediagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.ide.IDE;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.sequencediagram.model.InteractionModel;

public class CreateSequenceDiagram implements IObjectActionDelegate {

	IMethod selecteMethod;

	public void run(final IAction action) {
		if (this.selecteMethod != null) {
			InteractionModelBuilder interactionModelBuilder = new InteractionModelBuilder();
			InteractionModel interactionModel = interactionModelBuilder
					.buildInteractionModel(this.selecteMethod);

			try {
				IWorkbenchWindow dw = ModelGoonActivator.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = dw.getActivePage();

				String fileName = this.selecteMethod.getDeclaringType()
						.getElementName()
						+ "#"
						+ this.selecteMethod.getElementName();
				IWorkspace workspace = ResourcesPlugin.getWorkspace();

				IFile file = this.selecteMethod.getCompilationUnit()
						.getResource().getProject().getFile(fileName + ".mgs");
				// file.setHidden(true);
				// if (!file.exists()) {
				// file.create(new ByteArrayInputStream(new byte[] {}), false,
				// null);
				// }

				if (page != null) {
					SequenceDiagramEditor editor = (SequenceDiagramEditor) IDE
							.openEditor(page, file, true);
					editor.setModel(interactionModel);
				}
			} catch (WorkbenchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void selectionChanged(final IAction action,
			final ISelection selection) {
		if (selection instanceof ITreeSelection) {
			ITreeSelection treeSelection = (ITreeSelection) selection;
			Object selectedObject = treeSelection.getFirstElement();
			if (selectedObject instanceof IMethod) {
				this.selecteMethod = (IMethod) selectedObject;
			}
			System.out.println("CreateSequenceDiagram.selectionChanged() : "
					+ treeSelection.getFirstElement().getClass());
		}
	}

	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

}
