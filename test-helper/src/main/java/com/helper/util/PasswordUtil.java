package com.helper.util;

import com.helper.handler.PropertiesHandler;
import org.apache.commons.lang3.StringUtils;

public class PasswordUtil {
	
	private static final String DEFAULT_SALT = "DEFAULTXSALT";
	
	public static String getPlainPassword(PropertiesHandler propManager){
		String username = propManager.getUsername();
		String password = propManager.getPassword();
		if(StringUtils.isNotEmpty(password)
				&& password.toLowerCase().startsWith("enc:")){
			String salt = propManager.getSalt();
			if(StringUtils.isEmpty(salt)){
				salt = DEFAULT_SALT;
			}
			password = Encoder.decode(salt, password);
		}
		return password;
	}

}
