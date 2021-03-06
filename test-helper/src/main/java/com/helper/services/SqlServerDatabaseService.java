package com.helper.services;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.helper.enumeration.PropertiesHandlerEnum;
import com.helper.exception.HelperException;
import com.helper.factory.PropertiesHandlerFactory;
import com.helper.handler.PropertiesHandler;
import com.helper.util.PasswordUtil;

public class SqlServerDatabaseService extends DatabaseService{

	public SqlServerDatabaseService(String application) {
		super(application);
	}

	public SqlServerDatabaseService(PropertiesHandlerEnum propertiesManagerEnum, String application) {
		super(propertiesManagerEnum, application);
	}

	@Override
	public void establishConnection() {
		PropertiesHandler propManager = PropertiesHandlerFactory.getPropertiesHandler(propertiesManagerEnum, application);
		StringBuilder url = new StringBuilder();
		url.append("jdbc:sqlserver://");
		url.append(propManager.getHost());
		url.append(":");
		url.append(propManager.getPort());
		url.append(";databaseName=");
		url.append(propManager.getDatabase());
		
		String username = propManager.getUsername();
		String password = PasswordUtil.getPlainPassword(propManager);
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			connection = DriverManager.getConnection(url.toString(), username, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			throw new HelperException("Error establishing DB connection", e);
		}
	}
}
