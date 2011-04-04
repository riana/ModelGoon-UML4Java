package org.modelgoon.jdt.model;



public class MethodDisplayFilter extends MembersDisplayFilter {

	private boolean accessorsAccepted = false;

	private boolean constructorsAccepted = false;

	public MethodDisplayFilter() {
		setPrivateAccepted(false);
		setDefaultAccepted(false);
		setProtectedAccepted(false);
	}

	public void setAccessorsAccepted(final boolean accessorsAccepted) {
		this.accessorsAccepted = accessorsAccepted;
	}

	public boolean isAccessorsAccepted() {
		return this.accessorsAccepted;
	}

	public void setConstructorsAccepted(final boolean constructorsAccepted) {
		this.constructorsAccepted = constructorsAccepted;
	}

	public boolean isConstructorsAccepted() {
		return this.constructorsAccepted;
	}

	public boolean accept(final Method method) {
		if (method.isAccessor()) {
			return super.accept(method) && this.accessorsAccepted;
		}

		if (method.isConstructor()) {
			return super.accept(method) && this.constructorsAccepted;
		}

		return super.accept(method);
	}

	public void setValue(final MethodDisplayFilter methodDisplayFilter) {
		super.setValue(methodDisplayFilter);
		this.constructorsAccepted = methodDisplayFilter
				.isConstructorsAccepted();
		this.accessorsAccepted = methodDisplayFilter.isAccessorsAccepted();
	}

}
