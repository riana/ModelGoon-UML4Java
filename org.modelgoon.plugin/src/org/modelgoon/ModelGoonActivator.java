package org.modelgoon;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ModelGoonActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.modelgoon.plugin"; //$NON-NLS-1$

	// The shared instance
	private static ModelGoonActivator plugin;

	public final static String DEFAULT_FONT_NAME = "Segoe UI";

	final Map<String, Font> fonts = new HashMap<String, Font>();

	/**
	 * The constructor
	 */
	public ModelGoonActivator() {

	}

	public static Display getDisplay() {
		return ModelGoonActivator.plugin.getWorkbench().getDisplay();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		ModelGoonActivator.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		ModelGoonActivator.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ModelGoonActivator getDefault() {
		return ModelGoonActivator.plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(
				ModelGoonActivator.PLUGIN_ID, path);
	}

	public void log(final String msg) {
		log(msg, null);
	}

	public void log(final String msg, final Exception e) {
		getLog().log(
				new Status(IStatus.INFO, ModelGoonActivator.PLUGIN_ID,
						IStatus.OK, msg, e));
	}

	public static Font getFont(final String name, final int size,
			final int style) {
		String fontId = name + "_" + size + "_" + style;
		Font font = ModelGoonActivator.getDefault().fonts.get(fontId);
		if (font == null) {
			FontData fontData = new FontData(name, size, style);
			font = new Font(ModelGoonActivator.getDisplay(), fontData);
			// ModelGoonActivator.fontRegistry.put(fontId,
			// new FontData[] { fontData });
			ModelGoonActivator.getDefault().fonts.put(fontId, font);
			// font = ModelGoonActivator.fontRegistry.getItalic(fontId);
		}
		return font;
	}

	public static Font getDefaultFont(final int size, final int style) {
		return ModelGoonActivator.getFont(ModelGoonActivator.DEFAULT_FONT_NAME,
				size, style);
	}

	public static int openWizardDialog(final Wizard wizard) {
		WizardDialog wizardDialog = new WizardDialog(ModelGoonActivator.plugin
				.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);

		return wizardDialog.open();
	}

}
