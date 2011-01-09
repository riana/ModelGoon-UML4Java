package org.modelgoon.packages.editor;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.Graph;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.dao.DAOException;
import org.modelgoon.dao.DataAccessLayer;
import org.modelgoon.packages.controls.AddPackageControl;
import org.modelgoon.packages.model.PackageAnalysis;
import org.modelgoon.packages.views.PackageDependencyGraph;
import org.modelgoon.packages.views.PackageGraphNode;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class PackagesEditor extends EditorPart implements
		IZoomableWorkbenchPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.modelgoon.packages.editors.PackagesDependenciesView";

	GraphViewer graphViewer;

	PackageDependencyGraph viewer;

	PackageAnalysis packageAnalysis;

	DataAccessLayer dataAccessLayer;

	private ZoomContributionViewItem contextZoomContributionViewItem;

	/**
	 * The constructor.
	 */
	public PackagesEditor() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */

	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(new FillLayout());
		this.graphViewer = new GraphViewer(parent, SWT.NONE);
		this.graphViewer.getControl().dispose();
		this.viewer = new PackageDependencyGraph(parent, SWT.NONE) {

			@Override
			public Point computeSize(final int hint, final int hint2,
					final boolean changed) {
				return new Point(0, 0);
			}
		};
		this.graphViewer.setControl(this.viewer);
		this.viewer.setAddPackageControl(new AddPackageControl(
				this.packageAnalysis));
		this.viewer.setPackageAnalysis(this.packageAnalysis);
		this.viewer.addMouseMoveListener(new MouseMoveListener() {

			public void mouseMove(final MouseEvent e) {
				if ((e.stateMask & SWT.BUTTON_MASK) != 0) {
					List<PackageGraphNode> selection = PackagesEditor.this.viewer
							.getSelection();
					if (!selection.isEmpty()) {
						PackagesEditor.this.packageAnalysis.setDirty(true);
						firePropertyChange(IEditorPart.PROP_DIRTY);
					}
				}
			}
		});

		this.contextZoomContributionViewItem = new ZoomContributionViewItem(
				this);

		hookContextMenu();
		this.viewer.refresh();
	}

	/**
	 * Creates the context menu for this view.
	 */
	private void hookContextMenu() {

		final Menu menu = new Menu(this.viewer);
		MenuItem deleteAction = new MenuItem(menu, SWT.NONE);
		deleteAction.setText("Remove");
		deleteAction.addSelectionListener(new SelectionListener() {

			public void widgetSelected(final SelectionEvent e) {
				List<PackageGraphNode> selection = PackagesEditor.this.viewer
						.getSelection();
				for (PackageGraphNode packageGraphNode : selection) {
					PackagesEditor.this.packageAnalysis
							.removePackage(packageGraphNode.getPackage());
				}
				PackagesEditor.this.viewer.refresh();
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		MenuItem refreshAction = new MenuItem(menu, SWT.NONE);
		refreshAction.setText("Refresh");
		refreshAction.addSelectionListener(new SelectionListener() {

			public void widgetSelected(final SelectionEvent e) {
				PackagesEditor.this.packageAnalysis.update();
				PackagesEditor.this.viewer.refresh();
			}

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Action screenshotAction = new Action() {

			@Override
			public void run() {

				Shell shell = ModelGoonActivator.getDefault().getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				Graph g = PackagesEditor.this.viewer;

				Rectangle bounds = g.getContents().getBounds();
				Point size = new Point(g.getContents().getSize().width, g
						.getContents().getSize().height);
				org.eclipse.draw2d.geometry.Point viewLocation = g
						.getViewport().getViewLocation();
				final Image image = new Image(null, size.x, size.y);
				GC gc = new GC(image);
				SWTGraphics swtGraphics = new SWTGraphics(gc);
				swtGraphics.translate(-1 * bounds.x + viewLocation.x, -1
						* bounds.y + viewLocation.y);
				g.getViewport().paint(swtGraphics);
				gc.copyArea(image, 0, 0);
				gc.dispose();

				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "Image Files",
						"All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.png", "*.*" }); // Windows
																				// wild
				// cards
				dialog.setFilterPath(PackagesEditor.this.dataAccessLayer
						.getWorkingDirectory());
				// dialog.setFilterPath ("c:\\"); //Windows path
				String name = PackagesEditor.this.dataAccessLayer.getFileName();
				name = name.substring(0, name.lastIndexOf(".")) + ".png";
				dialog.setFileName(name);
				String fileName = dialog.open();
				ImageLoader loader = new ImageLoader();
				loader.data = new ImageData[] { image.getImageData() };
				loader.save(fileName, SWT.IMAGE_PNG);

			}
		};

		screenshotAction.setText("Save as Image");
		screenshotAction.setToolTipText("Save as Image");
		screenshotAction.setEnabled(true);
		MenuItem screenShotItem = new MenuItem(menu, SWT.NONE);
		screenShotItem.setText(screenshotAction.getText());
		screenShotItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(final SelectionEvent e) {
				screenshotAction.run();
			}

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new MenuItem(menu, SWT.SEPARATOR);
		this.viewer.setMenu(menu);
		this.viewer.addMenuDetectListener(new MenuDetectListener() {

			public void menuDetected(final MenuDetectEvent e) {
				fillContextMenu(menu);
				menu.setVisible(true);

			}
		});

	}

	/**
	 * Add the items to the context menu
	 * 
	 * @param manager
	 */
	private void fillContextMenu(final Menu menu) {

		this.contextZoomContributionViewItem.fill(menu, -1);
	}

	@Override
	public void setFocus() {
		this.viewer.setFocus();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		try {
			this.dataAccessLayer.saveData(this.packageAnalysis);
			this.packageAnalysis.setDirty(false);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input)
			throws PartInitException {
		setSite(site);

		setInput(input);
		setPartName(input.getName());
		this.dataAccessLayer = new DataAccessLayer(input);
		this.dataAccessLayer
				.addMapping("org/modelgoon/packages/xml/PackageAnalysis.cas");
		try {
			this.packageAnalysis = this.dataAccessLayer.loadData();
		} catch (DAOException e) {
			this.packageAnalysis = new PackageAnalysis();
		}
		final IJavaProject javaProject = this.dataAccessLayer.getJavaProject();
		if (javaProject.isOpen()) {
			this.packageAnalysis.setJavaProject(javaProject);
		} else {
			ModelGoonActivator.getDefault().log("No Java Project found");
		}
	}

	@Override
	public boolean isDirty() {
		return this.packageAnalysis.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	public AbstractZoomableViewer getZoomableViewer() {
		return this.graphViewer;
	}

}