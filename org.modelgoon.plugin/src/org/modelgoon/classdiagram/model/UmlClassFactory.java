package org.modelgoon.classdiagram.model;

public class UmlClassFactory implements ClassModelFactory {

	public ClassModel createClassModel() {
		return new UmlClass();
	}

}
