package org.modelgoon.core.ui;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IPersistenceEventHandler {

	public void load(final String file);

	public void save(IProgressMonitor monitor);

}
