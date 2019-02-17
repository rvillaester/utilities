package com.helper.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import com.helper.exception.HelperException;
import org.apache.commons.io.FileUtils;

/**
 * Given a text, it will replace the sub string that is enclosed in {}
 * Returns the substituted text
 */
public class TextReplaceParser {
	
	public static String substitute(String text, Map<String, String> params){
		String subText = text;
		if(params != null){
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String key = "{" + entry.getKey() + "}";
				String value = entry.getValue();
				subText = subText.replace(key, value);
			}
		}
		
		return subText;
	}
	
	public static String substituteFileContent(File file, Map<String, String> params){
		String content;
		try {
			content = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new HelperException(e);
		}
		return substitute(content, params);
	}
	
	public static String substituteFileContent(String filePath, Map<String, String> params){
		File file = new File(filePath);
		return substituteFileContent(file, params);
	}

}
