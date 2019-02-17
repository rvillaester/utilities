package com.helper.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.helper.DBRowColumn;
import com.helper.exception.HelperException;
import com.helper.repo.DynamicSqlKeyValueRepo;

public class ValueMatcher {

	private DBRowColumn rowColumn;
	private Properties matchProps;
	
	public ValueMatcher(String matchFilename) {
		this(null, matchFilename, matchFilename);
	}
	
	public ValueMatcher(String matchFilename, String dynamicPropKey) {
		this(null, matchFilename, dynamicPropKey);
	}
	
	public ValueMatcher(DBRowColumn rowColumn, String matchFilename) {
		this(rowColumn, matchFilename, matchFilename);
	}
	
	public ValueMatcher(DBRowColumn rowColumn, String matchFilename, String dynamicPropKey) {
		this.rowColumn = rowColumn;
		loadMatchFile(matchFilename, dynamicPropKey);
	}
	
	public String[] getSubfolders(){
		return null;
	}
	
	private void loadMatchFile(String filename, String dynamicPropKey){
		StringBuilder builder = new StringBuilder();
		builder.append("resources");
		builder.append(File.separator);
		builder.append("match-validation");
		builder.append(File.separator);

		String[]  subfolders = getSubfolders();
		if(subfolders != null){
			for (String subfolder : subfolders) {
				builder.append(subfolder);
				builder.append(File.separator);
			}
		}
		
		builder.append(FileUtils.toMatch(filename));
		
		matchProps = new Properties();
		addDynamicProperties(FileUtils.removeExtension(dynamicPropKey));
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(builder.toString()));
			matchProps.putAll(prop);
		} catch (IOException e) {
			throw new HelperException(e);
		}
	}
	
	public void assertMatch(){
		Set<String> keys = matchProps.stringPropertyNames();
	    for (String key : keys) {
	    	System.out.println("key: " + key);
	    	String expectedValue = matchProps.getProperty(key).trim();
			String actualValue = rowColumn.getObjectAsString(key).trim();
			assertValue(actualValue, expectedValue);
	    }
	}
	
	public void assertMatch(DBRowColumn rowColumn){
		Map<String,Object> columnValueMap = rowColumn.getColumnValueMap();
		for (Map.Entry<String,Object> entry : columnValueMap.entrySet()) {
			String key = entry.getKey();
	    	System.out.println("key: " + key);
			String actualValue = rowColumn.getObjectAsString(key).trim();
			String expectedValue = matchProps.getProperty(key).trim();
			assertValue(actualValue, expectedValue);
		}
	}
	
	public void assertMatch(DBRowColumn rowColumn, String dynamicPropertyKey){
		assertMatch(rowColumn);
	}
	
	public void assertMatch(List<DBRowColumn> rowColumns, String dynamicPropertyKey){
		for (DBRowColumn rowColumn : rowColumns) {
			assertMatch(rowColumn);
		}
	}
	
	private void addDynamicProperties(String dynamicPropertyKey){
		DynamicSqlKeyValueRepo repo = DynamicSqlKeyValueRepo.getInstance();
		Map<String, String> map = repo.get(dynamicPropertyKey);
		if(map != null){
			matchProps.putAll(map);
		}
	}
	
	private void assertValue(String actualValue, String expectedValue){
		System.out.println(expectedValue + ":" + actualValue);
		if(isNumber(expectedValue)){
			assertTrue(Double.parseDouble(actualValue) == getNumberValue(expectedValue));
		}else if(isContains(expectedValue)){
			assertThat(actualValue, containsString(getValue(expectedValue)));
		}else if(isNotNull(expectedValue)){
			assertThat(actualValue, is(notNullValue()));
		}else{
			assertThat(actualValue, equalTo(expectedValue));
		}
	}
	
	private boolean isNotNull(String value){
		return value.startsWith("NOT_NULL");
	}
	
	private boolean isNumber(String value){
		return value.startsWith("~number");
	}
	
	private boolean isContains(String value){
		return value.startsWith("~contains");
	}
	
	private double getNumberValue(String value){
		return Double.parseDouble(getValue(value));
	}
	
	private String getValue(String value){
		String[] strs = value.split("\\|");
		return strs[1].trim();
	}
}
