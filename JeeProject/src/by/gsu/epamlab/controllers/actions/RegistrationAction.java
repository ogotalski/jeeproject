package by.gsu.epamlab.controllers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.enums.Role;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;
import by.gsu.epamlab.interfaces.IUserDAO;
import by.gsu.epamlab.model.factories.DAOFactory;

public class RegistrationAction implements IAction {
	
	private static final String ERROR_URI = Constants.SIGNUP_JSP;
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
		String pass2 = request.getParameter(ConstantsJSP.PASSWORD2);

		String email = request.getParameter(ConstantsJSP.EMAIL);
		String phone = request.getParameter(ConstantsJSP.PHONE);

		if (pass == null || !pass.equals(pass2)) {
			throw new DAOException(Constants.PASSWORDS_DO_NOT_MATCH);
		}

		userDAO.addUser(name, pass, Role.USER, email, phone);
		User user = userDAO.getUser(name, pass);
		HttpSession session = request.getSession();
		session.setAttribute(ConstantsJSP.USER, user);
   }

}
