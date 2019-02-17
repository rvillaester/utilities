package com.helper.services;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.helper.enumeration.PropertiesHandlerEnum;
import com.helper.exception.HelperException;
import com.helper.factory.PropertiesHandlerFactory;
import com.helper.handler.PropertiesHandler;
import com.helper.util.PasswordUtil;

public class OracleDatabaseService extends DatabaseService{

	public OracleDatabaseService(String application) {
		super(application);
	}

	public OracleDatabaseService(PropertiesHandlerEnum propertiesManagerEnum, String path) {
		super(propertiesManagerEnum, path);
	}

	@Override
	public void establishConnection() {
		PropertiesHandler propManager = PropertiesHandlerFactory.getPropertiesHandler(propertiesManagerEnum, application);
		StringBuilder url = new StringBuilder();
		url.append("jdbc:oracle:thin:@");
		url.append(propManager.getHost());
		url.append(":");
		url.append(propManager.getPort());
		url.append("/");
		url.append(propManager.getDatabase());
		
		String username = propManager.getUsername();
		String password = PasswordUtil.getPlainPassword(propManager);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			connection = DriverManager.getConnection(url.toString(), username, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			throw new HelperException("Error establishing DB connection", e);
		}
	}
}
