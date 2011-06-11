package org.modelgoon.jdt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
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
import org.modelgoon.core.NamedModelElement;

public class UMLClass extends NamedModelElement {

	String superClass = null;

	String qualifiedName = "";

	boolean isSingleton = false;

	boolean isInterface = false;

	boolean isEnum = false;

	boolean isAbstract = false;

	boolean isInternal = false;

	IType javaType;

	UMLModel diagram;

	MembersDisplayFilter attributesDisplayFilter = new MembersDisplayFilter();

	MethodDisplayFilter methodDisplayFilter = new MethodDisplayFilter();

	List<String> implementedInterfaces = new ArrayList<String>();

	Map<String, Method> methods = new HashMap<String, Method>();

	Map<String, Field> fields = new HashMap<String, Field>();

	Map<String, AssociationRelationShip> associationRelationShips = new HashMap<String, AssociationRelationShip>();

	Map<String, ExtensionRelationShip> extensionRelationShips = new HashMap<String, ExtensionRelationShip>();

	Map<String, CommunicationRelationship> communicationRelationShips = new HashMap<String, CommunicationRelationship>();

	private transient List<String> foundMethods = new ArrayList<String>();

	private transient List<String> foundAttributes = new ArrayList<String>();

	List<AssociationRelationShip> incomingAssociationRelationShips = new ArrayList<AssociationRelationShip>();

	List<ExtensionRelationShip> incomingExtensionRelationship = new ArrayList<ExtensionRelationShip>();

	List<CommunicationRelationship> incomingCommunicationRelationships = new ArrayList<CommunicationRelationship>();

	JDTClassParser jdtClassParser = new JDTClassParser();

	public UMLClass() {
		super();
	}

	public UMLClass(final String className) {
		super();
		this.qualifiedName = className;
	}

	public void setInterface(final boolean isInterface) {
		this.isInterface = isInterface;
	}

	public void setDiagram(final UMLModel diagram) {
		this.diagram = diagram;
	}

	public void setJavaType(final IType javaType) {
		this.javaType = javaType;
		if (javaType != null) {
			String fullyQualifiedName = javaType.getFullyQualifiedName();
			this.qualifiedName = fullyQualifiedName.replace("$", ".");
			setQualifiedName(this.qualifiedName);
		}
	}

	public IType getJavaType() {

		return this.javaType;
	}

	public String getPackageName() {
		return this.javaType.getPackageFragment().getElementName();
	}

