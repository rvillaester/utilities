package com.helper.services;

import com.helper.enumeration.PropertiesHandlerEnum;

public abstract class RemoteConnectionService {
	
	protected PropertiesHandlerEnum propertiesManagerEnum;
	protected String application;
	
	protected RemoteConnectionService(PropertiesHandlerEnum propertiesManagerEnum, String application) {
		this.application = application;
		this.propertiesManagerEnum = propertiesManagerEnum;
	}
	
	public String getApplication(){
		return application;
	}
	
	public abstract void establishConnection();
	public abstract void disconnect();
	
}
