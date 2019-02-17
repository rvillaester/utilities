package com.helper.factory;

import com.helper.enumeration.PropertiesHandlerEnum;
import com.helper.handler.DBClasspathPropertiesHandler;
import com.helper.handler.DBFileSystemPropertiesHandler;
import com.helper.handler.PropertiesHandler;
import com.helper.handler.SSHClasspathPropertiesHandler;
import com.helper.handler.SSHFileSystemPropertiesHandler;

public class PropertiesHandlerFactory {
	
	public static PropertiesHandler getPropertiesHandler(PropertiesHandlerEnum propertiesManagerEnum, String application){
		PropertiesHandler propertiesHandler = null;
		switch (propertiesManagerEnum) {
		case DB_CLASSPATH:
			propertiesHandler = new DBClasspathPropertiesHandler(application);
			break;
		case SSH_CLASSPATH:
			propertiesHandler = new SSHClasspathPropertiesHandler(application);
			break;
		case SSH_FILESYSTEM:
			propertiesHandler = new SSHFileSystemPropertiesHandler(application);
			break;
		case DB_FILESYSTEM:
			propertiesHandler = new DBFileSystemPropertiesHandler(application);
			break;
		default:
			break;
		}
		return propertiesHandler;
	}
}