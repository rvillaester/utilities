package com.helper.util;

import java.io.File;
import java.util.Optional;

import com.helper.enumeration.FileExtensionEnum;

public class FileUtils {
	
	public static boolean isXml(String filename){
		return Optional.ofNullable(filename).filter(f -> f.endsWith(FileExtensionEnum.XML.getExtension())).isPresent();
	}
	
	public static boolean isAttach(String filename){
		return Optional.ofNullable(filename).filter(f -> f.endsWith(FileExtensionEnum.ATTACH.getExtension())).isPresent();
	}
	
	public static boolean isSql(String filename){
		return Optional.ofNullable(filename).filter(f -> f.endsWith(FileExtensionEnum.SQL.getExtension())).isPresent();
	}
	
	public static boolean isCsv(String filename){
		return Optional.ofNullable(filename).filter(f -> f.endsWith(FileExtensionEnum.CVS.getExtension())).isPresent();
	}
	
	public static boolean isMatch(String filename){
		return Optional.ofNullable(filename).filter(f -> f.endsWith(FileExtensionEnum.MATCH.getExtension())).isPresent();
	}
	
	public static String toSql(String filename){
		if(isSql(filename)){
			return filename;
		}
		return Optional.ofNullable(filename).map(f -> filename + FileExtensionEnum.SQL.getExtension()).orElse(filename);
	}
	
	public static String toMatch(String filename){
		if(isMatch(filename)){
			return filename;
		}
		return Optional.ofNullable(filename).map(f -> filename + FileExtensionEnum.MATCH.getExtension()).orElse(filename);
	}
	
	public static String toXml(String filename){
		if(isXml(filename)){
			return filename;
		}
		return Optional.ofNullable(filename).map(f -> filename + FileExtensionEnum.XML.getExtension()).orElse(filename);
	}
	
	public static String toCsv(String filename){
		if(isCsv(filename)){
			return filename;
		}
		return Optional.ofNullable(filename).map(f -> filename + FileExtensionEnum.CVS.getExtension()).orElse(filename);
	}
	
	public static String toAttach(String filename){
		if(isAttach(filename)){
			return filename;
		}
		return Optional.ofNullable(filename).map(f -> filename + FileExtensionEnum.ATTACH.getExtension()).orElse(filename);
	}
	
	public static String removeExtension(String filename){
		int i = filename.lastIndexOf('.');
		if(i == -1){
			return filename;
		}
		return filename.substring(0, i);
	}
	
	public static boolean isFileExist(String filePath){
		return new File(filePath).isFile();
	}
}
