package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class SaxParseXML extends DefaultHandler {
	private String nodeName;
	private StringBuilder id;
	private StringBuilder name;
	private StringBuilder version;

	@Override
	// 开始解析XML被调用
	public void startDocument() throws SAXException {
		id = new StringBuilder();
		name = new StringBuilder();
		version = new StringBuilder();
	}

	@Override
	// 开始解析某个节点时候被调用
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// 记录当前节点名
		nodeName = localName;
	}

	@Override
	// 获取节点中的内容时被调用
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// 根据当前节点名判断内容添加到哪一个StringBuilder对象中
		if ("id".equals(nodeName)) {
			id.append(ch, start, length);
		} else if ("name".equals(nodeName)) {
			name.append(ch, start, length);
		} else if ("version".equals(nodeName)) {
			version.append(ch, start, length);
		}
	}

	@Override
	// 完成解析某个节点时候被调用
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("app".equals(localName)){
			Log.d("ContentHandler","id is " + id.toString().trim());
			Log.d("ContentHandler","name is " + name.toString().trim());
			Log.d("ContentHandler","version is " + version.toString().trim());
			//最后要将StringBuilder清空掉
			id.setLength(0);
			name.setLength(0);
			version.setLength(0);
		}
	}

	@Override
	// 完成解析XML时候被调用
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
}
