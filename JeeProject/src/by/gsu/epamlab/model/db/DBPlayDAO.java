package by.gsu.epamlab.model.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.gsu.epamlab.beans.DBDate;
import by.gsu.epamlab.beans.Play;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IPlayDAO;
import by.gsu.epamlab.model.files.XMLPlaysDAO;

public class DBPlayDAO implements IPlayDAO {

	private static final int IMAGE_INSERT_INDEX = 5;
	private static final int IMAGE_INDEX = 8;
	private static final int DESC_INDEX = 7;
	private static final int DESC_INSERT_INDEX = 4;
	private static final int DATE_ID_INDEX = 6;
	private static final String INSERT_INTO_GENRE = "INSERT INTO genres (name) VALUES (?)";
	private static final String INSERT_INTO_AUTHORS = "INSERT INTO authors (name) VALUES (?)";
	private static final String SELECT_GENRE_ID = "SELECT id FROM genres WHERE name = ?";
	private static final String SELECT_AUTHORS_ID = "SELECT id FROM authors WHERE name = ?";
	private static final String INSERT_INTO_DATE = "INSERT INTO dates (date,playId) VALUES (?,?)";
	private static final String INSERT_INTO_PLAYS = "INSERT INTO plays (name,genreId,authorId,description,image) VALUES (?,?,?,?,?)";
	private static final String SELECT_DATE = "SELECT date FROM dates WHERE date = ?";
	private static final String SELECT_PLAY_ID = "SELECT id FROM plays WHERE name = ?";
	private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	
	private static final int THIRD_INDEX = 3;
	private static final int SECOND_INDEX = 2;
	private static final String SQL_ERROR = "SQL Error";
	private static final int ID_INDEX = 1;
	private static final int FIRST_INDEX = 1;

	private static final int DATE_INDEX = 5;
	private static final int GENRE_INDEX = 4;
	private static final int AUTHOR_INDEX = 3;
	private static final int NAME_INDEX = 2;
	private static final String SELECT_PLAYS = "SELECT plays.id, plays.name, authors.name, genres.name, dates.date, dates.id, plays.description, plays.image FROM plays LEFT JOIN authors ON plays.authorId = authors.id LEFT JOIN genres ON plays.genreId = genres.id LEFT JOIN dates ON plays.id = dates.playId ORDER BY plays.id; ";
	private static final String SELECT_ONE_PLAY = "SELECT plays.id, plays.name, authors.name, genres.name, dates.date, dates.id, plays.description, plays.image  FROM (plays LEFT JOIN authors ON plays.authorId = authors.id LEFT JOIN genres ON plays.genreId = genres.id LEFT JOIN dates ON plays.id = dates.playId) WHERE plays.id IN (SELECT dates.playId FROM dates WHERE dates.id = ?) ORDER BY plays.id; ";
	@Override
	public List<Play> getPlaysList() throws DAOException {
		List<Play> list = new ArrayList<Play>();
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet rs = null;
		try {
			connection = DBConnector.getConnection();
			selectStatement = connection.prepareStatement(SELECT_PLAYS);
			rs = selectStatement.executeQuery();

			int playId = 0;
			Play play = null;
			Date date = null;
			int id;
			if (rs.next()) {
				play = new Play(rs.getString(NAME_INDEX),
						rs.getString(AUTHOR_INDEX), rs.getString(GENRE_INDEX));
				play.addDate(rs.getInt(DATE_ID_INDEX), rs.getDate(DATE_INDEX));
				play.setDescription(rs.getString(DESC_INDEX));
				play.setImageURI(rs.getString(IMAGE_INDEX));
				playId = rs.getInt(ID_INDEX);
			}
			while (rs.next()) {
				id = rs.getInt(ID_INDEX);
				if (id != playId) {
					list.add(play);
					play = new Play(rs.getString(NAME_INDEX),
							rs.getString(AUTHOR_INDEX),
							rs.getString(GENRE_INDEX));
					play.setDescription(rs.getString(DESC_INDEX));
					play.setImageURI(rs.getString(IMAGE_INDEX));
					playId = rs.getInt(ID_INDEX);
				}
				date = rs.getDate(DATE_INDEX);
				if (date != null) {
					play.addDate(rs.getInt(DATE_ID_INDEX), date);
				}

			}
			if (play != null) {
				list.add(play);

			}
			return list;
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} finally {

			DBConnector.closeConnection(connection, selectStatement, rs);

		}

	}

