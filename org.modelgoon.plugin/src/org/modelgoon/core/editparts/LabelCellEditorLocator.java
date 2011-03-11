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
package org.modelgoon.core.editparts;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;
import org.modelgoon.core.NoteFigure;

final public class LabelCellEditorLocator implements CellEditorLocator {

	private NoteFigure stickyNote;

	public LabelCellEditorLocator(final NoteFigure stickyNote) {
		setLabel(stickyNote);
	}

	public void relocate(final CellEditor celleditor) {

		Text text = (Text) celleditor.getControl();
		Rectangle rect = this.stickyNote.getClientArea();
		this.stickyNote.translateToAbsolute(rect);
		org.eclipse.swt.graphics.Rectangle trim = text.computeTrim(0, 0, 0, 0);
		rect.translate(trim.x, trim.y);
		rect.width += trim.width;
		rect.height += trim.height;
		text.setBounds(rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Returns the stickyNote figure.
	 */
	protected NoteFigure getLabel() {
		return this.stickyNote;
	}

	/**
	 * Sets the Sticky note figure.
	 * 
	 * @param stickyNote
	 *            The stickyNote to set
	 */
	protected void setLabel(final NoteFigure stickyNote) {
		this.stickyNote = stickyNote;
	}

}