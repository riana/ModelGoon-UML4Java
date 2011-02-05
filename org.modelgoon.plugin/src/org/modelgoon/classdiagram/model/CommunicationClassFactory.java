package org.modelgoon.classdiagram.model;

public class CommunicationClassFactory implements ClassModelFactory {

	public ClassModel createClassModel() {
		return new CommunicationClass();
	}

}
