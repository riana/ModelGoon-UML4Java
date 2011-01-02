package org.modelgoon.classdiagram.model;

public class CommunicationClassFactory implements ClassModelFactory {

	@Override
	public ClassModel createClassModel() {
		return new CommunicationClass();
	}

}
