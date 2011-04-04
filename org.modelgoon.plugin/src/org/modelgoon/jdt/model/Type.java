package org.modelgoon.jdt.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.modelgoon.core.NamedModelElement;

public class Type extends NamedModelElement {

	String typeQualifiedName;

	boolean isArray = false;

	boolean isPrimitive = false;

	boolean isCollection = false;

	List<Type> parametersTypes = new ArrayList<Type>();

	private final static List<String> COLLECTION_TYPES = new ArrayList<String>();

	static {
		Type.COLLECTION_TYPES.add("java.util.List");
		Type.COLLECTION_TYPES.add("java.util.Vector");
		Type.COLLECTION_TYPES.add("java.util.ArrayList");
		Type.COLLECTION_TYPES.add("java.util.Set");
		Type.COLLECTION_TYPES.add("java.util.HashSet");
		Type.COLLECTION_TYPES.add("java.util.Map");
		Type.COLLECTION_TYPES.add("java.util.HashMap");
	}

	public Type() {
		super();
	}

	private void update(final ITypeBinding typeBinding) {
		this.isPrimitive = typeBinding.isPrimitive();

		this.typeQualifiedName = typeBinding.getQualifiedName();
		String simpleName = typeBinding.getName();
		setName(simpleName);

		this.isArray = typeBinding.isArray();

		this.parametersTypes.clear();
		for (ITypeBinding typeArgument : typeBinding.getTypeArguments()) {
			Type parameterType = new Type();
			parameterType.update(typeArgument);
			this.parametersTypes.add(parameterType);
		}
	}

	public void update(final org.eclipse.jdt.core.dom.Type type) {
		ITypeBinding typeBinding = type.resolveBinding();
		if (typeBinding != null) {
			update(typeBinding);
		} else {
			setName(type.toString());
			this.typeQualifiedName = type.toString();
			this.isArray = type.isArrayType();
			this.isPrimitive = type.isPrimitiveType();
		}
	}

	public boolean isArray() {
		return this.isArray;
	}

	public boolean isPrimitive() {
		return this.isPrimitive;
	}

	public boolean isCollection() {
		for (String collectionType : Type.COLLECTION_TYPES) {
			if (this.typeQualifiedName.startsWith(collectionType)) {
				return true;
			}
		}
		return false;
	}

	public String getQualifiedName() {
		return this.typeQualifiedName;
	}

	public Type getLastEnclosedType() {
		return this.parametersTypes.get(this.parametersTypes.size() - 1);
	}

	public boolean hasParameterTypes() {
		return !this.parametersTypes.isEmpty();
	}

}
