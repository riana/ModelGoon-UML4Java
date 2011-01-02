package org.modelgoon.classdiagram.model;

public class UmlClassFactory implements ClassModelFactory {

	@Override
	public ClassModel createClassModel() {
		return new UmlClass();
	}

}
