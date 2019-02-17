package com.helper.enumeration;

public enum PropertiesHandlerEnum {
	// used this if you want to connect to a ssh server with the properties file in the classpath
	SSH_CLASSPATH(SystemEnum.SSH, FileLocationEnum.CLASSPATH),
	// used this if you want to connect to a ssh server with the properties file in the filesystem
	SSH_FILESYSTEM(SystemEnum.SSH, FileLocationEnum.FILESYSTEM),
	// used this if you want to connect to a database server with the properties file in the classpath
	DB_CLASSPATH(SystemEnum.DB, FileLocationEnum.CLASSPATH),
	// used this if you want to connect to a database server with the properties file in the classpath
	DB_FILESYSTEM(SystemEnum.DB, FileLocationEnum.FILESYSTEM);
	
	private SystemEnum system;
	private FileLocationEnum fileLocation;
	
	PropertiesHandlerEnum(SystemEnum system, FileLocationEnum fileLocation) {
		this.system = system;
		this.fileLocation = fileLocation;
	}
	
	public boolean isSsh(){
		return this.system.equals(SystemEnum.SSH);
	}
	
	public boolean isDB(){
		return this.system.equals(SystemEnum.DB);
	}
	
	public boolean isClasspath(){
		return this.fileLocation.equals(FileLocationEnum.CLASSPATH);
	}
	
	public boolean isFileSystem(){
		return this.fileLocation.equals(FileLocationEnum.FILESYSTEM);
	}
	
	public boolean isSsh_Classpath(){
		return this.equals(SSH_CLASSPATH);
	}
	
	public boolean isSsh_FileSystem(){
		return this.equals(SSH_FILESYSTEM);
	}
	
	public boolean isDB_Classpath(){
		return this.equals(DB_CLASSPATH);
	}
	
	public boolean isDB_FileSystem(){
		return this.equals(DB_FILESYSTEM);
	}

}
