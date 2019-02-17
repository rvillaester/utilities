package com.helper.handler;

import java.io.File;

public class DBClasspathPropertiesHandler extends ClasspathPropertiesHandler{
	
	public DBClasspathPropertiesHandler(String application) {
		super(application);
	}
	
	@Override
	public void loadProperties(String application) {
		StringBuilder builder = new StringBuilder();
		builder.append(File.separator);
		builder.append("db");
		builder.append(File.separator);
		builder.append(application);
		builder.append(File.separator);
		builder.append("connection.properties");
		super.loadProperties(builder.toString());
	}

}
