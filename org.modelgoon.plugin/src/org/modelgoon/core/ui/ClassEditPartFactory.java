package org.modelgoon.core.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class ClassEditPartFactory implements EditPartFactory {

	Map<Class<?>, Class<? extends EditPart>> classesMapping = new HashMap<Class<?>, Class<? extends EditPart>>();

	public void registerEditPart(final Class<?> modelClass,
			final Class<? extends EditPart> editPartClass) {
		this.classesMapping.put(modelClass, editPartClass);
	}

	public EditPart createEditPart(final EditPart context, final Object model) {
		EditPart editPart = null;
		Class<? extends EditPart> editPartClass = this.classesMapping.get(model
				.getClass());
		if (editPartClass != null) {
			try {
				editPart = editPartClass.newInstance();
				editPart.setModel(model);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return editPart;
	}
}