package org.modelgoon.sequencediagram.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractionModel extends StatementBlock {

	private final Map<String, ColloboratingObject> objectsRegistry = new HashMap<String, ColloboratingObject>();

	private final List<ColloboratingObject> objects = new ArrayList<ColloboratingObject>();

	public final void addObject(final ColloboratingObject colloboratingObject) {
		if (!this.objects.contains(colloboratingObject)) {
			this.objects.add(colloboratingObject);
			this.objectsRegistry.put(colloboratingObject.getName(),
					colloboratingObject);
		}
	}

	public final void insertObjectAt(final int index,
			final ColloboratingObject colloboratingObject) {
		this.objects.add(index, colloboratingObject);
		this.objectsRegistry.put(colloboratingObject.getName(),
				colloboratingObject);
	}

	public final List<ColloboratingObject> getObjects() {
		return this.objects;
	}

}
