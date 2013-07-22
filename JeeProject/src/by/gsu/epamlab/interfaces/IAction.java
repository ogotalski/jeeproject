package by.gsu.epamlab.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.exceptions.DAOException;

public interface IAction {
public String getDoneUrl();
public String getErrorUrl();
public void execute(HttpServletRequest request, HttpServletResponse response) throws DAOException;

}
