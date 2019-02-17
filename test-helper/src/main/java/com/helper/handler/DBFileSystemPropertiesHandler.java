package com.helper.handler;

import java.io.File;

public class DBFileSystemPropertiesHandler extends FileSystemPropertiesHandler{
	
	public DBFileSystemPropertiesHandler(String application) {
		super(application);
	}

	@Override
	public void loadProperties(String application) {
		StringBuilder builder = new StringBuilder();
		builder.append("resources");
		builder.append(File.separator);
		builder.append("connection");
		builder.append(File.separator);
		builder.append("db");
		builder.append(File.separator);
		builder.append(application);
		builder.append(File.separator);
		builder.append("connection.properties");
		super.loadProperties(builder.toString());
	}
}
