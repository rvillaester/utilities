package com.helper.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.helper.exception.HelperException;

public class ShortSqlPropertiesHandler extends PropertiesHandler{
	
	public ShortSqlPropertiesHandler(){
		this("");
	}

	private ShortSqlPropertiesHandler(String propertiesPath) {
		super(propertiesPath);
	}

	@Override
	public void loadProperties(String propertiesPath) {
		propertiesPath = getFilePath();
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			throw new HelperException(e);
		}
	}
	
	private String getFilePath(){
		StringBuilder builder = new StringBuilder();
		builder.append("resources");
		builder.append(File.separator);
		builder.append("sql");
		builder.append(File.separator);
		builder.append("short-sql.query");
		return builder.toString();
	}
}
