package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class UmlClassBuilder extends ASTVisitor {

	ClassModel umlClass;

	ClassDiagram diagram;

	String superClass = null;

	boolean isSingleton = false;

	boolean isInterface = false;

	boolean isEnum = false;

	boolean isAbstract = false;

	boolean isInternal = false;

	List<String> implementedInterfaces = new ArrayList<String>();

	Map<String, Method> methods = new HashMap<String, Method>();

	Map<String, Field> fields = new HashMap<String, Field>();

	Map<Field, AssociationRelationShip> associationRelationShips = new HashMap<Field, AssociationRelationShip>();

	Map<String, ExtensionRelationShip> extensionRelationShips = new HashMap<String, ExtensionRelationShip>();

	Map<String, CommunicationRelationship> communicationRelationShips = new HashMap<String, CommunicationRelationship>();

	private transient List<String> foundMethods = new ArrayList<String>();

	private transient List<String> foundAttributes = new ArrayList<String>();

	List<AssociationRelationShip> incomingAssociationRelationShips = new ArrayList<AssociationRelationShip>();

	List<ExtensionRelationShip> incomingExtensionRelationship = new ArrayList<ExtensionRelationShip>();

	List<CommunicationRelationship> incomingCommunicationRelationships = new ArrayList<CommunicationRelationship>();

	public UmlClassBuilder(final ClassModel umlClass) {
		super();
		this.umlClass = umlClass;
	}

	public void setDiagram(final ClassDiagram diagram) {
		this.diagram = diagram;
	}

	public static String getTypeName(final org.eclipse.jdt.core.dom.Type type) {
		String name = type.toString();
		ITypeBinding typeBinding = type.resolveBinding();
		if (typeBinding != null) {
			name = typeBinding.getQualifiedName();
		}
		return name;
	}

	private void startParsing() {
		this.isSingleton = false;
		this.superClass = null;
		this.foundMethods.clear();
		this.foundAttributes.clear();
		this.implementedInterfaces.clear();
		for (CommunicationRelationship communicationRelationship : this.communicationRelationShips
				.values()) {
			communicationRelationship.clearMessages();
		}

	}

	private void stopParsing() {
		// Clean removed Fields
		Iterator<Entry<String, Field>> fieldEntriesIterator = this.fields
				.entrySet().iterator();
		while (fieldEntriesIterator.hasNext()) {
			Map.Entry<String, Field> entry = fieldEntriesIterator.next();
			if (!this.foundAttributes.contains(entry.getKey())) {
				fieldEntriesIterator.remove();
				Field removedField = entry.getValue();
				if (removedField.isAssociation()) {
					removeAssociationRelationship(removedField);
				}
			}
		}

		// Clean removed Methods
		Iterator<Entry<String, Method>> it = this.methods.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, org.modelgoon.classdiagram.model.Method> entry = it
					.next();
			if (!this.foundMethods.contains(entry.getKey())) {
				it.remove();
			}
		}
		consolidateSupertypes();
	}

	@Override
	public boolean visit(final EnumDeclaration node) {
		String currentTypeName = node.resolveBinding().getQualifiedName();
		boolean found = currentTypeName.equals(this.umlClass.getName());
		if (found) {
			startParsing();
			try {
				IType javaType = this.umlClass.getJavaElement();
				this.isEnum = javaType.isEnum();
				if (this.isEnum) {
					for (IField field : javaType.getFields()) {
						if (field.isEnumConstant()) {
							String literal = field.getElementName();
							this.foundAttributes.add(literal);
							Field structuralFeature = this.fields.get(literal);
							if (structuralFeature == null) {
								structuralFeature = new Field();
								structuralFeature.setName(literal);
								structuralFeature.setJavaElement(field);
								structuralFeature.setLiteral(true);
								structuralFeature
										.setDeclaringClass(this.umlClass);
								this.fields.put(literal, structuralFeature);
							}
						}
					}

					List superInterfaces = node.superInterfaceTypes();
					for (Object si : superInterfaces) {
						if (si instanceof org.eclipse.jdt.core.dom.Type) {
							org.eclipse.jdt.core.dom.Type superInterface = (org.eclipse.jdt.core.dom.Type) si;
							this.implementedInterfaces.add(UmlClassBuilder
									.getTypeName(superInterface));
						}
					}

					for (Object object : node.bodyDeclarations()) {
						if (object instanceof FieldDeclaration) {
							FieldDeclaration fieldDeclaration = (FieldDeclaration) object;
							updateFieldDeclaration(fieldDeclaration);
						} else if (object instanceof MethodDeclaration) {
							MethodDeclaration methodDeclaration = (MethodDeclaration) object;
							updateMethodDeclaration(methodDeclaration);
						}
					}
				}
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopParsing();
		}
		return true;
	}

	@Override
	public boolean visit(final TypeDeclaration node) {
		String currentTypeName = node.resolveBinding().getQualifiedName();
		boolean found = currentTypeName.equals(this.umlClass.getName());
		if (found) {
			startParsing();
			try {

				IType javaType = this.umlClass.getJavaElement();

				this.isInternal = javaType.getDeclaringType() != null;
				this.isInterface = javaType.isInterface();
				this.isAbstract = Flags.isAbstract(javaType.getFlags());

				// Update SuperTypes
				org.eclipse.jdt.core.dom.Type superClassType = node
						.getSuperclassType();
				if (superClassType != null) {
					this.superClass = UmlClassBuilder
							.getTypeName(superClassType);
				}

				List superInterfaces = node.superInterfaceTypes();
				for (Object si : superInterfaces) {
					if (si instanceof org.eclipse.jdt.core.dom.Type) {
						org.eclipse.jdt.core.dom.Type superInterface = (org.eclipse.jdt.core.dom.Type) si;
						this.implementedInterfaces.add(UmlClassBuilder
								.getTypeName(superInterface));
					}
				}

				for (FieldDeclaration fieldDeclaration : node.getFields()) {
					updateFieldDeclaration(fieldDeclaration);
				}

				for (MethodDeclaration methodDeclaration : node.getMethods()) {
					updateMethodDeclaration(methodDeclaration);
				}

				// System.out.println("Checking required methods : "
				// + this.umlClass.getName());
				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(final MethodInvocation node) {
						updateCommunicationsRelationship(node);
						return false;
					}
				});
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stopParsing();
		}
		return !found;
	}

	private void checkIfMember(final MethodInvocation node,
			final String message, final String destination) {
		Expression exp = node.getExpression();
		ITypeBinding invocationType = null;
		IMember declaringMember = null;

		if (exp instanceof SimpleName) {
			SimpleName name = (SimpleName) exp;
			IBinding binding = name.resolveBinding();
			if ((binding != null) && (binding instanceof IVariableBinding)) {
				IVariableBinding varBinding = (IVariableBinding) binding;
				// invocationType =
				// varBinding.getType();
				if (varBinding.getDeclaringClass() != null) {
					declaringMember = (IMember) varBinding.getJavaElement();
					System.out.println("\tuses : " + destination + " => "
							+ message + " on "
							+ declaringMember.getElementName());

				}
				// if (declaringMember == null) {
				// if (varBinding.getDeclaringMethod()
				// != null) {
				// if (varBinding
				// .getDeclaringMethod()
				// .getDeclaringClass() != null) {
				// declaringMember = (IMember)
				// varBinding
				// .getDeclaringMethod()
				// .getDeclaringClass()
				// .getJavaElement();
				// }
				// }
				// }
			}
		}
	}

	private CommunicationRelationship getCommunicationRelationship(
			final String destination) {
		if (destination.equals(this.umlClass.getName())) {
			return null;
		}
		CommunicationRelationship communicationRelationship = this.communicationRelationShips
				.get(destination);
		ClassModel destClass = this.diagram.resolveType(destination);
		if (destClass != null) {
			if (communicationRelationship == null) {
				communicationRelationship = new CommunicationRelationship();
				communicationRelationship.setSource(this.umlClass);
				communicationRelationship.setDestination(destClass);
				destClass.addIncomingRelationship(communicationRelationship);
				this.communicationRelationShips.put(destination,
						communicationRelationship);
			}
		} else if (communicationRelationship != null) {
			this.communicationRelationShips.remove(destClass);
			communicationRelationship = null;
		}

		return communicationRelationship;
	}

	private void updateCommunicationsRelationship(final MethodInvocation node) {
		IMethodBinding methodBinding = node.resolveMethodBinding();
		if (methodBinding != null) {
			String destination = methodBinding.getDeclaringClass()
					.getQualifiedName();
			CommunicationRelationship communicationRelationship = getCommunicationRelationship(destination);
			if (communicationRelationship != null) {
				communicationRelationship.addMessage(methodBinding.getName());
			}
		}
	}

	public void addExtensionRelationship(final ClassModel superClass) {
		if ((superClass != null)
				&& (!this.extensionRelationShips.containsKey(superClass
						.getName()))) {
			ExtensionRelationShip extensionRelationship = new ExtensionRelationShip();
			extensionRelationship.setSource(this.umlClass);
			extensionRelationship.setDestination(superClass);
			superClass.addIncomingRelationship(extensionRelationship);
			this.extensionRelationShips.put(superClass.getName(),
					extensionRelationship);
		}
	}

	private void updateMethodDeclaration(final MethodDeclaration node) {

		String method = node.getName().toString();
		this.foundMethods.add(method);

		Method m = this.methods.get(method);
		if (m == null) {
			m = new Method();
			IMethodBinding methodBinding = node.resolveBinding();
			if (methodBinding != null) {
				IJavaElement javaElement = methodBinding.getJavaElement();
				if (javaElement instanceof IMethod) {
					m.setJavaElement((IMethod) javaElement);

				}
			}
			m.setDeclaringClass(this.umlClass);
			this.methods.put(method, m);
		}
		m.update(node);
	}

	private void consolidateSupertypes() {
		List<String> foundSupertypes = new ArrayList<String>();
		String superclass = this.superClass;
		if (superclass != null) {
			if (!this.extensionRelationShips.containsKey(superclass)) {
				ClassModel supertype = this.diagram.resolveType(superclass);
				if (supertype != null) {
					addExtensionRelationship(supertype);
				}
			}
			foundSupertypes.add(superclass);
		}

		List<String> superInterfaces = getImplementedInterfaces();
		for (String superInterfaceName : superInterfaces) {
			if (!this.extensionRelationShips.containsKey(superInterfaceName)) {
				ClassModel superInterface = this.diagram
						.resolveType(superInterfaceName);
				if (superInterface != null) {
					addExtensionRelationship(superInterface);

				}
			}
			foundSupertypes.add(superInterfaceName);
		}

		List<String> currentSupertypes = new ArrayList<String>(
				this.extensionRelationShips.keySet());
		for (String supertype : currentSupertypes) {
			if (!foundSupertypes.contains(supertype)) {
				ExtensionRelationShip removedRelationShip = this.extensionRelationShips
						.remove(supertype);
				removedRelationShip.getDestination()
						.removeIncomingRelationship(removedRelationShip);
				removOutgoingeRelationship(removedRelationShip);
			}
		}
	}

	private void updateFieldDeclaration(final FieldDeclaration node) {
		String simpleName = null;
		if (node != null) {
			String declaredName = node.fragments().get(0).toString();
			if (declaredName.lastIndexOf('=') != -1) {
				declaredName = declaredName.substring(0,
						declaredName.indexOf('='));
			}
			simpleName = declaredName;
		}

		this.foundAttributes.add(simpleName);
		Field structuralFeature = this.fields.get(simpleName);
		if (structuralFeature == null) {
			structuralFeature = new Field();
			structuralFeature.setDeclaringClass(this.umlClass);
			IField iField = this.umlClass.getJavaElement().getField(simpleName);
			structuralFeature.setJavaElement(iField);
			this.fields.put(simpleName, structuralFeature);
		}

		structuralFeature.setDeclaringClass(this.umlClass);
		structuralFeature.setName(simpleName);
		structuralFeature.update(node);
		this.isSingleton = structuralFeature.isSingleton();

		// Check AssociationShip
		addAssociationRelationship(structuralFeature);
	}

	public void addAssociationRelationship(final Field field) {
		AssociationRelationShip associationRelationship = this.associationRelationShips
				.get(field);
		if (associationRelationship == null) {
			Type type = field.getType();
			if (!type.isPrimitive()) {
				ClassModel associatedClass = this.diagram.resolveType(type
						.getQualifiedName());

				if (type.isCollection() && type.hasParameterTypes()) {
					associatedClass = this.diagram.resolveType(type
							.getLastEnclosedType().getQualifiedName());
				} else if (type.isArray()) {
					String typeName = type.getQualifiedName();
					typeName = typeName.substring(0, typeName.lastIndexOf("["));
					associatedClass = this.diagram.resolveType(typeName);

				}

				field.setAssociation((associatedClass != null)
						&& !field.isSingleton());
				if (field.isAssociation()) {

					String multiplicity = type.isCollection() | type.isArray() ? "*"
							: "1";
					associationRelationship = new AssociationRelationShip();
					associationRelationship.setSource(this.umlClass);
					associationRelationship.setDestination(associatedClass);
					associatedClass
							.addIncomingRelationship(associationRelationship);
					this.associationRelationShips.put(field,
							associationRelationship);
					associationRelationship.setEndpointName(field.getName());
					associationRelationship.setContainment(false);
					associationRelationship.setMultiplicity(multiplicity);
				}
			}
		}
	}

	public void removeAssociationRelationship(final Field field) {
		AssociationRelationShip associationRelationship = this.associationRelationShips
				.get(field);
		if (associationRelationship != null) {
			this.associationRelationShips.remove(field);
			associationRelationship.getDestination()
					.removeIncomingRelationship(associationRelationship);
		}
	}

	public Collection<AssociationRelationShip> getAssociation() {
		return this.associationRelationShips.values();
	}

	public Collection<Method> getMethods() {
		return this.methods.values();
	}

	public Collection<Field> getFields() {
		return this.fields.values();
	}

	public List<String> getImplementedInterfaces() {
		return this.implementedInterfaces;
	}

	public String getSuperClass() {
		return this.superClass;
	}

	public boolean isSingleton() {
		return this.isSingleton;
	}

	public boolean isEnum() {
		return this.isEnum;
	}

	public boolean isAbstract() {
		return this.isAbstract;
	}

	public boolean isInterface() {
		return this.isInterface;
	}

	public boolean isInternal() {
		return this.isInternal;
	}

	public void removOutgoingeRelationship(final Relationship relationship) {
		Iterator<Entry<Field, AssociationRelationShip>> iterator = this.associationRelationShips
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<org.modelgoon.classdiagram.model.Field, org.modelgoon.classdiagram.model.AssociationRelationShip> entry = iterator
					.next();
			if (entry.getValue() == relationship) {
				iterator.remove();
			}
		}
		this.associationRelationShips.remove(relationship);

		this.extensionRelationShips.remove(relationship.getDestination()
				.getName());
		this.communicationRelationShips.remove(relationship.getDestination()
				.getName());
	}

	public void removeIncomingRelationship(final Relationship relationship) {
		if (relationship instanceof ExtensionRelationShip) {
			this.incomingExtensionRelationship.remove(relationship);
		} else if (relationship instanceof AssociationRelationShip) {
			this.incomingAssociationRelationShips.remove(relationship);
		} else if (relationship instanceof CommunicationRelationship) {
			this.incomingCommunicationRelationships.remove(relationship);
		}
	}

	public void addIncomingRelationship(final Relationship relationship) {
		if (relationship instanceof ExtensionRelationShip) {
			this.incomingExtensionRelationship
					.add((ExtensionRelationShip) relationship);
		} else if (relationship instanceof AssociationRelationShip) {
			this.incomingAssociationRelationShips
					.add((AssociationRelationShip) relationship);
		} else if (relationship instanceof CommunicationRelationship) {
			this.incomingCommunicationRelationships
					.add((CommunicationRelationship) relationship);
		}
	}

	public Collection<ExtensionRelationShip> getExtensionRelationShips() {
		return this.extensionRelationShips.values();
	}

	public List<AssociationRelationShip> getIncomingAssociationRelationShips() {
		return this.incomingAssociationRelationShips;
	}

	public List<CommunicationRelationship> getIncomingCommunicationRelationships() {
		return this.incomingCommunicationRelationships;
	}

	public Collection<CommunicationRelationship> getCommunicationRelationships() {
		return this.communicationRelationShips.values();
	}

	public List<ExtensionRelationShip> getIncomingExtensionRelationship() {
		return this.incomingExtensionRelationship;
	}

}
