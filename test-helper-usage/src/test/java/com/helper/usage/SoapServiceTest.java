package com.helper.usage;

import java.io.File;

import com.helper.usage.service.SoapWebService;
import org.junit.Before;
import org.junit.Test;

import com.helper.DocumentWrapper;
import com.helper.services.SoapRequestParams;

public class SoapServiceTest {
	
	SoapWebService soapService;
	
	@Before
	public void init(){
		soapService = new SoapWebService();
	}

	@Test
	public void testSoapService(){
		SoapRequestParams params = new SoapRequestParams();
		params.setSoapXmlFile(new File("soap.xml"));
		DocumentWrapper result = soapService.invokeSoapRequest(params);
		System.out.println(result.getElementValue("firstname"));
		System.out.println(result.getElementValue("id"));
	}
}
