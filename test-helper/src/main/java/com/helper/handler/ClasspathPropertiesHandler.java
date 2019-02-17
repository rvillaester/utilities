package com.helper.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import com.helper.exception.HelperException;
import org.apache.commons.lang3.StringUtils;

public class ClasspathPropertiesHandler extends PropertiesHandler{
	
	public ClasspathPropertiesHandler(String propertiesPath) {
		super(propertiesPath);
	}

	@Override
	public void loadProperties(String propertiesPath) {
		properties = new Properties();
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(propertiesPath);
		try {
			properties.load(stream);
		} catch (IOException e) {
			throw new HelperException(e);
		}
	}
	
	@Override
	public File getPrivateKeyFile() {
		String privateKeyPath = getPrivateKey();
		if(privateKeyPath != null){
			URL url = this.getClass().getClassLoader().getResource(privateKeyPath);
			try {
				return new File(url.toURI());
			} catch (URISyntaxException e) {
				throw new HelperException(e);
			}
		}
		return null;
	}
	
	@Override
	public Properties getConfigProperties(){
		String configPropertiesPath = getConfig();
		if(StringUtils.isNotEmpty(configPropertiesPath)){
			Properties properties = new Properties();
			try {
				try (final InputStream stream =
						this.getClass().getClassLoader().getResourceAsStream(configPropertiesPath)) {
				    properties.load(stream);
				}
			}  catch (FileNotFoundException e) {
				// do nothing
			} catch (IOException e) {
				throw new HelperException(e);
			}
			
			return properties;
		}
		
		return null;
	}
}
