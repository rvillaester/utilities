package com.helper.handler;

import java.io.File;

public class SoapFileSystemPropertiesHandler extends FileSystemPropertiesHandler{
	
	private String application;
	
	public SoapFileSystemPropertiesHandler(String application) {
		super(application);
		this.application = application;
	}

	@Override
	public void loadProperties(String application) {
		StringBuilder builder = new StringBuilder();
		builder.append("resources");
		builder.append(File.separator);
		builder.append("connection");
		builder.append(File.separator);
		builder.append("soap");
		builder.append(File.separator);
		builder.append(application);
		builder.append(File.separator);
		builder.append("connection.properties");
		super.loadProperties(builder.toString());
	}

	public String getTrustore(){
		StringBuilder builder = new StringBuilder();
		builder.append("resources");
		builder.append(File.separator);
		builder.append("connection");
		builder.append(File.separator);
		builder.append("soap");
		builder.append(File.separator);
		builder.append(application);
		builder.append(File.separator);
		String value = getProperty("truststore");
		if(value == null){
			value = "truststore.jks";
		}
		builder.append(value);
		return builder.toString();
	}
}
