package org.modelgoon.packages.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.packages.controls.AddPackageControl;
import org.modelgoon.packages.model.Dependency;
import org.modelgoon.packages.model.Package;
import org.modelgoon.packages.model.PackageAnalysis;

public class PackageDependencyGraph extends Graph {

	Map<Package, PackageGraphNode> packageItems = new HashMap<Package, PackageGraphNode>();

	Map<String, GraphConnection> connections = new HashMap<String, GraphConnection>();

	PackageAnalysis packageAnalysis;

	AddPackageControl addPackageControl;

	public PackageDependencyGraph(final Composite parent, final int style) {
		super(parent, style);
		// setLayoutAlgorithm(new SpringLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		// setLayoutAlgorithm(new GridLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);

		// setLayoutAlgorithm(new RadialLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		DropTarget dt = new DropTarget(this, DND.DROP_MOVE | DND.DROP_COPY
				| DND.DROP_DEFAULT);
		dt.setTransfer(new Transfer[] { ResourceTransfer.getInstance() });

		dt.addDropListener(new DropTargetAdapter() {

			@Override
			public void dragEnter(final DropTargetEvent event) {
				// always indicate a copy
				event.detail = DND.DROP_COPY;
				event.feedback = DND.FEEDBACK_NONE;
			}

			@Override
			public void dragOver(final DropTargetEvent event) {
				// always indicate a copy
				event.detail = DND.DROP_COPY;
				event.feedback = DND.FEEDBACK_NONE;
			}

			@Override
			public void drop(final DropTargetEvent event) {
				super.drop(event);
				if (event.data instanceof IResource[]) {
					IResource[] resources = (IResource[]) event.data;
					for (IResource resource : resources) {
						handleDropResource(resource, event.x, event.y);
					}

				}

			}

			@Override
			public void dropAccept(final DropTargetEvent event) {
				// System.out.println("Drop Accept : " + event);
				// event.detail = DND.DROP_NONE;
			}

		});

	}

	protected void handleDropResource(final IResource resource, final int x,
			final int y) {
		Point p = toControl(x, y);
		ModelGoonActivator.getDefault().log("Handle drop resource");
		this.addPackageControl.addPackage(resource, p.x, p.y);
		refresh();
	}

	public void setPackageAnalysis(final PackageAnalysis packageAnalysis) {
		this.packageAnalysis = packageAnalysis;
	}

	public void setAddPackageControl(final AddPackageControl addPackageControl) {
		this.addPackageControl = addPackageControl;
	}

	public void refresh() {
		for (GraphConnection connection : this.connections.values()) {
			connection.dispose();
		}
		this.connections.clear();
		if (this.packageAnalysis.getPackages().size() != this.packageItems
				.size()) {
			for (PackageGraphNode connection : this.packageItems.values()) {
				connection.dispose();
			}
			this.packageItems.clear();
		}
		for (Package pkg : this.packageAnalysis.getPackages()) {
			PackageGraphNode source = getPackageGraphNode(pkg);
			for (Dependency dependency : pkg.getDependencies()) {
				Package depPackage = this.packageAnalysis
						.findPackage(dependency.getPackageName());
				if (depPackage != null) {
					PackageGraphNode destination = getPackageGraphNode(depPackage);
					GraphConnection con = new GraphConnection(this, SWT.NONE,
							source, destination);
					String connectionName = pkg.getName() + "_"
							+ depPackage.getName();
					this.connections.put(connectionName, con);
					if (depPackage.dependsUpon(pkg)) {
						con.setLineColor(new Color(null, 200, 0, 0));
					} else {
						con.setLineColor(new Color(null, 100, 100, 100));
					}

					con.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
				}
			}
		}

	}

	private PackageGraphNode getPackageGraphNode(final Package pkg) {
		PackageGraphNode node = this.packageItems.get(pkg);
		if (node == null) {
			node = new PackageGraphNode(this);
			node.setPkg(pkg);
			this.packageItems.put(pkg, node);
		}
		return node;
	}

	public void addPackage(final Package pkg, final int x, final int y) {
		PackageGraphNode n = new PackageGraphNode(this);
		n.setPkg(pkg);
		Point p = this.toControl(x, y);
		n.setLocation(p.x, p.y);

	}

}
