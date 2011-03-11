package org.modelgoon.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLContext;

public class CastorDAO implements DAO {

	XMLContext context;

	Mapping mapping;

	public CastorDAO() {
		this.context = new XMLContext();
		this.mapping = new Mapping();
		try {
			this.context.addMapping(this.mapping);
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addMapping(final String path) {
		try {
			this.mapping.loadMapping(getClass().getClassLoader().getResource(
					path));
			this.context.addMapping(this.mapping);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public <T> T loadData(final String name) throws DAOException {
		try {
			Unmarshaller unmarshaller = this.context.createUnmarshaller();
			unmarshaller.setValidation(false);
			unmarshaller.setWhitespacePreserve(true);
			return (T) unmarshaller.unmarshal(new FileReader(name));

		} catch (Exception e) {
			throw new DAOException("Error loading data", e);
		}
	}

	public <T> void saveData(final T data, final String name)
			throws DAOException {
		try {
			Marshaller marshaller = this.context.createMarshaller();
			marshaller.setValidation(false);
			marshaller.setProperty("org.exolab.castor.indent", "true");
			marshaller.setWriter(new FileWriter(name));
			marshaller.marshal(data);
		} catch (Exception e) {
			throw new DAOException("Error saving data", e);
		}

	}

}
