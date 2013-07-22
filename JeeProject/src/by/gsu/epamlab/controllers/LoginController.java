package by.gsu.epamlab.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.enums.LoginActions;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IAction;

public class LoginController extends AbstractController {

	@Override
	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		IAction action = null;
		String actionString = request.getParameter(ConstantsJSP.ACTION);
		if (actionString == null) {
			jump(Constants.INDEX_JSP, request, response);
			return;
		}
		try {
			action = LoginActions.valueOf(actionString.toUpperCase())
					.getAction();
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
	

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
	}

}
