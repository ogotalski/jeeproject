package by.gsu.epamlab.controllers.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.beans.Payment;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;
import by.gsu.epamlab.interfaces.ITicketDAO;
import by.gsu.epamlab.model.factories.DAOFactory;

public class PayOrderAction implements IAction {
	private static final String ERROR_URI = Constants.VIEW_PLAYS_ORDERS_JSP;
	private static final String DONE_URI = Constants.VIEW_PLAYS_ORDERS_JSP;

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

		Integer dateId = (Integer) session.getAttribute(ConstantsJSP.DATE_ID);
		if (user == null || dateId == null) {
			throw new DAOException(Constants.INTERNAL_ERROR);
		}

		// TODO Privileges
		ITicketDAO ticketDAO = DAOFactory.getDAO(ITicketDAO.class);
		try {
			ticketDAO.pay(getPayList(request));
		} catch (DAOException e) {
			request.setAttribute(ConstantsJSP.ERROR_MESSAGE, e.getMessage());
		}
		request.setAttribute(ConstantsJSP.USER_ORDERS,
				ticketDAO.getUserOrders(dateId));

	}

	private List<Payment> getPayList(HttpServletRequest request)
			throws DAOException {
		List<Payment> list = new ArrayList<Payment>();
		String[] ids =  request.getParameterValues("orderIds");
		int orderId;
		int quantity;
		if (ids == null) {
			return list; 
		}
		String s;
		try {
			for (String id : ids) {
				
				s = request.getParameter(id);
				if ("".equals(s)){
					continue;
				}
				orderId = Integer.parseInt(id);
				quantity = Integer.parseInt(s);
				if (orderId > 0 && quantity > 0) {
					list.add(new Payment(orderId, quantity));
				} else {
					throw new DAOException(Constants.NEGATIVE_ARGUMENT_QUANTITY);
				}
			}
		} catch (NumberFormatException e) {
			throw new DAOException(Constants.ILLEGAL_ARGUMENT + e.getMessage());
		}
		return list;
	}

}
