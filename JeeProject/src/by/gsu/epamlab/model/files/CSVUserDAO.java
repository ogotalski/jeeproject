package by.gsu.epamlab.model.files;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsXML;
import by.gsu.epamlab.enums.Role;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IUserDAO;

public class CSVUserDAO implements IUserDAO {

	private static final int DEFAULT_ID = 0;
	private static final int ID_NUM = 5;
	private static final int PHONE_NUM = 4;
	private static final int EMAIL_NUM = 3;
	private static final int LOGIN_NUM = 0;
	private static final int PASSWORD_NUM = 1;
	private static final int ROLE_NUM = 2;
	private static final String CSV_SPLITTER = ";";
	private static int maxId;
	
	static {
		final String filename = Constants.ResourcePath
				+ ConstantsXML.USERS_FILE_NAME;
		final String CSV_SPLITTER = ";";
		Scanner sc = null;
		maxId = DEFAULT_ID;
			
		try {
				boolean found = false;
				sc = new Scanner(new FileReader(filename));
				Role role = null;
				String[] str = null;
				int id = 0;
				while (sc.hasNext()) {
					str = sc.nextLine().split(CSV_SPLITTER);
					id = Integer.parseInt(str[ID_NUM]);
					if (maxId < id) {
						maxId = id;
					}
				}
			} catch (FileNotFoundException e){
			
			}
	}
	@Override
	public User getUser(String login, String password) throws DAOException {
		final String filename = Constants.ResourcePath
				+ ConstantsXML.USERS_FILE_NAME;
		final String CSV_SPLITTER = ";";
		Scanner sc = null;
		synchronized (CSVUserDAO.class) {
			try {
				boolean found = false;
				sc = new Scanner(new FileReader(filename));
				Role role = null;
				String[] str = null;
				while (sc.hasNext()) {
					str = sc.nextLine().split(CSV_SPLITTER);
					if (str[LOGIN_NUM].equals(login)
							&& str[PASSWORD_NUM].equals(password)) {
						role = Role.valueOf(str[ROLE_NUM].toUpperCase());
						found = true;
						break;
					}
				}
				if (!found) {
					throw new DAOException(Constants.INVALID_NAME_OR_PASSWORD);
				}
				return new User(login, password, str[EMAIL_NUM], str[PHONE_NUM], role, Integer.parseInt(str[ID_NUM]));
			} catch (DAOException e) {
				throw new DAOException(e);
			}

			catch (Exception e) {
				e.printStackTrace();
				throw new DAOException(Constants.INTERNAL_ERROR);
			} finally {
				if (sc != null) {
					sc.close();
				}
			}
		}
	}

	@Override
	public void addUser(String login, String password, Role role, String email,
			String phone) throws DAOException {
		final String filename = Constants.ResourcePath
				+ ConstantsXML.USERS_FILE_NAME;
		Scanner sc = null;
		synchronized (CSVUserDAO.class) {

			try {
				boolean found = false;
				sc = new Scanner(new FileReader(filename));
				while (sc.hasNext()) {
					String[] str = sc.nextLine().split(CSV_SPLITTER);
					if (str[LOGIN_NUM].equals(login)) {
						found = true;
						break;
					}
				}
				if (found) {
					throw new DAOException(Constants.INVALID_NAME);
				}
			} catch (DAOException e) {
				throw new DAOException(e);
			} catch (FileNotFoundException e) {
				// TODO: handle exception
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException(Constants.INTERNAL_ERROR);
			} finally {
				if (sc != null) {
					sc.close();
				}
			}
			PrintWriter writer = null;
			try {
				User user = new User(login, password, email, phone, role, ++maxId);
				writer = new PrintWriter(new FileWriter(filename, true));
				writer.println(user);
			} catch (Exception e) {
				throw new DAOException(e);
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}
}
