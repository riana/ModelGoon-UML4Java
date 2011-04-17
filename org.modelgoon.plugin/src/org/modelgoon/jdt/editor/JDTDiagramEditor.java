package org.modelgoon.jdt.editor;

import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.ModelGoonIcons;
import org.modelgoon.core.ModelLoader;
import org.modelgoon.core.Note;
import org.modelgoon.core.ui.Diagram;
import org.modelgoon.dao.DAOException;
import org.modelgoon.jdt.model.UMLModel;

public abstract class JDTDiagramEditor extends Diagram<UMLModel> {

	protected ModelLoader modelLoader = new ModelLoader();

	public JDTDiagramEditor(final UMLModel model) {
		super(model);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot paletteRoot = new PaletteRoot();
		PaletteGroup group = new PaletteGroup("Creation tools");

		ImageDescriptor imgDesc = ModelGoonActivator
				.getImageDescriptor(ModelGoonIcons.COMMENT_ICON);

		group.add(new CreationToolEntry("Note", "Insert a new Note in ",
				new SimpleFactory(Note.class), imgDesc, imgDesc));
		paletteRoot.add(group);

		fillPalette(paletteRoot, group);
		return paletteRoot;
	}

	protected void fillPalette(final PaletteRoot paletteRoot,
			final PaletteGroup group) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void registerActions() {
		addAction(new UpdateDiagramCommand(this), GEFActionConstants.GROUP_VIEW);
		addSelectionAction(new EditVisualPreferencesCommand(this),
				GEFActionConstants.GROUP_VIEW);
		addSelectionAction(new EditAssociationCommand(this),
				GEFActionConstants.GROUP_VIEW);
		addSelectionAction(new ShowMethodSequenceCommand(this),
				GEFActionConstants.GROUP_EDIT);
		addSelectionAction(new OpenInEditorCommand(this),
				GEFActionConstants.GROUP_EDIT);
	}

	@Override
	protected UMLModel load(final String file) {

		IJavaProject javaProject = this.modelLoader.getContainer(file);

		UMLModel model = new UMLModel();
		try {
			model = this.modelLoader.loadData(file);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.setJavaProject(javaProject);

		return model;
	}

	@Override
	protected void save(final UMLModel model, final String filePath) {
		try {
			this.modelLoader.saveData(model, filePath);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}