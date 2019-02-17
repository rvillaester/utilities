package com.helper;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentWrapper {
	
	private Document document;
	
	public DocumentWrapper(Document document) {
		this.document = document;
	}
	
	public String getElementValue(String name){
		if(document.hasChildNodes()){
			return getElementValue(document.getChildNodes(), name);
		}
		return null;
	}
	
	public Document getDocument() {
		return document;
	}
	
	private String getElementValue(NodeList nodeList, String name) {
		int len = nodeList.getLength();
		for (int count = 0; count < len; count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE
					&& tempNode.getNodeName().endsWith(name)) {
				return tempNode.getTextContent();
			}
			if (tempNode.hasChildNodes()) {
				String val = getElementValue(tempNode.getChildNodes(), name);
				if(StringUtils.isNotEmpty(val)){
					return val;
				}
			}
		}
		return "";
	}
}