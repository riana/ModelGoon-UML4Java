package org.modelgoon.classes.model;

import java.util.ArrayList;
import java.util.List;

public class Method extends StructuralFeature {

	List<String> parameterTypes = new ArrayList<String>();

	public List<String> getParameterTypes() {
		return this.parameterTypes;
	}

	public void setParameterTypes(final List<String> parameterTypes) {
		this.parameterTypes.addAll(parameterTypes);
	}
}
