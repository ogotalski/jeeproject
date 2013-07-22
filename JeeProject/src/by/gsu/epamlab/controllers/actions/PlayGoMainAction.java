package by.gsu.epamlab.controllers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;

public class PlayGoMainAction implements IAction {

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
		HttpSession session = request.getSession(false);
		if (session != null){
			session.removeAttribute(ConstantsJSP.DATE_ID);
			session.removeAttribute(ConstantsJSP.CURRENT_PLAY);
		}

	}

}
