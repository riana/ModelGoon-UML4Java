package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

public class CommunicationClass extends ClassModel {

	@Override
	public List<Relationship> getOutgoingRelationships() {
		List<Relationship> outgoingRelationships = new ArrayList<Relationship>();
		outgoingRelationships.addAll(this.umlClassBuilder
				.getCommunicationRelationships());
		return outgoingRelationships;
	}

	@Override
	public List<Relationship> getIncomingRelationships() {
		List<Relationship> relationships = new ArrayList<Relationship>();
		relationships.addAll(this.umlClassBuilder
				.getIncomingCommunicationRelationships());
		return relationships;
	}

}