	public void setQualifiedName(final String qualifiedName) {
		this.qualifiedName = qualifiedName;
		setName(qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1));
	}

	public String getQualifiedName() {
		return this.qualifiedName;
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
			Map.Entry<String, org.modelgoon.jdt.model.Method> entry = it.next();
			if (!this.foundMethods.contains(entry.getKey())) {
				it.remove();
			}
		}
		consolidateSupertypes();
	}

	public boolean updateTypeDeclaration(final TypeDeclaration node) {
		String currentTypeName = node.resolveBinding().getQualifiedName();
		boolean found = currentTypeName.equals(this.qualifiedName);
		if (found) {
			startParsing();
			try {

				this.isInternal = this.javaType.getDeclaringType() != null;
				this.isInterface = this.javaType.isInterface();
				this.isAbstract = Flags.isAbstract(this.javaType.getFlags());

				// Update SuperTypes
				org.eclipse.jdt.core.dom.Type superClassType = node
						.getSuperclassType();
				if (superClassType != null) {
					this.superClass = UMLClass.getTypeName(superClassType);
				}

				List superInterfaces = node.superInterfaceTypes();
				for (Object si : superInterfaces) {
					if (si instanceof org.eclipse.jdt.core.dom.Type) {
						org.eclipse.jdt.core.dom.Type superInterface = (org.eclipse.jdt.core.dom.Type) si;
						this.implementedInterfaces.add(UMLClass
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
		if (destination.equals(this.qualifiedName)) {
			return null;
		}
		CommunicationRelationship communicationRelationship = this.communicationRelationShips
				.get(destination);
		UMLClass destClass = this.diagram.resolveType(destination);
		if (destClass != null) {

			if (communicationRelationship == null) {
				communicationRelationship = new CommunicationRelationship();
				communicationRelationship.setSource(this);
				communicationRelationship.setDestination(destClass);
				destClass.addIncomingRelationship(communicationRelationship);
				this.communicationRelationShips.put(destination,
						communicationRelationship);
			} else {
				communicationRelationship.getDestination();
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

	public void addExtensionRelationship(final UMLClass superClass) {
		if ((superClass != null)
				&& (!this.extensionRelationShips.containsKey(superClass
						.getQualifiedName()))) {
			ExtensionRelationShip extensionRelationship = new ExtensionRelationShip();
			extensionRelationship.setSource(this);
			extensionRelationship.setDestination(superClass);
			superClass.addIncomingRelationship(extensionRelationship);
			this.extensionRelationShips.put(superClass.getQualifiedName(),
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
					m.setJdtMethod((IMethod) javaElement);

				}
			}
			m.setDeclaringClass(this);
			this.methods.put(method, m);
		}
		m.update(node);
	}

	private void consolidateSupertypes() {
		List<String> foundSupertypes = new ArrayList<String>();
		String superclass = this.superClass;
		if (superclass != null) {
			if (!this.extensionRelationShips.containsKey(superclass)) {
				UMLClass supertype = this.diagram.resolveType(superclass);
				if (supertype != null) {
					addExtensionRelationship(supertype);
				}
			}
			foundSupertypes.add(superclass);
		}

		List<String> superInterfaces = getImplementedInterfaces();
		for (String superInterfaceName : superInterfaces) {
			if (!this.extensionRelationShips.containsKey(superInterfaceName)) {
				UMLClass superInterface = this.diagram
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
				removeOutgoingRelationship(removedRelationShip);
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
			structuralFeature.setDeclaringClass(this);
			IField iField = this.javaType.getField(simpleName);
			structuralFeature.setJdtField(iField);
			this.fields.put(simpleName, structuralFeature);
		}

		structuralFeature.setDeclaringClass(this);
		structuralFeature.setName(simpleName);
		structuralFeature.update(node);
		this.isSingleton = structuralFeature.isSingleton();

		// Check AssociationShip
		addAssociationRelationship(structuralFeature);
	}

	public void addAssociationRelationship(final Field field) {

		Type type = field.getType();
		if (!type.isPrimitive()) {

			UMLClass associatedClass = this.diagram.resolveType(type
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
				AssociationRelationShip associationRelationship = this.associationRelationShips
						.get(field.getName());
				if (associationRelationship == null) {
					associationRelationship = new AssociationRelationShip();
					associationRelationship.setSource(this);
					this.associationRelationShips.put(field.getName(),
							associationRelationship);
				}

				associationRelationship.setDestination(associatedClass);
				associatedClass
						.addIncomingRelationship(associationRelationship);

				associationRelationship.setEndpointName(field.getName());

				associationRelationship.setMultiplicity(multiplicity);

			}
		}

	}

	public void removeAssociationRelationship(final Field field) {
		AssociationRelationShip associationRelationship = this.associationRelationShips
				.get(field.getName());
		if (associationRelationship != null) {
			this.associationRelationShips.remove(field);
			associationRelationship.getDestination()
					.removeIncomingRelationship(associationRelationship);
		}
	}

	public Collection<AssociationRelationShip> getAssociationRelationships() {
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

	public void removeOutgoingRelationship(final Relationship relationship) {
		Iterator<Entry<String, AssociationRelationShip>> iterator = this.associationRelationShips
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, AssociationRelationShip> entry = iterator.next();
			if (entry.getValue() == relationship) {
				iterator.remove();
			}
		}
		this.associationRelationShips.remove(relationship);

		this.extensionRelationShips.remove(relationship.getDestination()
				.getQualifiedName());
		this.communicationRelationShips.remove(relationship.getDestination()
				.getQualifiedName());
		propertyChanged();
	}

	public UMLClass resolveUMLClass(final String umlClassName) {
		return this.diagram.getUmlClass(umlClassName);
	}

	public void addRelationship(final Relationship relationship) {
		relationship.setSource(this);
		if (relationship instanceof ExtensionRelationShip) {
			this.extensionRelationShips.put(
					relationship.getDestinationClassName(),
					(ExtensionRelationShip) relationship);
		} else if (relationship instanceof AssociationRelationShip) {
			AssociationRelationShip associationRelationShip = (AssociationRelationShip) relationship;
			this.associationRelationShips.put(
					associationRelationShip.getEndpointName(),
					associationRelationShip);
		} else if (relationship instanceof CommunicationRelationship) {
			this.communicationRelationShips.put(
					relationship.getDestinationClassName(),
					(CommunicationRelationship) relationship);
		}
		propertyChanged();
	}

	public void removeIncomingRelationship(final Relationship relationship) {
		if (relationship instanceof ExtensionRelationShip) {
			this.incomingExtensionRelationship.remove(relationship);
		} else if (relationship instanceof AssociationRelationShip) {
			this.incomingAssociationRelationShips.remove(relationship);
		} else if (relationship instanceof CommunicationRelationship) {
			this.incomingCommunicationRelationships.remove(relationship);
		}
		propertyChanged();
	}

	public void addIncomingRelationship(final Relationship relationship) {
		if (relationship instanceof ExtensionRelationShip) {
			if (!this.incomingExtensionRelationship.contains(relationship)) {
				this.incomingExtensionRelationship
						.add((ExtensionRelationShip) relationship);
			}
		} else if (relationship instanceof AssociationRelationShip) {
			if (!this.incomingAssociationRelationShips.contains(relationship)) {
				this.incomingAssociationRelationShips
						.add((AssociationRelationShip) relationship);
			}
		} else if (relationship instanceof CommunicationRelationship) {
			if (!this.incomingCommunicationRelationships.contains(relationship)) {
				this.incomingCommunicationRelationships
						.add((CommunicationRelationship) relationship);
			}
		}
		propertyChanged();
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

	public void consolidate() {
		String qualifiedName = getName();
		qualifiedName = qualifiedName.replace("$", ".");

		if ((this.jdtClassParser != null) && (this.javaType != null)) {
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(this.javaType.getCompilationUnit());
			parser.setResolveBindings(true);
			ASTNode root = parser.createAST(null);
			root.accept(this.jdtClassParser);

			Iterator<Entry<String, AssociationRelationShip>> iterator = this.associationRelationShips
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, AssociationRelationShip> entry = iterator.next();
				String epName = entry.getKey();
				if (!this.fields.containsKey(epName)) {
					AssociationRelationShip relationship = entry.getValue();
					relationship.getDestination().removeIncomingRelationship(
							relationship);
					iterator.remove();
				}
			}
		}
		propertyChanged();
	}

	public void updateEnumDeclaration(final EnumDeclaration node) {
		String currentTypeName = node.resolveBinding().getQualifiedName();
		boolean found = currentTypeName.equals(this.qualifiedName);
		if (found) {
			startParsing();
			try {
				this.isEnum = this.javaType.isEnum();
				if (this.isEnum) {
					for (IField field : this.javaType.getFields()) {
						if (field.isEnumConstant()) {
							String literal = field.getElementName();
							this.foundAttributes.add(literal);
							Field structuralFeature = this.fields.get(literal);
							if (structuralFeature == null) {
								structuralFeature = new Field();
								structuralFeature.setName(literal);
								structuralFeature.setLiteral(true);
								structuralFeature.setDeclaringClass(this);
								structuralFeature.setJdtField(field);
								this.fields.put(literal, structuralFeature);
							}
						}
					}

					List superInterfaces = node.superInterfaceTypes();
					for (Object si : superInterfaces) {
						if (si instanceof org.eclipse.jdt.core.dom.Type) {
							org.eclipse.jdt.core.dom.Type superInterface = (org.eclipse.jdt.core.dom.Type) si;
							this.implementedInterfaces.add(UMLClass
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
	}

	private class JDTClassParser extends ASTVisitor {

		public JDTClassParser() {

		}

		@Override
		public boolean visit(final EnumDeclaration node) {
			updateEnumDeclaration(node);
			return true;
		}

		@Override
		public boolean visit(final TypeDeclaration node) {
			return updateTypeDeclaration(node);
		}

	}

	public List<Method> getVisibleMethods() {
		List<Method> acceptedMethods = new ArrayList<Method>();
		for (Method method : getMethods()) {
			if (this.methodDisplayFilter.accept(method)) {
				acceptedMethods.add(method);
			}
		}
		Collections
				.sort(acceptedMethods, StructuralFeatureComparator.SINGLETON);
		return acceptedMethods;
	}

	public List<Relationship> getRelationships() {
		List<Relationship> relationships = new ArrayList<Relationship>();
		relationships.addAll(this.extensionRelationShips.values());
		relationships.addAll(this.associationRelationShips.values());
		relationships.addAll(this.communicationRelationShips.values());
		return relationships;
	}

	public List<Field> getVisibleAttributes() {
		List<Field> attributes = new ArrayList<Field>();
		for (Field field : getFields()) {
			if (!field.isAssociation() && !field.isSingleton()
					&& this.attributesDisplayFilter.accept(field)) {
				attributes.add(field);
			}
		}

		Collections.sort(attributes, StructuralFeatureComparator.SINGLETON);
		return attributes;
	}

	public void setAttributesDisplayFilter(
			final MembersDisplayFilter attributesDisplayFilter) {
		this.attributesDisplayFilter = attributesDisplayFilter;
		propertyChanged();
	}

	public MembersDisplayFilter getAttributesDisplayFilter() {
		return this.attributesDisplayFilter;
	}

	public void setMethodDisplayFilter(
			final MethodDisplayFilter methodDisplayFilter) {
		this.methodDisplayFilter = methodDisplayFilter;
		propertyChanged();
	}

	public MethodDisplayFilter getMethodDisplayFilter() {
		return this.methodDisplayFilter;
	}

	public void removeFromDiagram() {
		for (Relationship relationship : this.associationRelationShips.values()) {
			relationship.getDestination().removeIncomingRelationship(
					relationship);
		}

		for (Relationship relationship : this.extensionRelationShips.values()) {
			relationship.getDestination().removeIncomingRelationship(
					relationship);
		}

		for (Relationship relationship : this.communicationRelationShips
				.values()) {
			relationship.getDestination().removeIncomingRelationship(
					relationship);
		}

		for (Relationship relationship : this.incomingAssociationRelationShips) {
			relationship.getSource().removeOutgoingRelationship(relationship);
		}

		for (Relationship relationship : this.incomingExtensionRelationship) {
			relationship.getSource().removeOutgoingRelationship(relationship);
		}

		for (Relationship relationship : this.incomingCommunicationRelationships) {
			relationship.getSource().removeOutgoingRelationship(relationship);
		}

		this.associationRelationShips.clear();
		this.extensionRelationShips.clear();
		this.communicationRelationShips.clear();
		this.incomingAssociationRelationShips.clear();
		this.incomingExtensionRelationship.clear();
		this.incomingCommunicationRelationships.clear();
		this.diagram.removeClass(this);
	}

}
