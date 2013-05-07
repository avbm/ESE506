/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceContracts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Base64;

/**
 * Utility class reading the messages from XML stream
 */
public class MessageReader extends DefaultHandler {
	/**
	 * The message list
	 */
	private List<Message> messages = null;

	/**
	 * The single message
	 */
	private Message message = null;

	/**
	 * The message tag
	 */
	private String preTag = null;

	/**
	 * Gets the messages from xml stream
	 * 
	 * @param xmlStream
	 *            the stream
	 * @return
	 * @throws Exception
	 *             List<Message>
	 */
	public List<Message> getMessages(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		MessageReader handler = new MessageReader();
		parser.parse(xmlStream, handler);

		return handler.getMessages();
	}

	/**
	 * Gets the messages
	 * 
	 * @return List<Message> Returns the messages.
	 */
	public List<Message> getMessages() {
		return this.messages;
	}

	/*
	 * Override base method
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		this.messages = new ArrayList<Message>();
	}

	/*
	 * Override base method
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("Message".equals(qName)) {
			this.message = new Message();
		}
		this.preTag = qName;
	}

	/*
	 * Override base method
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("Message".equals(qName)) {
			this.messages.add(this.message);
			this.message = null;
		}
		preTag = null;
	}

	/*
	 * Override base method
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("From".equals(preTag)) {
				this.message.setFrom(content);
			} else if ("To".equals(preTag)) {
				this.message.setTo(content);
			} else if ("Body".equals(preTag)) {
				this.message.setBody(Base64.decode(content, Base64.DEFAULT));
			}
		}
	}

}
