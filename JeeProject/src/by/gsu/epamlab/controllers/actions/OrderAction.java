package by.gsu.epamlab.controllers.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.beans.Order;
import by.gsu.epamlab.beans.Ticket;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;
import by.gsu.epamlab.interfaces.ITicketDAO;
import by.gsu.epamlab.model.factories.DAOFactory;

public class OrderAction implements IAction {
	private static final String ERROR_URI = Constants.VIEW_PLAYS_TICKETS_JSP;
	private static final String DONE_URI = Constants.VIEW_PLAYS_TICKETS_JSP;

	@Override
	public String getDoneUrl() {
		
		return DONE_URI;
	}

	@Override
	public String getErrorUrl() {
		return ERROR_URI;
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws DAOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(ConstantsJSP.USER);
		
		
		Integer dateId =(Integer) session.getAttribute(ConstantsJSP.DATE_ID);
		if (user == null || dateId == null){
			throw new DAOException(Constants.INTERNAL_ERROR);
		}
		ITicketDAO ticketDAO = DAOFactory.getDAO(ITicketDAO.class);
		try {
			ticketDAO.order(dateId, user, getOrderList(request));
		} catch (DAOException e){
			request.setAttribute(ConstantsJSP.ERROR_MESSAGE, e.getMessage());
		}
		request.setAttribute(ConstantsJSP.TICKETS_SUMMARIES, ticketDAO.getTicketsSummaries(dateId, user));
	}

	private List<Order> getOrderList(HttpServletRequest request) throws DAOException{
		List<Order> list = new ArrayList<Order>();
		int quantity = 0;
		String s;
		for(Ticket ticket : Constants.tickets.values()){
			try{
				s = request.getParameter(ticket.getName());
				if (s == null || "".equals(s)){
					continue;
				}
				quantity = Integer.parseInt(s);
				if (quantity > 0) {
				list.add(new Order(ticket,quantity));
				} else {
				    throw new DAOException(Constants.NEGATIVE_ARGUMENT_QUANTITY);	
				}
			}catch (NumberFormatException e){
				throw new DAOException(Constants.ILLEGAL_ARGUMENT + e.getMessage() );
			}
		}
		return list;
	}

}
