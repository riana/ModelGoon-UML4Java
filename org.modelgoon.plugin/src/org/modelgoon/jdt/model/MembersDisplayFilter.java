package org.modelgoon.jdt.model;

import java.util.HashSet;
import java.util.Set;



public class MembersDisplayFilter {

	protected final Set<Visibility> acceptedVisibilities = new HashSet<Visibility>();

	private boolean staticAccepted = false;

	public MembersDisplayFilter() {
		setPublicAccepted(true);
		setPrivateAccepted(true);
		setProtectedAccepted(true);
		setDefaultAccepted(true);
	}

	public void setPublicAccepted(final boolean accepted) {
		if (accepted) {
			this.acceptedVisibilities.add(Visibility.PUBLIC);
		} else {
			this.acceptedVisibilities.remove(Visibility.PUBLIC);
		}
	}

	public boolean isPublicAccepted() {
		return this.acceptedVisibilities.contains(Visibility.PUBLIC);
	}

	public void setPrivateAccepted(final boolean accepted) {
		if (accepted) {
			this.acceptedVisibilities.add(Visibility.PRIVATE);
		} else {
			this.acceptedVisibilities.remove(Visibility.PRIVATE);
		}
	}

	public boolean isPrivateAccepted() {
		return this.acceptedVisibilities.contains(Visibility.PRIVATE);
	}

	public void setProtectedAccepted(final boolean accepted) {
		if (accepted) {
			this.acceptedVisibilities.add(Visibility.PROTECTED);
		} else {
			this.acceptedVisibilities.remove(Visibility.PROTECTED);
		}
	}

	public boolean isProtectedAccepted() {
		return this.acceptedVisibilities.contains(Visibility.PROTECTED);
	}

	public void setDefaultAccepted(final boolean accepted) {
		if (accepted) {
			this.acceptedVisibilities.add(Visibility.DEFAULT);
		} else {
			this.acceptedVisibilities.remove(Visibility.DEFAULT);
		}
	}

	public boolean isDefaultAccepted() {
		return this.acceptedVisibilities.contains(Visibility.DEFAULT);
	}

	public void setStaticAccepted(final boolean staticAccepted) {
		this.staticAccepted = staticAccepted;
	}

	public boolean isStaticAccepted() {
		return this.staticAccepted;
	}

	public boolean accept(final StructuralFeature structuralFeature) {
		boolean filterStatic = true;
		filterStatic = !this.staticAccepted && structuralFeature.isStatic();
		return this.acceptedVisibilities.contains(structuralFeature
				.getVisibility()) && !filterStatic;
	}

	public void setValue(final MembersDisplayFilter methodDisplayFilter) {
		this.acceptedVisibilities.clear();
		this.acceptedVisibilities
				.addAll(methodDisplayFilter.acceptedVisibilities);
		this.staticAccepted = methodDisplayFilter.isStaticAccepted();
	}

}
