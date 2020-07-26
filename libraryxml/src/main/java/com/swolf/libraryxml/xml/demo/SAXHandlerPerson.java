package com.swolf.libraryxml.xml.demo;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author LiuYi-15973602714
 */
public class SAXHandlerPerson extends DefaultHandler {

	private ArrayList<Person> list;
	private Person p;
	private String tagName = null;

	public SAXHandlerPerson(ArrayList<Person> list) {
		this.list = list;
	}

	@Override
	public void startDocument() throws SAXException {
		if (list == null) {
			list = new ArrayList<Person>();
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.tagName = localName;
		if ("person".equals(tagName)) {
			p = new Person();
			int length = attributes.getLength();
			for (int i = 0; i < length; i++) {
				// String attrName = attributes.getLocalName(i);
				// String attrValue = attributes.getValue(i);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("person".equals(localName)) {
			list.add(p);
		}
		tagName = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch, start, length);
		if (p != null) {
			if ("name".equals(tagName)) {
				p.name = value;
			} else if ("pwd".equals(tagName)) {
				p.pwd = value;
			} else if ("age".equals(tagName)) {
				p.age = value;
			}
		}
	}

}
