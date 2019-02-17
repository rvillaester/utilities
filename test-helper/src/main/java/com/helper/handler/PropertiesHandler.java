package com.helper.handler;

import java.io.File;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;


public abstract class PropertiesHandler {
	
	protected Properties properties;
	
	public abstract void loadProperties(String propertiesPath);
	
	protected PropertiesHandler(String propertiesPath){
		loadProperties(propertiesPath);
	}
	
	public File getPrivateKeyFile(){
		return null;
	}
	
	public Properties getConfigProperties(){
		return null;
	}

	public String getProperty(String key){
		return properties.getProperty(key);
	}
	
	public String getUsername(){
		return properties.getProperty("username");
	}
	
	public String getSystem(){
		return properties.getProperty("system");
	}
	
	public String getPassword(){
		return properties.getProperty("password");
	}
	
	public String getSalt(){
		return properties.getProperty("salt");
	}
	
	public String getEndpoint(){
		return properties.getProperty("endpoint");
	}
	
	public String getPrivateKey(){
		String privateKey = properties.getProperty("privatekey");
		if(StringUtils.isNotEmpty(privateKey)){
			privateKey = privateKey.replace("/", File.separator);
		}
		return privateKey;
	}
	
	public String getConfig(){
		String config = properties.getProperty("config");
		if(StringUtils.isNotEmpty(config)){
			config = config.replace("/", File.separator);
		}
		return config;
	}
	
	public String getHost(){
		return properties.getProperty("host");
	}
	
	public String getDatabase(){
		return properties.getProperty("database");
	}
	
	public int getPort(){
		return Integer.valueOf(properties.getProperty("port"));
	}
}
