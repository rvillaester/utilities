package com.helper.repo;

import java.util.HashMap;
import java.util.Map;

import com.helper.services.SSHService;

public class SSHServiceRepo {
	
	private static SSHServiceRepo instance;
	
	private Map<String, SSHService> map = new HashMap<>();
	
	private SSHServiceRepo() {}
	
	public static SSHServiceRepo getInstance(){
		if(instance == null){
			instance = new SSHServiceRepo();
		}
		return instance;
	}
	
	public SSHService get(String key){
		SSHService service = map.get(key);
		if(service == null){
			service = new SSHService(key);
			service.establishConnection();
			map.put(key, service);
		}
		return service;
	}
	
	public void remove(String key){
		SSHService service = map.get(key);
		if(service != null){
			service.disconnect();
		}
		map.remove(key);
	}
	
	public void add(SSHService service){
		map.put(service.getApplication(), service);
	}

}
