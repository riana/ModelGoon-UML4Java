package org.modelgoon.classdiagram.editor;

import org.modelgoon.classdiagram.model.UmlClassFactory;

public class ClassDiagramEditor extends DiagramEditor {

	/**
	 * Constructor for a Digraph1GraphicalEditor.
	 */
	public ClassDiagramEditor() {
		super(new UmlClassFactory());
	}

}
