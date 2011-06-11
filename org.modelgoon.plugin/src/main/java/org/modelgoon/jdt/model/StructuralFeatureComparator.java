package org.modelgoon.jdt.model;

import java.util.Comparator;


public class StructuralFeatureComparator implements
		Comparator<StructuralFeature> {

	public final static StructuralFeatureComparator SINGLETON = new StructuralFeatureComparator();

	private StructuralFeatureComparator() {
	}

	public int compare(final StructuralFeature o1, final StructuralFeature o2) {
		if (o1.isStatic() == o2.isStatic()) {
			return o1.toString().compareTo(o2.toString());
		} else {
			return o1.isStatic() == true ? -1 : 1;
		}
	}

}
