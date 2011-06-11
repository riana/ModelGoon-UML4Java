package org.modelgoon.core.ui;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IPersistenceEventHandler {

	public Object load(final String file);

	public void save(IProgressMonitor monitor);

}
