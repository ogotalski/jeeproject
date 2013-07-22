package by.gsu.epamlab.constants;

import java.io.IOException;
import java.util.Map;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.gsu.epamlab.beans.Ticket;
import by.gsu.epamlab.model.files.XMLTicketHandler;

public class Constants {
	public static final String NAME_CLASSES_ROOT = "WEB-INF/classes/";
	public static final String Name_RESOURCE_ROOT = "/WEB-INF/";
	public static final String NAME_PROJECT_ROOT = "";
	public static final String INTERNAL_ERROR = "some troubles";
	public static final String INVALID_NAME_OR_PASSWORD = "invalid name or password";
	public static final String INVALID_NAME = "invalid name";
	public static final String LOGOUT_URL = "";
	public static final String DefaultCharset = "UTF-8";
	public static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	public static final String SLASH = "/";
	public static String ResourcePath;
	public static final String VIEW_PLAYS_TICKETS_JSP = "/WEB-INF/viewPlaysTickets.jsp";
	public static final String VIEW_PLAYS_ORDERS_JSP = "/WEB-INF/viewPlaysOrders.jsp";
	public static final String ORDER_REPORT_JSP = "/WEB-INF/orderReport.jsp";
	public static final String INDEX_JSP = "/index.jsp";
	public static final String SIGNUP_JSP = "/signup.jsp";
	public static Map<Integer, Ticket> tickets;
	public static void init(){
		XMLReader reader = null;
		XMLTicketHandler contentHandler = null;
    	
    	try{
			reader = XMLReaderFactory.createXMLReader();
			contentHandler = new XMLTicketHandler();
			reader.setContentHandler(contentHandler);
			reader.parse(Constants.ResourcePath + ConstantsXML.XML_TICKET_FILE_NAME);
			tickets = contentHandler.getMap();
		} catch (SAXException e){
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	public static final String ILLEGAL_ARGUMENT = "Illegal argument :";
	public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match";
	public static final String NEGATIVE_ARGUMENT_QUANTITY = "Negative argument: quantity";
	public static final String NOT_ENOUGH_TICKETS = "Not enough tickets ";
}