	private static int getId(PreparedStatement select,
			PreparedStatement insert, PreparedStatement identy, String name)
			throws SQLException {
		ResultSet rs = null;
		select.setString(FIRST_INDEX, name);
		insert.setString(FIRST_INDEX, name);
		try {
			rs = select.executeQuery();
			if (!rs.next()) {

				rs.close();
				insert.executeUpdate();
				rs = identy.executeQuery();
				if (!rs.next()) {
					throw new SQLException(SQL_ERROR);
				}
			}
			return rs.getInt(ID_INDEX);

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public void addPlays(List<Play> plays) throws DAOException {
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement selectAuthor = null;
		PreparedStatement selectGenre = null;
		PreparedStatement selectPlay = null;
		PreparedStatement selectDate = null;
		PreparedStatement insertAuthor = null;
		PreparedStatement insertGenre = null;
		PreparedStatement insertPlay = null;
		PreparedStatement insertDate = null;
		PreparedStatement selectId = null;
		try {
			connection = DBConnector.getConnection();
			selectPlay = connection.prepareStatement(SELECT_PLAY_ID);
			selectDate = connection.prepareStatement(SELECT_DATE);
			insertPlay = connection.prepareStatement(INSERT_INTO_PLAYS);
			insertDate = connection.prepareStatement(INSERT_INTO_DATE);
			selectAuthor = connection.prepareStatement(SELECT_AUTHORS_ID);
			selectGenre = connection.prepareStatement(SELECT_GENRE_ID);
			insertAuthor = connection.prepareStatement(INSERT_INTO_AUTHORS);
			insertGenre = connection.prepareStatement(INSERT_INTO_GENRE);
			selectId = connection.prepareStatement(SELECT_LAST_INSERT_ID);
			for (Play play : plays) {
				int playId;
				selectPlay.setString(ID_INDEX, play.getName());
				rs = selectPlay.executeQuery();
				if (!rs.next()) {
					rs.close();
					insertPlay.setString(FIRST_INDEX, play.getName());
					insertPlay.setInt(
							SECOND_INDEX,
							getId(selectGenre, insertGenre, selectId,
									play.getGenre()));
					insertPlay.setInt(
							THIRD_INDEX,
							getId(selectAuthor, insertAuthor, selectId,
									play.getAuthor()));
					insertPlay.setString(DESC_INSERT_INDEX, play.getDescription());
					insertPlay.setString(IMAGE_INSERT_INDEX, play.getImageURI());
					
					insertPlay.executeUpdate();
					selectPlay.setString(ID_INDEX, play.getName());
					rs = selectPlay.executeQuery();
					if (!rs.next()) {
						throw new SQLException(SQL_ERROR);
					}
				}
				playId = rs.getInt(ID_INDEX);
				rs.close();
				for (DBDate date : play.getDates()) {
					selectDate.setDate(FIRST_INDEX, date.getDate());
					rs = selectDate.executeQuery();
					if (!rs.next()) {
						insertDate.setDate(FIRST_INDEX, date.getDate());
						insertDate.setInt(SECOND_INDEX, playId);
						insertDate.executeUpdate();
					}
					rs.close();
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBConnector.closeStatement(insertDate);
			DBConnector.closeStatement(insertPlay);
			DBConnector.closeStatement(insertGenre);
			DBConnector.closeStatement(insertAuthor);
			DBConnector.closeStatement(selectDate);
			DBConnector.closeStatement(selectPlay);
			DBConnector.closeStatement(selectGenre);
			DBConnector.closeStatement(selectAuthor);
			DBConnector.closeConnection(connection, null, rs);

		}
	}

	@Override
	public void update() throws DAOException {
		this.addPlays(new XMLPlaysDAO().getPlaysList());
	}

	@Override
	public Play getPlay(int dateID) throws DAOException {
		Play play = null;
		Connection connection = null;
		PreparedStatement selectStatement = null;
		ResultSet rs = null;
		try {
			connection = DBConnector.getConnection();
			selectStatement = connection.prepareStatement(SELECT_ONE_PLAY);
			selectStatement.setInt(FIRST_INDEX, dateID);
			rs = selectStatement.executeQuery();

			Date date = null;
			if (rs.next()) {
				play = new Play(rs.getString(NAME_INDEX),
						rs.getString(AUTHOR_INDEX), rs.getString(GENRE_INDEX));
				play.setDescription(rs.getString(DESC_INDEX));
				play.setImageURI(rs.getString(IMAGE_INDEX));
				play.addDate(rs.getInt(DATE_ID_INDEX), rs.getDate(DATE_INDEX));
			} else {
				throw new DAOException(Constants.INTERNAL_ERROR);
			}
			while (rs.next()) {
				rs.getInt(ID_INDEX);
				date = rs.getDate(DATE_INDEX);
				if (date != null) {
					play.addDate(rs.getInt(DATE_ID_INDEX), date);
				}
			}

			return play;
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} finally {

			DBConnector.closeConnection(connection, selectStatement, rs);

		}

	}

}
