package by.gsu.epamlab.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.enums.PlayCourierActions;
import by.gsu.epamlab.enums.PlaysActions;
import by.gsu.epamlab.enums.Role;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;
import by.gsu.epamlab.interfaces.IPlayDAO;
import by.gsu.epamlab.model.factories.DAOFactory;

public class PlaysController extends AbstractController {

	@Override
	public void init() throws ServletException {
		super.init();
		Constants.ResourcePath = getServletConfig().getServletContext()
				.getRealPath(Constants.NAME_PROJECT_ROOT)+Constants.Name_RESOURCE_ROOT;
		IPlayDAO playDAO = DAOFactory.getDAO(IPlayDAO.class);
		Constants.init();
		ServletContext sc = getServletContext();
		try {
			playDAO.update();
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			sc.setAttribute(ConstantsJSP.PLAYS, playDAO.getPlaysList());
		} catch (DAOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		IAction action = null;
		String actionString = request.getParameter(ConstantsJSP.ACTION);
		if (actionString == null) {
			jump(Constants.INDEX_JSP, request, response);
			return;
		}
		User user = null;
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				user = (User) session.getAttribute(ConstantsJSP.USER);
			}
			if (user != null
					&& user.getRole().ordinal() >= Role.COURIER.ordinal()) {
				action = PlayCourierActions.valueOf(actionString.toUpperCase())
						.getAction();
			} else {
				action = PlaysActions.valueOf(actionString.toUpperCase())
						.getAction();
			}
			action.execute(request, response);
			if (!response.isCommitted()) {
				jump(action.getDoneUrl(), request, response);
			}
		} catch (IllegalArgumentException e) {
			jumpError(Constants.INDEX_JSP, Constants.INTERNAL_ERROR, request, response);
			return;
		} catch (DAOException e) {
			jumpError(action.getErrorUrl(), e.getMessage(), request, response);
			return;
		}

	}

}
