package org.modelgoon.jdt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.core.ui.ModelElementFactory;
import org.modelgoon.jdt.model.UMLClass;

public class ClassModelElementFactory implements ModelElementFactory {

	JDTDiagramEditor jdtClassDiagramEditor;

	public ClassModelElementFactory(
			final JDTDiagramEditor jdtClassDiagramEditor) {
		super();
		this.jdtClassDiagramEditor = jdtClassDiagramEditor;
	}

	public List createObjectFromDroppedResources(
			final List<IResource> resources, final Point location) {
		List<UMLClass> classes = new ArrayList<UMLClass>();
		for (IResource javaResource : resources) {
			String extension = javaResource.getFileExtension();
			if ((extension != null) && extension.equalsIgnoreCase("java")) {

				try {
					IJavaElement javaElement = JavaCore.create(javaResource);
					if (javaElement instanceof IType) {
						classes.add(createClass((IType) javaElement, location));
					} else if (javaElement instanceof ICompilationUnit) {
						ICompilationUnit cu = (ICompilationUnit) javaElement;
						IType[] declaredTypes = cu.getAllTypes();
						for (IType iType : declaredTypes) {
							classes.add(createClass(iType, location));
						}
					}
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	public UMLClass createClass(final IType type, final Point location) {
		UMLClass umlClass = new UMLClass(type.getElementName());
		umlClass.setJavaType(type);
		umlClass.setLocation(location);
		this.jdtClassDiagramEditor.getModel().addClass(umlClass);
		return umlClass;
	}

}
