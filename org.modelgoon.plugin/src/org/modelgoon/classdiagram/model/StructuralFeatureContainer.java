package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

public class StructuralFeatureContainer extends ArrayList<StructuralFeature> {

	public StructuralFeatureContainer(
			final List<? extends StructuralFeature> list) {
		addAll(list);
	}

}
