package de.netos.parser;

import java.util.function.Consumer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.netos.model.Instrument;

public class Parser extends DefaultHandler {
	
	private  final Consumer<Instrument> consumer;
	
	public Parser(Consumer<Instrument> consumer) {
		this.consumer = consumer;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("instrument")) {
			String isin = attributes.getValue("isin");
			String displayName = attributes.getValue("name");
			String country = attributes.getValue("country");
			String symbolType = attributes.getValue("symbolType");
			String ticker = attributes.getValue("ticker");
			
			consumer.accept(new Instrument(isin, displayName, country, symbolType, ticker));
		}
	}
}
