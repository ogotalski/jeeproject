package by.gsu.epamlab.model.factories;

import java.util.HashMap;
import java.util.Map;

import by.gsu.epamlab.interfaces.IPlayDAO;
import by.gsu.epamlab.interfaces.ITicketDAO;
import by.gsu.epamlab.interfaces.IUserDAO;
import by.gsu.epamlab.model.db.DBPlayDAO;
import by.gsu.epamlab.model.db.DBTickets;
import by.gsu.epamlab.model.db.DBUserDAO;
import by.gsu.epamlab.model.files.XMLPlaysDAO;

public class DAOFactory {
	protected static Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	static{
	//	map.put(IUserDAO.class, new CSVUserImpl());
	//	map.put(IPlayDAO.class, new XMLPlaysDAO());
		map.put(IUserDAO.class, new DBUserDAO());
		map.put(IPlayDAO.class, new DBPlayDAO());
	    map.put(ITicketDAO.class, new DBTickets());
	}
	public static <T> T getDAO(Class<T> type) {
		
		return type.cast(map.get(type));
	}
}
