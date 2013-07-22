package by.gsu.epamlab.controllers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;
import by.gsu.epamlab.interfaces.IUserDAO;
import by.gsu.epamlab.model.factories.DAOFactory;

public class LoginAction implements IAction {
	
	private static final String ERROR_URI = Constants.INDEX_JSP;
	private static final String DONE_URI = Constants.INDEX_JSP;

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

		IUserDAO userDAO = DAOFactory.getDAO(IUserDAO.class);
		String name = request.getParameter(ConstantsJSP.LOGIN);
		String pass = request.getParameter(ConstantsJSP.PASSWORD);

		if (name == null || pass == null) {
			throw new DAOException(Constants.INVALID_NAME_OR_PASSWORD);
		}
        name = name.trim();
		User user = userDAO.getUser(name, pass);
		HttpSession session = request.getSession();
		session.setAttribute(ConstantsJSP.USER, user);
	}
	


}
