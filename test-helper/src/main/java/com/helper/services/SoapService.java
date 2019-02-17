package com.helper.services;

import java.net.URL;
import java.nio.file.Path;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import com.helper.DocumentWrapper;
import com.helper.exception.HelperException;
import org.w3c.dom.Document;

import com.helper.handler.SoapFileSystemPropertiesHandler;
import com.helper.util.PasswordUtil;

public class SoapService {
	
	protected String application;
	
	public SoapService(String application) {
		this.application = application;
	}

	public DocumentWrapper invokeSoapRequest(SoapRequestParams params) {
		return invokeSoapRequest(application, params);
	}

	public DocumentWrapper invokeSoapRequest(String application, SoapRequestParams params) {
		try {
			SoapFileSystemPropertiesHandler properties = new SoapFileSystemPropertiesHandler(application);
			setTrustore(properties);
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();
			URL endpoint = new URL(properties.getEndpoint());
			SOAPMessage message = buildMessage(params, properties);
			SOAPMessage response = connection.call(message, endpoint);
			connection.close();
			return new DocumentWrapper(getResponseAsDocument(response));
		} catch (Exception e) {
			throw new HelperException(e);
		}
	}
	
	private void setTrustore(SoapFileSystemPropertiesHandler properties){
		System.setProperty("javax.net.ssl.trustStore", properties.getTrustore());
	}

	private SOAPMessage buildMessage(SoapRequestParams params, SoapFileSystemPropertiesHandler properties) throws Exception {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		addAttachment(message, params);
		message.getSOAPPart().setContent(createSource(params));
		setAuth(message, properties);
		return message;
	}
	
	private void addAttachment(SOAPMessage message, SoapRequestParams properties) throws Exception{
		if(properties.hasAttachment()){
			Path attachmentPath = properties.getAttachment().toPath();
			URL url = new URL("file:" + attachmentPath.toFile().getAbsolutePath());
			DataHandler dh = new DataHandler(url);
			AttachmentPart att = message.createAttachmentPart(dh);
			att.setContentId("fgdgsdfhgfd");
			message.addAttachmentPart(att);
		}
	}
	
	private Source createSource(SoapRequestParams properties) throws Exception {
		DocumentBuilderFactory theDBFactory = DocumentBuilderFactory.newInstance();
		theDBFactory.setNamespaceAware(true);
		DocumentBuilder theDocumentBuilder = theDBFactory.newDocumentBuilder();
		Document theDocument = theDocumentBuilder.parse(properties.getSoapXmlFile());
		return new DOMSource(theDocument);
	}
	
	@SuppressWarnings("restriction")
	private void setAuth(SOAPMessage message, SoapFileSystemPropertiesHandler properties){
		String credential = buildCredential(properties);
		String authorization = new sun.misc.BASE64Encoder().encode((credential).getBytes());
		MimeHeaders hd = message.getMimeHeaders();
		hd.addHeader("Authorization", "Basic " + authorization);
	}
    
    private String buildCredential(SoapFileSystemPropertiesHandler properties){
    	StringBuilder builder = new StringBuilder();
    	String username = properties.getUsername();
    	builder.append(username);
    	builder.append(":");
    	
    	String password = PasswordUtil.getPlainPassword(properties);
    	builder.append(password);
    	return builder.toString();
    }

	private Document getResponseAsDocument(SOAPMessage response) throws Exception{
		return response.getSOAPBody().extractContentAsDocument();
	}
}
