package by.gsu.epamlab.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsJSP;

/**
 * Servlet implementation class AbstractController
 */
public abstract class AbstractController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
  



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType(Constants.CONTENT_TYPE);
    	performTask(req,resp);
	}

   

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType(Constants.CONTENT_TYPE);
		performTask(req,resp);
	}
	
	protected void jump(String url, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
		rd.forward(request, response);
	}

	protected void jumpError(String url,String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ConstantsJSP.ERROR_MESSAGE, message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
		rd.forward(request, response);

	}
	
	protected abstract void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;


// 	TODO
//	protected String getRefferUri(HttpServletRequest request) {
//		return getUri(request, ConstantsJSP.FROM);
//	}
//	protected String getErrorUri(HttpServletRequest request) {
//		return getUri(request, ConstantsJSP.ERROR_URL);
//	}
//	protected String getUri(HttpServletRequest request , String name) {
//		String url = request.getParameter(name);
//	    int contextLength = request.getContextPath().length();
//		if (url== null || url.length()<contextLength) {
//			url=Constants.SLASH+Constants.LOGOUT_URL;
//		} else {
//		url = url.substring(request.getContextPath().length());
//		}
//		return url;
//	}
 
	
	
}
