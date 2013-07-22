package by.gsu.epamlab.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.gsu.epamlab.constants.ConstantsDB;
import by.gsu.epamlab.exceptions.DAOException;

public class DBConnector {
	static{
		try {
			Class.forName(ConstantsDB.SQL_DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
			
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(ConstantsDB.DATABASE);
	}
	public static void closeStatement( Statement st) {
		if (st != null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws DAOException {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
            throw new DAOException(ConstantsDB.CLOSE_EXCEPTION_STRING, e);
		}
	}
}
