package com.helper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBRowColumn {

	private Map<String,Object> columnValueMap = new HashMap<>();
	
	public void put(String column, Object value){
		columnValueMap.put(column, value);
	}
	
	public Object getObject(String key){
		Object obj = columnValueMap.get(key);
		if(obj == null){
			return "NULL";
		}
		return obj;
	}
	
	public String getString(String key){
		return (String) getObject(key);
	}
	
	public int getInt(String key){
		return (Integer) getObject(key);
	}
	
	public double getDecimal(String key){
		Object obj = getObject(key);
		double value = 0;
		if (obj instanceof Double) {
			value = ((Double) obj).doubleValue();
		}else if(obj instanceof BigDecimal){
			value = ((BigDecimal) obj).doubleValue();
		}else if(obj instanceof Float){
			value = ((Float) obj).doubleValue();
		}
		return value;
	}
	
	public double getDouble(String key){
		return (Double) getObject(key);
	}
	
	public BigDecimal getBigDecimal(String key){
		return (BigDecimal) getObject(key);
	}
	
	public Date getDate(String key){
		return (Date) getObject(key);
	}
	
	public Long getLong(String key){
		return (Long) getObject(key);
	}
	
	public Timestamp getTimestamp(String key){
		return (Timestamp) getObject(key);
	}
	
	public String getObjectAsString(String key){
		return getObject(key).toString();
	}
	
	public Map<String, Object> getColumnValueMap() {
		return columnValueMap;
	}
	
	public Map<String, String> getMapWithValueAsString(){
		Map<String, String> map = new HashMap<>();
		for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
			String key = entry.getKey();
			map.put(key, getObjectAsString(key).trim());
		}
		return map;
	}
	
	public void putAll(Map<String,Object> map){
		this.columnValueMap.putAll(map);
	}
	
	public void removeEntries(List<String> keys){
		this.columnValueMap.keySet().removeAll(keys);
	}
}
