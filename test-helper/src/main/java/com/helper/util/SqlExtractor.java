package com.helper.util;

import java.io.File;
import java.util.Map;

public class SqlExtractor {
	
	private String filename;
	private String defaultFilename;
	private Map<String, String> params;
	
	public SqlExtractor(String filename, String defaultFilename, Map<String, String> params) {
		this.filename = filename;
		this.params = params;
		this.defaultFilename = defaultFilename;
	}
	
	public SqlExtractor(String filename, Map<String, String> params) {
		this.filename = filename;
		this.params = params;
	}
	
	public String getFileName(){
		return filename;
	}
	
	public String[] getSubfolders(){
		return null;
	}
	
	public String[] getSqls(String[] subfolders){
		StringBuilder builder = new StringBuilder();
		builder.append("resources");
		builder.append(File.separator);
		builder.append("sql");
		builder.append(File.separator);
		if(subfolders != null){
			for (String subfolder : subfolders) {
				builder.append(subfolder);
				builder.append(File.separator);
			}
		}
		
		String filenameToUse = FileUtils.toSql(getFilenameToUse(builder.toString()));
		builder.append(filenameToUse);
		
		String sql = TextReplaceParser.substituteFileContent(builder.toString(), params);
		return sql.split(";");
	}
	
	private String getFilenameToUse(String parentPath){
		String filePath = parentPath + FileUtils.toSql(filename);
		if(FileUtils.isFileExist(filePath)){
			return filename;
		}
		return defaultFilename;
	}
	
	public String getSingleSql(String[] subfolders){
		return getSqls(subfolders)[0];
	}
	
	public String getSingleSql(){
		return getSingleSql(getSubfolders());
	}
	
	public String[] getSqls(){
		return getSqls(getSubfolders());
	}
}
