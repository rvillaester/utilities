package com.helper.repo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DynamicSqlKeyValueRepo {
	
	private static DynamicSqlKeyValueRepo instance;
	
	private Map<String, Map<String, String>> map = new HashMap<>();
	
	private DynamicSqlKeyValueRepo() {}
	
	public static DynamicSqlKeyValueRepo getInstance(){
		if(instance == null){
			instance = new DynamicSqlKeyValueRepo();
		}
		return instance;
	}
	
	public void put(String key, Map<String, String> value){
		map.put(key, value);
	}
	
	public Map<String, String> get(String key){
		return map.get(key);
	}
	
	public Properties getAsProperties(String key){
		Map<String, String> entries = map.get(key);
		Properties properties = new Properties();
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		return properties;
	}

}
