package org.modelgoon.dao;

public interface DAO {

	public <T> T loadData(String name) throws DAOException;

	public <T> void saveData(T data, String name) throws DAOException;

}
