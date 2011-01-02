package org.modelgoon.dao;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.modelgoon.ModelGoonActivator;

public class DataAccessLayer {

	private final IEditorInput input;

	private IJavaProject javaProject;

	private String filePath = null;

	private final CastorDAO castorDAO = new CastorDAO();

	public DataAccessLayer(final IEditorInput input) {
		super();
		this.input = input;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root != null) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			this.filePath = fileEditorInput.getPath().toOSString();
			IProject project = null;
			IContainer[] projects = root
					.findContainersForLocationURI(fileEditorInput.getURI());

			int index = 0;
			int min = 0;
			for (int j = 0; j < projects.length; j++) {
				IContainer iContainer = projects[j];
				int segments = iContainer.getFullPath().segmentCount();
				if ((min == 0) || (min > segments)) {
					min = segments;
					index = j;
				}
			}
			project = root.getProject(projects[index].getProject().getName());
			try {
				// if (project.hasNature(JavaCore.NATURE_ID)) {
				this.javaProject = JavaCore.create(project);
				this.javaProject.open(null);
				ModelGoonActivator.getDefault().log("Java project found");
				// } else {
				// ModelGoonActivator.getDefault().log(
				// "Project has no JAVA Nature");
				// }
			} catch (Exception e) {
				ModelGoonActivator.getDefault().log(e.getMessage(), e);
			}
		} else {
			ErrorDialog errorDialog = new ErrorDialog(ModelGoonActivator
					.getDefault().getWorkbench().getDisplay().getActiveShell(),
					"Error Loading File", "Can't find workspace root", null,
					IStatus.ERROR);
		}
	}

	public void addMapping(final String path) {
		this.castorDAO.addMapping(path);
	}

	public <T> T loadData() throws DAOException {
		return this.castorDAO.loadData(this.filePath);
	}

	public <T> void saveData(final T data) throws DAOException {
		this.castorDAO.saveData(data, this.filePath);
	}

	public IJavaProject getJavaProject() {
		return this.javaProject;
	}

	public String getWorkingDirectory() {
		File currentFile = new File(this.filePath);
		return currentFile.getParent();
	}

	public String getFileName() {
		File currentFile = new File(this.filePath);
		return currentFile.getName();
	}

}
