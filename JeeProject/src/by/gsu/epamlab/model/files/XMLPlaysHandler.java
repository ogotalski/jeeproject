package by.gsu.epamlab.model.files;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import by.gsu.epamlab.beans.Play;

public class XMLPlaysHandler extends DefaultHandler {
	private static final String IMAGE = "image";
	private static final String NAME = "name";
	private static final String AUTHOR = "author";
	private static final String GENRE = "genre";
	private DateFormat DATE_FORMAT_IN = new SimpleDateFormat("dd.MM.yyyy");
	
	private static enum PlayXMLEnum{
		PLAYS,
		PLAY,
		DATE,
		DESCRIPTION
	}
	private Date parseTime (String date){
		Date sqlDate = null;
		try {
			sqlDate = new Date( DATE_FORMAT_IN.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e.getMessage());
		}
		return sqlDate;
	}
	
	private List<Play> list;
	private String currPlayDate = null;
	private static int dateId = 0;
	private Play currentPlay = null;
	private PlayXMLEnum currPlayEnum = null;
	private StringBuilder currentDesc = null;
	public XMLPlaysHandler() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<Play>();
		list.clear();
	}
	/**
	 * @return the results
	 */
	public List<Play> getList() {
		return list;
	}
	
	public void addPlay(Play play)
	{
		list.add(play);
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		if (currPlayEnum == PlayXMLEnum.DATE){
			String str = new String (ch, start, length);
			currPlayDate = str.trim();
		} else if (currPlayEnum == PlayXMLEnum.DESCRIPTION){
			String str = new String (ch, start, length);
			currentDesc.append(str);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		currPlayEnum = PlayXMLEnum.valueOf(qName.toUpperCase());
		if (PlayXMLEnum.PLAY == currPlayEnum){
			
			currentPlay = new Play(attributes.getValue(NAME), attributes.getValue(AUTHOR), attributes.getValue(GENRE));
			String img = attributes.getValue(IMAGE);
			if (img != null) {
				currentPlay.setImageURI(img);
			}
			currentDesc = new StringBuilder();
		}
	}
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		PlayXMLEnum playXMLEnum =  PlayXMLEnum.valueOf(qName.toUpperCase());
		if (playXMLEnum == PlayXMLEnum.DATE){
			int curId;
			synchronized (XMLPlaysHandler.class) {
				 curId = ++dateId;
					
			}
			currentPlay.addDate(curId,parseTime(currPlayDate));
		}else if (playXMLEnum == PlayXMLEnum.PLAY){
			addPlay(currentPlay);
		}else if (playXMLEnum == PlayXMLEnum.DESCRIPTION){
			currentPlay.setDescription(currentDesc.toString());
		}
		
		currPlayEnum = null;
	}
	
	 
	 
}
