package com.helper.repo;

import java.util.HashMap;
import java.util.Map;

import com.helper.enumeration.DatabaseType;
import com.helper.exception.HelperException;
import com.helper.factory.DatabaseServiceFactory;
import com.helper.services.DatabaseService;

public class DatabaseServiceRepo {
	
	private static DatabaseServiceRepo instance;
	
	private Map<String, DatabaseService> map = new HashMap<>();
	
	private DatabaseServiceRepo() {}
	
	public static DatabaseServiceRepo getInstance(){
		if(instance == null){
			instance = new DatabaseServiceRepo();
		}
		return instance;
	}
	
	/**
	 * use this method only if the service hasn't been registered yet
	 */
	public DatabaseService register(String key, DatabaseType type){
		DatabaseService service = map.get(key);
		if(service == null){
			service = DatabaseServiceFactory.getDatabaseService(type, key);
			service.establishConnection();
			map.put(key, service);
		}
		return service;
	}
	
	public DatabaseService get(String key){
		DatabaseService service = map.get(key);
		if(service == null){
			throw new HelperException("Database hasn't been registered yet. Please use register method instead");
		}
		return service;
	}
	
	public void remove(String key){
		DatabaseService service = map.get(key);
		if(service != null){
			service.disconnect();
		}
		map.remove(key);
	}
	
	public void add(DatabaseService service){
		map.put(service.getApplication(), service);
	}

}
