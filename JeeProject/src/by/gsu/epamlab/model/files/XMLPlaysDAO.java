package by.gsu.epamlab.model.files;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.gsu.epamlab.beans.Play;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsXML;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IPlayDAO;

public class XMLPlaysDAO implements IPlayDAO {

	
	
	@Override
	public List<Play> getPlaysList() throws DAOException {
		XMLReader reader = null;
    	XMLPlaysHandler contentHandler = null;
    	
    	try{
			reader = XMLReaderFactory.createXMLReader();
			contentHandler = new XMLPlaysHandler();
			reader.setContentHandler(contentHandler);
			reader.parse(Constants.ResourcePath + ConstantsXML.XML_PLAYS_FILE_NAME);
			return contentHandler.getList();
		} catch (SAXException e){
			throw new DAOException(e);
		} catch (IOException e) {
			throw new DAOException(e);
		} 
		
	
		
	}
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addPlays(List<Play> plays) throws DAOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Play getPlay(int dateID) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
