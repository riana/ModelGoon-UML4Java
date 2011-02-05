/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.modelgoon.classdiagram.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;

public class ResourcesDropTargetListener extends
		TemplateTransferDropTargetListener {

	ClassCreationFactory creationFactory;

	public ResourcesDropTargetListener(final EditPartViewer viewer,
			final Transfer xfer, final ClassCreationFactory creationFactory) {
		super(viewer);
		setTransfer(xfer);
		this.creationFactory = creationFactory;
	}

	@Override
	public void dragEnter(final DropTargetEvent event) {
		super.dragEnter(event);
		event.detail = DND.DROP_COPY;
		event.feedback = DND.FEEDBACK_NONE;

	}

	@Override
	public void dragOver(final DropTargetEvent event) {
		super.dragOver(event);
		event.detail = DND.DROP_COPY;
		event.feedback = DND.FEEDBACK_NONE;
	}

	@Override
	protected Request createTargetRequest() {
		CreateRequest request = new CreateRequest();
		request.setFactory(this.creationFactory);
		return request;
	}

	@Override
	protected void updateTargetRequest() {
		List<IResource> javaResources = new ArrayList<IResource>();
		((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
		DropTargetEvent event = getCurrentEvent();
		if ((event.data != null) && (event.data instanceof IResource[])) {
			IResource[] resources = (IResource[]) event.data;
			for (IResource resource : resources) {
				javaResources.add(resource);
			}
		}
		this.creationFactory.setLocation(getDropLocation());
		this.creationFactory.setJavaResources(javaResources);

	}

}
