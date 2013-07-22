package by.gsu.epamlab.controllers.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;

public class LogoutAction implements IAction {

	private static final String ERROR_URI = Constants.LOGOUT_URL;
	private static final String DONE_URI = Constants.LOGOUT_URL;

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
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		try {
			response.sendRedirect(Constants.LOGOUT_URL);
		} catch (IOException e) {
			throw new DAOException(e);
		}

	}

}
