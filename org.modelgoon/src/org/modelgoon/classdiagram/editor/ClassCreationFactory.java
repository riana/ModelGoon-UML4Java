package org.modelgoon.classdiagram.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.classdiagram.model.ClassModel;
import org.modelgoon.classdiagram.model.ClassModelFactory;

public class ClassCreationFactory implements CreationFactory {

	List<IResource> javaResources;

	private final Point location = new Point();

	ClassModelFactory classModelFactory;

	public ClassCreationFactory(final ClassModelFactory classModelFactory) {
		super();
		this.classModelFactory = classModelFactory;
	}

	public void setJavaResources(final List<IResource> javaResources) {
		this.javaResources = javaResources;
	}

	public Point setLocation(final Point point) {
		return this.location.setLocation(point);
	}

	@Override
	public Object getObjectType() {
		return List.class;
	}

	@Override
	public Object getNewObject() {
		List<ClassModel> classes = new ArrayList<ClassModel>();
		for (IResource javaResource : this.javaResources) {
			String extension = javaResource.getFileExtension();
			if ((extension != null) && extension.equalsIgnoreCase("java")) {

				try {
					IJavaElement javaElement = JavaCore.create(javaResource);
					if (javaElement instanceof IType) {
						classes.add(createClass((IType) javaElement));
					} else if (javaElement instanceof ICompilationUnit) {
						ICompilationUnit cu = (ICompilationUnit) javaElement;
						IType[] declaredTypes = cu.getAllTypes();
						for (IType iType : declaredTypes) {
							classes.add(createClass(iType));
						}
					}
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return classes;
	}

	public ClassModel createClass(final IType type) {
		ClassModel umlClass = this.classModelFactory.createClassModel();
		umlClass.setJavaElement(type);
		umlClass.setLocation(this.location);
		return umlClass;
	}

}
