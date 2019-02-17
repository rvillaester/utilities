package com.helper.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.helper.DBRowColumn;
import com.helper.enumeration.PropertiesHandlerEnum;
import com.helper.exception.HelperException;
import org.apache.commons.collections.CollectionUtils;

public abstract class DatabaseService extends RemoteConnectionService{
	
	protected Connection connection;
	
	protected DatabaseService(String application){
		this(PropertiesHandlerEnum.DB_FILESYSTEM, application);
	}
	
	protected DatabaseService(PropertiesHandlerEnum propertiesManagerEnum, String application){
		super(propertiesManagerEnum, application);
	}
	
	public int executeUpdate(String sql){
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new HelperException("Error executing query: " + sql, e);
		} finally {
			close(statement, null);
		}
	}
	
	public List<DBRowColumn> executeQuery(String sql){
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			return convertResultSetToList(resultSet);
		} catch (SQLException e) {
			throw new HelperException("Error executing query: " + sql, e);
		} finally {
			close(statement, resultSet);
		}
	}
	
	public DBRowColumn executeSingleResultQuery(String sql){
		List<DBRowColumn> results = executeQuery(sql);
		if(CollectionUtils.isNotEmpty(results)){
			return results.get(0);
		}
		return null;
	}
	
	public String getResultAsString(String sql){
		Object result = getSingleValueResult(sql);
		if(result != null){
			return (String) result;
		}
		
		return null;
	}
	
	public Integer getResultAsInt(String sql){
		Object result = getSingleValueResult(sql);
		if(result != null){
			return (int) result;
		}
		
		return null;
	}
	
	public Date getResultAsDate(String sql){
		Object result = getSingleValueResult(sql);
		if(result != null){
			return (Date) result;
		}
		
		return null;
	}
	
	public BigDecimal getResultAsBigDecimal(String sql){
		Object result = getSingleValueResult(sql);
		if(result != null){
			return (BigDecimal) result;
		}
		
		return null;
	}
	
	public Object getSingleValueResult(String sql){
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getObject(1);
		} catch (SQLException e) {
			throw new HelperException("Error executing query: " + sql, e);
		} finally {
			close(statement, resultSet);
		}
	}
	
	private void close(Statement statement, ResultSet resultSet){
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				//do nothing
			}
		}
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				//do nothing
			}
		}
	}
	
	private List<DBRowColumn> convertResultSetToList(ResultSet resultSet) throws SQLException{
		ResultSetMetaData md = resultSet.getMetaData();
	    int columns = md.getColumnCount();
	    List<DBRowColumn> list = new ArrayList<>();

	    while (resultSet.next()) {
	    	DBRowColumn row = new DBRowColumn();
	        for(int i=1; i<=columns; ++i) {
	            row.put(md.getColumnLabel(i), resultSet.getObject(i));
	        }
	        list.add(row);
	    }
	    return list;
	}

	@Override
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new HelperException("Error closing DB connection", e);
		}
	}
}
