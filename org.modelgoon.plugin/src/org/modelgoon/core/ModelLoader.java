package org.modelgoon.core;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.dao.CastorDAO;
import org.modelgoon.dao.DAOException;

public class ModelLoader {

	private final CastorDAO castorDAO = new CastorDAO();

	public void addMapping(final String path) {
		this.castorDAO.addMapping(path);

	}

	public IJavaProject getContainer(final String filePath) {
		IJavaProject javaProject = null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root != null) {
			IProject project = null;
			IContainer[] projects = root
					.findContainersForLocationURI((new File(filePath)).toURI());

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
				javaProject = JavaCore.create(project);
				javaProject.open(null);
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
		return javaProject;
	}

	public <T> T loadData(final String filePath) throws DAOException {
		return this.castorDAO.loadData(filePath);
	}

	public <T> void saveData(final T data, final String filePath)
			throws DAOException {
		this.castorDAO.saveData(data, filePath);
	}
}
