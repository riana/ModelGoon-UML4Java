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

import org.eclipse.gef.commands.Command;
import org.modelgoon.core.Note;

public class SetNoteContentsCommand extends Command {

	private String newName, oldName;
	private final Note note;

	public SetNoteContentsCommand(final Note l, final String s) {
		this.note = l;
		if (s != null) {
			this.newName = s;
		} else {
			this.newName = ""; //$NON-NLS-1$
		}
	}

	@Override
	public void execute() {
		this.oldName = this.note.getContent();
		this.note.setContent(this.newName);
	}

	@Override
	public void undo() {
		this.note.setContent(this.oldName);
	}

}
