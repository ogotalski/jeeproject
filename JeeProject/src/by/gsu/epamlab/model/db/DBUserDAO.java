package by.gsu.epamlab.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.enums.Role;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IUserDAO;

public class DBUserDAO implements IUserDAO {

	private static final int ID_INDEX = 6;
	private static final int PHONE_INDEX = 5;
	private static final int EMAIL_INDEX = 4;
	private static final int ROLE_INDEX = 3;
	private static final int PASS_INDEX = 2;
	private static final int NAME_INDEX = 1;
	private static final String INSERT_LOGIN_QUERY = "INSERT  INTO logins(name, password, roleid, email, phone) VALUES (?,?,?,?,?);";
	private static final String SELECT_USER_QUERY = "SELECT name, password, roleid, email, phone, id FROM logins WHERE name = ? AND password = ?;";
	private static final String SELECT_USERNAME_QUERY = "SELECT name FROM logins WHERE name = ?;";

	@Override
	public User getUser(String name, String pass) throws DAOException {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet rs = null;
		try {
			connection = DBConnector.getConnection();
			selectStatement = connection.prepareStatement(SELECT_USER_QUERY);
			selectStatement.setString(NAME_INDEX, name);
			selectStatement.setString(PASS_INDEX, pass);
			rs = selectStatement.executeQuery();
			if (!rs.next()) {
				throw new DAOException(Constants.INVALID_NAME_OR_PASSWORD);
			}
			return new User(rs.getString(NAME_INDEX), rs.getString(PASS_INDEX),
					rs.getString(EMAIL_INDEX), rs.getString(PHONE_INDEX),
					Role.values()[rs.getInt(ROLE_INDEX)],rs.getInt(ID_INDEX));
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} catch (IllegalArgumentException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);

		} finally {

			DBConnector.closeConnection(connection, selectStatement, rs);
			
		}
	}

	@Override
	public void addUser(String name, String pass, Role role, String email,
			String phone) throws DAOException {

		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement insertStatement = null;
		ResultSet rs = null;
		try {
			User.validate(name, pass, email, phone, role);
			connection = DBConnector.getConnection();
			statement = connection.prepareStatement(SELECT_USERNAME_QUERY);
			statement.setString(NAME_INDEX, name);
			insertStatement = connection.prepareStatement(INSERT_LOGIN_QUERY);
			insertStatement.setString(NAME_INDEX, name);
			insertStatement.setString(PASS_INDEX, pass);
			insertStatement.setInt(ROLE_INDEX, role.ordinal());
			insertStatement.setString(EMAIL_INDEX, email);
			insertStatement.setString(PHONE_INDEX, phone);
			synchronized (DBUserDAO.class) {
				rs = statement.executeQuery();
				if (rs.next()) {
					throw new DAOException(Constants.INVALID_NAME);
				}
				insertStatement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} catch (IllegalArgumentException e) {
			throw new DAOException(Constants.ILLEGAL_ARGUMENT+ e.getMessage());
		} finally {
			if (insertStatement != null) {
				try {
					insertStatement.close();
				} catch (SQLException e) {
					throw new DAOException(Constants.INTERNAL_ERROR, e);
				}
			}
			DBConnector.closeConnection(connection,statement, rs);
			

		}
	}

}
