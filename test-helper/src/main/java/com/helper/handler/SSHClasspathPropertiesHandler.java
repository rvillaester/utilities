package com.helper.handler;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class SSHClasspathPropertiesHandler extends ClasspathPropertiesHandler{
	
	private String application;
	
	public SSHClasspathPropertiesHandler(String application) {
		super(application);
		this.application = application;
	}

	@Override
	public void loadProperties(String application) {
		StringBuilder builder = new StringBuilder();
		builder.append(File.separator);
		builder.append("ssh");
		builder.append(File.separator);
		builder.append(application);
		builder.append(File.separator);
		builder.append("connection.properties");
		super.loadProperties(builder.toString());
	}
	
	@Override
	public String getPrivateKey() {
		String privateKey = super.getPrivateKey();
		if(StringUtils.isNotEmpty(privateKey)){
			StringBuilder builder = new StringBuilder();
			builder.append(File.separator);
			builder.append("ssh");
			builder.append(File.separator);
			builder.append(application);
			builder.append(File.separator);
			builder.append(privateKey);
			return builder.toString();
		}
		return privateKey;
	}
	
	@Override
	public String getConfig() {
		String config = super.getConfig();
		if(StringUtils.isNotEmpty(config)){
			StringBuilder builder = new StringBuilder();
			builder.append("ssh");
			builder.append(File.separator);
			builder.append(application);
			builder.append(File.separator);
			builder.append(config);
			return builder.toString();
		}
		return config;
	}

}
