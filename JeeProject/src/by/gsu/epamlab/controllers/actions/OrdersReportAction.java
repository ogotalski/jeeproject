package by.gsu.epamlab.controllers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.beans.Play;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;
import by.gsu.epamlab.interfaces.IPlayDAO;
import by.gsu.epamlab.interfaces.ITicketDAO;
import by.gsu.epamlab.model.factories.DAOFactory;

public class OrdersReportAction implements IAction {
	private static final String ERROR_URI = Constants.VIEW_PLAYS_ORDERS_JSP;
	private static final String DONE_URI = Constants.ORDER_REPORT_JSP;

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
		Integer dateId = 0;
		try {
			dateId = Integer.parseInt(request.getParameter(ConstantsJSP.DATE_ID));
			Play play = DAOFactory.getDAO(IPlayDAO.class).getPlay(dateId);
			session.setAttribute(ConstantsJSP.CURRENT_PLAY, play);
			session.setAttribute(ConstantsJSP.DATE_ID, dateId);
		}catch (NumberFormatException e) {
			 dateId =(Integer) session.getAttribute(ConstantsJSP.DATE_ID);
		}
		
		
		if (user == null || dateId == null){
			throw new DAOException(Constants.INTERNAL_ERROR);
		}
		ITicketDAO ticketDAO = DAOFactory.getDAO(ITicketDAO.class);
		request.setAttribute(ConstantsJSP.TICKETS_SUMMARIES, ticketDAO.getTicketsSummaries(dateId));
		request.setAttribute(ConstantsJSP.USER_ORDERS, ticketDAO.getUserOrders(dateId));
	}

}
