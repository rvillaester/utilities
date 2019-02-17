package com.helper.enumeration;

public enum FileExtensionEnum {
	XML(".xml"), 
	CVS(".csv"),
	MATCH(".match"),
	ATTACH(".attach"),
	SQL(".sql"),;
	
	private String extension;
	
	private FileExtensionEnum(String extension){
		this.extension = extension;
	}
	
	public String getExtension() {
		return extension;
	}
	
	@Override
	public String toString() {
		return extension;
	}
}
