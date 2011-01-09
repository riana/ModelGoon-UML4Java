package org.modelgoon.sequencediagram.ui;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SequenceDiagramTest {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Display d = new Display();
		final Shell shell = new Shell(d, SWT.CLOSE | SWT.TITLE | SWT.MIN
				| SWT.MAX | SWT.RESIZE);
		shell.setSize(800, 750);
		shell.setText("InteractionModelBuilder Diagram Test");

		LightweightSystem lws = new LightweightSystem(shell);
		shell.setBackground(new Color((Device) null, 255, 255, 255));
		final SequenceDiagramFigure contents = new SequenceDiagramFigure();

		final String p1 = ": Participant1";

		final String p2 = ": Participant2";

		final String p3 = ": Participant3";

		final String p4 = ": Participant4";

		contents.addLifeline("Actor");
		contents.addLifeline(p1);
		contents.addLifeline(p2);
		contents.addLifeline(p3);
		contents.addLifeline(p4);

		lws.setContents(contents);

		Thread t = new Thread() {

			@Override
			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				d.syncExec(new Runnable() {

					public void run() {
						contents.addMessage("Message1", p1, p2);
						contents.addMessage("Message2", p1, p1);
						contents.addMessage("Message3", p2, p3);

						FrameFigure frame = new FrameFigure();
						frame.setLabel("Try");
						contents.add(frame);

						frame.add(contents.createMessageConnection(
								"inFrameMessage1", p3, p2));
						frame.add(contents.createMessageConnection(
								"inFrameMessage2", p4, p2));

						frame.add(contents.createMessageConnection(
								"inFrameMessage3", p2, p4));

						FrameFigure frame2 = new FrameFigure();
						frame2.setLabel("Loop");
						frame2.setGuard("[ Expression ]");
						frame.add(frame2);

						frame2.add(contents.createMessageConnection(
								"inFrameMessageA", p3, p1));
						frame2.add(contents.createMessageConnection(
								"inFrameMessageB", p4, p2));

						FrameFigure frame3 = new FrameFigure();
						frame3.setLabel("Alt");
						frame3.setGuard("[ If condition ]");
						frame2.add(frame3);

						frame3.add(contents.createMessageConnection(
								"inFrameMessageC", p3, p4));

						CombinedFragmentFigure combined = new CombinedFragmentFigure();
						combined.setGuard("[ Else ]");
						// combined.add(contents.createMessageConnection(
						// "inFrameMessageD", p4, p3));
						frame3.add(combined);

						FrameFigure frame4 = new FrameFigure();
						frame4.setLabel("For");
						frame.add(frame4);

						frame4.add(contents.createMessageConnection(
								"inFrameMessageC", p1, p4));

						CombinedFragmentFigure catchCombined = new CombinedFragmentFigure();
						catchCombined.setGuard("[ Catch ]");
						// catchCombined.add(frame2);

						catchCombined.add(contents.createMessageConnection(
								"handle", p4, p3));

						frame.add(catchCombined);
						contents.addMessage("Message4", p3, p4);
					}
				});

			}
		};
		t.start();
		shell.open();
		while (!shell.isDisposed()) {
			while (!d.readAndDispatch()) {
				d.sleep();
			}
		}

	}
}
