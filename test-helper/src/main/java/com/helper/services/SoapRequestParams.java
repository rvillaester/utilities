package com.helper.services;

import java.io.File;

public class SoapRequestParams {
	private File soapXmlFile;
	private File attachment;
	
	public boolean hasAttachment(){
		return attachment != null;
	}

	public File getSoapXmlFile() {
		return soapXmlFile;
	}

	public void setSoapXmlFile(File soapXmlFile) {
		this.soapXmlFile = soapXmlFile;
	}

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	
}
