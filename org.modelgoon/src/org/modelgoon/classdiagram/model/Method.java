package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class Method extends StructuralFeature<IMethod> {

	boolean accessor = false;

	boolean constructor = false;

	String returnType;

	List<String> parameterTypes = new ArrayList<String>();

	public Method() {
		super();
	}

	public boolean isAccessor() {
		return this.accessor;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getVisibilityString());
		buffer.append(getName());
		buffer.append("(");
		for (int i = 0; i < this.parameterTypes.size(); i++) {
			String parametersType = this.parameterTypes.get(i);
			buffer.append(parametersType);
			if (i < this.parameterTypes.size() - 1) {
				buffer.append(", ");
			}

		}
		buffer.append(")");
		if (this.returnType != null) {
			buffer.append(":");
			buffer.append(this.returnType);
		}
		return buffer.toString();
	}

	public boolean isConstructor() {
		return this.constructor;
	}

	public void update(final MethodDeclaration node) {
		setFlags(node.getModifiers());
		String name = node.getName().toString();
		setName(name);
		this.accessor = name.startsWith("get") || name.startsWith("set")
				|| name.startsWith("is") || name.startsWith("add")
				|| name.startsWith("remove");

		this.constructor = name.startsWith(getDeclaringClass().getJavaElement()
				.getElementName());

		if (!this.constructor) {
			Type returnType = node.getReturnType2();
			if (returnType != null) {
				this.returnType = returnType.toString();
			}

			this.parameterTypes.clear();
			IMethodBinding methodBinding = node.resolveBinding();
			if (methodBinding != null) {
				for (ITypeBinding typeParameter : methodBinding
						.getParameterTypes()) {
					this.parameterTypes.add(typeParameter.getName());
				}
			}
		}

	}

}
