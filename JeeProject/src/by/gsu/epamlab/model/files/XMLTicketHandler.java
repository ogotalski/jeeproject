package by.gsu.epamlab.model.files;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.gsu.epamlab.beans.Ticket;

public class XMLTicketHandler extends DefaultHandler {
	private static final String NAME = "type";
	private static final String ID = "id";

	private enum TicketXMLEnum {
		HALL, TICKETS, COUNT, PRICE
	}

	private TicketXMLEnum currTicketEnum = null;
	private int currentCount = 0;
	private int currnetId = 0;
	private String currentName = null;
	private int currentPrice = 0;
	private Map<Integer, Ticket> map;

	public XMLTicketHandler() {
		map = new HashMap<Integer, Ticket>();
	}

	public Map<Integer, Ticket> getMap() {
		return map;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currTicketEnum != null) {
			switch (currTicketEnum) {
			case COUNT: {
				String str = new String(ch, start, length);
				currentCount = Integer.parseInt(str.trim());
			}
				break;
			case PRICE: {
				String str = new String(ch, start, length);
				currentPrice = Integer.parseInt(str.trim());
			}
				break;

			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		TicketXMLEnum enum1 = TicketXMLEnum.valueOf(qName.toUpperCase());
		if (enum1 == TicketXMLEnum.TICKETS) {
			map.put(currnetId,
					new Ticket(currnetId, currentName, currentCount, currentPrice));
		}
		currTicketEnum = null;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currTicketEnum = TicketXMLEnum.valueOf(qName.toUpperCase());

		if (currTicketEnum == TicketXMLEnum.TICKETS) {
			currnetId = Integer.parseInt(attributes.getValue(ID));
			currentName = attributes.getValue(NAME);
			
		}
	}

}
