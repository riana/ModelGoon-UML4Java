package org.modelgoon.packages.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.modelgoon.core.ModelLoader;
import org.modelgoon.core.ui.IPersistenceEventHandler;
import org.modelgoon.dao.DAOException;
import org.modelgoon.packages.model.PackageDiagram;

public class PackageDiagramLoader implements IPersistenceEventHandler {

	ModelLoader modelLoader = new ModelLoader();

	public PackageDiagramLoader() {
		this.modelLoader
				.addMapping("org/modelgoon/packages/xml/PackageAnalysis.cas");
	}

	public Object load(final String file) {
		IJavaProject javaProject = this.modelLoader.getContainer(file);
		PackageDiagram model = new PackageDiagram();
		try {
			model = this.modelLoader.loadData(file);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// PackageDiagram diagram = new PackageDiagram();
		model.setJavaProject(javaProject);
		// model.setJavaProject(javaProject);
		// for (Package pkg : model.getPackages()) {
		// PackageElement p = new PackageElement();
		// p.setQualifiedName(pkg.getName());
		// diagram.addPackage(p);
		// p.setLocation(pkg.getX(), pkg.getY());
		// }
		model.consolidate();
		return model;
	}

	public void save(final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
