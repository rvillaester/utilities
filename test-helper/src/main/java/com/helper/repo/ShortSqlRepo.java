package com.helper.repo;

import com.helper.handler.ShortSqlPropertiesHandler;

public class ShortSqlRepo {
	
	private static ShortSqlRepo instance;
	
	private ShortSqlPropertiesHandler handler;
	
	public static ShortSqlRepo getInstance(){
		if(instance == null){
			instance = new ShortSqlRepo();
		}
		return instance;
	}
	
	private ShortSqlRepo() {
		initializeHandler();
	}
	
	private void initializeHandler(){
		if(handler == null){
			handler = new ShortSqlPropertiesHandler();
		}
	}
	
	public String getSql(String key){
		return handler.getProperty(key);
	}
}
