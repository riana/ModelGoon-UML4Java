package org.modelgoon.classes.editparts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelgoon.core.ModelElement;

public class ClassCompartmentModel extends ModelElement {

	List<Object> contents = new ArrayList<Object>();

	public ClassCompartmentModel(final Collection<?> list) {
		this.contents.addAll(list);
	}

	public List<Object> getContents() {
		return this.contents;
	}

}
