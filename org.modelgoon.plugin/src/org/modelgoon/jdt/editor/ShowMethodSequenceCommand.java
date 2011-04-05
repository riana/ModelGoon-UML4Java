package org.modelgoon.jdt.editor;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.ide.IDE;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.jdt.editparts.MethodEditPart;
import org.modelgoon.jdt.model.Method;
import org.modelgoon.sequencediagram.InteractionModelBuilder;
import org.modelgoon.sequencediagram.SequenceDiagramEditor;
import org.modelgoon.sequencediagram.model.InteractionModel;

public class ShowMethodSequenceCommand extends SelectionAction {

	public static final String ID = "Show sequence";

	public ShowMethodSequenceCommand(
			final JDTDiagramEditor classDiagramEditor) {
		super(classDiagramEditor);
	}

	@Override
	protected void init() {
		super.init();
		setId(ShowMethodSequenceCommand.ID);
		setText(ShowMethodSequenceCommand.ID);
	}

	@Override
	protected boolean calculateEnabled() {
		// we only want enabled if is single selection
		if (getSelectedObjects().size() != 1) {
			return false;
		}
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof MethodEditPart) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		Object selection = getSelectedObjects().get(0);
		if (selection instanceof MethodEditPart) {
			MethodEditPart cep = (MethodEditPart) selection;
			final Method method = cep.getModelElement();
			final IMethod iMethod = method.getJdtMethod();
			InteractionModelBuilder interactionModelBuilder = new InteractionModelBuilder();
			InteractionModel interactionModel = interactionModelBuilder
					.buildInteractionModel(iMethod);

			try {
				IWorkbenchWindow dw = ModelGoonActivator.getDefault()
						.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = dw.getActivePage();

				String fileName = iMethod.getDeclaringType().getElementName()
						+ "#" + iMethod.getElementName();
				IFile file = iMethod.getCompilationUnit().getResource()
						.getProject().getFile(fileName + ".mgs");

				if (!file.exists()) {
					file.create(new ByteArrayInputStream(new byte[] {}), false,
							null);
				}

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

	private static void showNodeChildren(final ASTNode node, final int index) {
		for (int i = 0; i < index; i++) {
			System.out.print("\t");
		}
		System.out.println(node.getClass());
	}
}
