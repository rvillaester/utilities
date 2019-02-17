package com.helper.enumeration;

public enum FileLocationEnum {
	CLASSPATH, 
	FILESYSTEM;
	
	public static boolean isClasspath(FileLocationEnum fileLocation){
		return CLASSPATH.equals(fileLocation);
	}
	
	public static boolean isFileSystem(FileLocationEnum fileLocation){
		return FILESYSTEM.equals(fileLocation);
	}
}
