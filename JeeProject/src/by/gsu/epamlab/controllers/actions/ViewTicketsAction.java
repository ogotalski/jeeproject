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

public class ViewTicketsAction implements IAction {

	private static final String ERROR_URI = Constants.INDEX_JSP;
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
		
		Integer dateId = 0;
		try {
			dateId = Integer.parseInt(request.getParameter(ConstantsJSP.DATE_ID));
		}catch (NumberFormatException e) {
			throw new DAOException(Constants.ILLEGAL_ARGUMENT + e.getMessage());
		}
		Play play = DAOFactory.getDAO(IPlayDAO.class).getPlay(dateId);
		
		HttpSession session = request.getSession();
		session.setAttribute(ConstantsJSP.CURRENT_PLAY, play);
		session.setAttribute(ConstantsJSP.DATE_ID, dateId);
		User user = (User) session.getAttribute(ConstantsJSP.USER);
		ITicketDAO ticketDAO = DAOFactory.getDAO(ITicketDAO.class);
		request.setAttribute(ConstantsJSP.TICKETS_SUMMARIES, ticketDAO.getTicketsSummaries(dateId, user));
		
	
	}

}
