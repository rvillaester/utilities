package com.helper.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.helper.exception.HelperException;

public class FileSystemPropertiesHandler extends PropertiesHandler{
	
	public FileSystemPropertiesHandler(String application) {
		super(application);
	}

	@Override
	public void loadProperties(String propertiesPath) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			throw new HelperException(e);
		} 
	}
	
	@Override
	public File getPrivateKeyFile() {
		String privateKeyPath = getPrivateKey();
		if(privateKeyPath != null){
			return new File(privateKeyPath);
		}
		return null;
	}
	
	@Override
	public Properties getConfigProperties(){
		String configPropertiesPath = getConfig();
		if(configPropertiesPath != null){
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(configPropertiesPath));
			} catch (IOException e) {
				throw new HelperException(e);
			}
			return properties;
		}
		return null;
	}
}
