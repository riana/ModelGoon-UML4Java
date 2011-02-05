package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UmlClass extends ClassModel {

	public final static String FILTERS_PROPERTY = "Filters";

	MembersDisplayFilter attributesDisplayFilter = new MembersDisplayFilter();

	MethodDisplayFilter methodDisplayFilter = new MethodDisplayFilter();

	public UmlClass() {
		super();
	}

	public List<Method> getMethods() {
		List<Method> acceptedMethods = new ArrayList<Method>();
		for (Method method : this.umlClassBuilder.getMethods()) {
			if (this.methodDisplayFilter.accept(method)) {
				acceptedMethods.add(method);
			}
		}
		Collections
				.sort(acceptedMethods, StructuralFeatureComparator.SINGLETON);
		return acceptedMethods;
	}

	public List<Field> getAttributes() {
		List<Field> attributes = new ArrayList<Field>();
		for (Field field : this.umlClassBuilder.getFields()) {
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
		firePropertyChange(UmlClass.FILTERS_PROPERTY);
	}

	public MembersDisplayFilter getAttributesDisplayFilter() {
		return this.attributesDisplayFilter;
	}

	public void setMethodDisplayFilter(
			final MethodDisplayFilter methodDisplayFilter) {
		this.methodDisplayFilter = methodDisplayFilter;
		firePropertyChange(UmlClass.FILTERS_PROPERTY);
	}

	public MethodDisplayFilter getMethodDisplayFilter() {
		return this.methodDisplayFilter;
	}

	@Override
	public List<Relationship> getOutgoingRelationships() {
		List<Relationship> outgoingRelationships = new ArrayList<Relationship>();
		outgoingRelationships.addAll(this.umlClassBuilder
				.getExtensionRelationShips());
		outgoingRelationships.addAll(this.umlClassBuilder.getAssociation());
		return outgoingRelationships;
	}

	@Override
	public List<Relationship> getIncomingRelationships() {
		List<Relationship> relationships = new ArrayList<Relationship>();
		relationships.addAll(this.umlClassBuilder
				.getIncomingAssociationRelationShips());
		relationships.addAll(this.umlClassBuilder
				.getIncomingExtensionRelationship());
		return relationships;
	}

}
