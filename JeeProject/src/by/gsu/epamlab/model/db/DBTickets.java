package by.gsu.epamlab.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.gsu.epamlab.beans.Order;
import by.gsu.epamlab.beans.Payment;
import by.gsu.epamlab.beans.Ticket;
import by.gsu.epamlab.beans.TicketSummary;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.beans.UserOrder;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.ITicketDAO;

public class DBTickets implements ITicketDAO {

	private static final String TICKETS_ORDERED_LESS_THAN_YOU_WANT_TO_PAY = "Tickets ordered less than you want to pay ";
	private static final String CAN_NOT_BOOK_A_TICKET_TO_A_PAST_DATE = "Can not book a ticket to a past date";
	@Override
	public Collection<TicketSummary> getTicketsSummaries(int id, User user)
			throws DAOException {

		final int ORDERED_TICKET_INDEX = 2;
		final int TICKET_TYPE_ID = 1;
		final int DATE_ID_INDEX = 1;
		final String SELECT_AVALIBLE_QUERY = "SELECT categoryId,SUM(quantity) FROM jeeproject.orders WHERE dateId = ? GROUP BY categoryId;";
		final int PAID_INDEX = 3;
		final int USER_ID_INDEX = 2;
		final String SELECT_USER_ORDERED_PAID_TICKET = "SELECT categoryId, SUM(quantity), S.SUM FROM orders	LEFT JOIN ( SELECT orderId ,SUM(quantity) as SUM FROM payment GROUP BY orderId) AS S ON orders.id = S.orderId WHERE dateId = ? AND loginId = ? GROUP BY categoryId";

		Connection connection = null;
		PreparedStatement selectAvalible = null;
		PreparedStatement selectOrdered = null;
		ResultSet rs = null;
		Map<Integer, TicketSummary> map = new HashMap<Integer, TicketSummary>();
		for (Ticket ticket : Constants.tickets.values()) {
			// TODO need TicketMap
			map.put(ticket.getId(), new TicketSummary(ticket));
		}
		boolean flag = false;
		int ticketId = 0;
		TicketSummary currentSummary = null;
		try {
			connection = DBConnector.getConnection();
			selectAvalible = connection.prepareStatement(SELECT_AVALIBLE_QUERY);
			selectAvalible.setInt(DATE_ID_INDEX, id);
			rs = selectAvalible.executeQuery();
			while (rs.next()) {
				flag = true;
				ticketId = rs.getInt(TICKET_TYPE_ID);
				currentSummary = map.get(ticketId);
				currentSummary.calcAvalible(rs.getInt(ORDERED_TICKET_INDEX));
				map.put(ticketId, currentSummary);
			}
			if (flag && user != null) {
				rs.close();
				selectOrdered = connection
						.prepareStatement(SELECT_USER_ORDERED_PAID_TICKET);
				selectOrdered.setInt(DATE_ID_INDEX, id);
				selectOrdered.setInt(USER_ID_INDEX, user.getId());
				rs = selectOrdered.executeQuery();
				while (rs.next()) {
					flag = true;
					ticketId = rs.getInt(TICKET_TYPE_ID);
					currentSummary = map.get(ticketId);
					currentSummary.setPaid(rs.getInt(PAID_INDEX));

					currentSummary.setOrdered(rs.getInt(ORDERED_TICKET_INDEX)
							- currentSummary.getPaid());
					map.put(ticketId, currentSummary);
				}
			}
			return map.values();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} finally {
			DBConnector.closeStatement(selectOrdered);
			DBConnector.closeConnection(connection, selectAvalible, rs);
		}

	}
	
	@Override
	public Collection<TicketSummary> getTicketsSummaries(int id)
			throws DAOException {

		final int ORDERED_TICKET_INDEX = 2;
		final int TICKET_TYPE_ID = 1;
		final int DATE_ID_INDEX = 1;
		final String SELECT_AVALIBLE_QUERY = "SELECT categoryId,SUM(quantity) FROM jeeproject.orders WHERE dateId = ? GROUP BY categoryId;";
		final int PAID_INDEX = 3;
	final String SELECT_USER_ORDERED_PAID_TICKET = "SELECT categoryId, SUM(quantity), S.SUM FROM orders	LEFT JOIN ( SELECT orderId ,SUM(quantity) as SUM FROM payment GROUP BY orderId) AS S ON orders.id = S.orderId WHERE dateId = ? GROUP BY categoryId";

		Connection connection = null;
		PreparedStatement selectAvalible = null;
		PreparedStatement selectOrdered = null;
		ResultSet rs = null;
		Map<Integer, TicketSummary> map = new HashMap<Integer, TicketSummary>();
		for (Ticket ticket : Constants.tickets.values()) {
			// TODO need TicketMap
			map.put(ticket.getId(), new TicketSummary(ticket));
		}
		boolean flag = false;
		int ticketId = 0;
		TicketSummary currentSummary = null;
		try {
			connection = DBConnector.getConnection();
			selectAvalible = connection.prepareStatement(SELECT_AVALIBLE_QUERY);
			selectAvalible.setInt(DATE_ID_INDEX, id);
			rs = selectAvalible.executeQuery();
			while (rs.next()) {
				flag = true;
				ticketId = rs.getInt(TICKET_TYPE_ID);
				currentSummary = map.get(ticketId);
				currentSummary.calcAvalible(rs.getInt(ORDERED_TICKET_INDEX));
				map.put(ticketId, currentSummary);
			}
			if (flag)
			{
				rs.close();
				selectOrdered = connection
						.prepareStatement(SELECT_USER_ORDERED_PAID_TICKET);
				selectOrdered.setInt(DATE_ID_INDEX, id);
				rs = selectOrdered.executeQuery();
				while (rs.next()) {
					flag = true;
					ticketId = rs.getInt(TICKET_TYPE_ID);
					currentSummary = map.get(ticketId);
					currentSummary.setPaid(rs.getInt(PAID_INDEX));

					currentSummary.setOrdered(rs.getInt(ORDERED_TICKET_INDEX)
							- currentSummary.getPaid());
					map.put(ticketId, currentSummary);
				}
			}
			return map.values();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} finally {
			DBConnector.closeStatement(selectOrdered);
			DBConnector.closeConnection(connection, selectAvalible, rs);
		}

	}
	@Override
	public void order(int dateId, User user, List<Order> orders)
			throws DAOException {
		final int TICKET_TYPE_INDEX = 2;
		final int DATE_ID_INDEX = 1;
		final int USER_ID_INDEX = 2;
		final String DELIMITER = "; ";
		final int ORDERED_INDEX = 1;
		final int QUANTITY_INDEX = 4;
		final int TICKET_ID_INDEX = 3;
		final String DATE_SELECT = "SELECT id FROM dates WHERE date >= CURDATE() AND id = ?;";
		final String INSERT_ORDER = "INSERT INTO orders(dateId, loginId,categoryId,date,quantity) VALUES (?,?,?,NOW(),?);";
		final String SELECT_AVALIBLE = "SELECT SUM(orders.quantity) FROM orders WHERE orders.dateId = ? AND orders.categoryId = ?; ";

		Connection connection = null;
		PreparedStatement selectAvalible = null;
		PreparedStatement insertOrder = null;
		PreparedStatement dateSelect = null;
		StringBuilder errorString = new StringBuilder();
		ResultSet rs = null;
		int avalible = 0;
		try {
			connection = DBConnector.getConnection();
			dateSelect = connection.prepareStatement(DATE_SELECT);
			dateSelect.setInt(DATE_ID_INDEX, dateId);
			if (!dateSelect.executeQuery().next()){
				throw new DAOException(CAN_NOT_BOOK_A_TICKET_TO_A_PAST_DATE);
			}
			selectAvalible = connection.prepareStatement(SELECT_AVALIBLE);
			insertOrder = connection.prepareStatement(INSERT_ORDER);
			for (Order order : orders) {
				selectAvalible.setInt(DATE_ID_INDEX, dateId);
				selectAvalible.setInt(TICKET_TYPE_INDEX, order.getTicket()
						.getId());
				insertOrder.setInt(DATE_ID_INDEX, dateId);
				insertOrder.setInt(USER_ID_INDEX, user.getId());
				insertOrder.setInt(TICKET_ID_INDEX, order.getTicket().getId());
				insertOrder.setInt(QUANTITY_INDEX, order.getQuantity());
				synchronized (DBTickets.class) {
					rs = selectAvalible.executeQuery();
					if (!rs.next()) {
						throw new DAOException(Constants.INTERNAL_ERROR);
					}
					avalible = order.getTicket().getCount()
							- rs.getInt(ORDERED_INDEX);
					if (avalible < order.getQuantity()) {
						errorString.append(order.getTicket().getName());
						errorString.append(DELIMITER);

					} else {
						insertOrder.executeUpdate();
					}
				}

			}
			if (errorString.length() > 0) {
				throw new DAOException(Constants.NOT_ENOUGH_TICKETS
						+ errorString.toString());
			}
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR);
		} finally {
            DBConnector.closeStatement(dateSelect);
			DBConnector.closeStatement(selectAvalible);
			DBConnector.closeConnection(connection, insertOrder, rs);
		}

	}

	@Override
	public void cancelOrder(int dateId, User user) throws DAOException {
		final int LOGIN_ID_INDEX = 2;
		final int DATE_ID_INDEX = 1;
		final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE orders.dateId = ? AND orders.loginId = ? AND orders.id NOT IN (SELECT payment.orderId FROM payment);";
		final String UPDATE_ORDER_QUERY = "UPDATE orders INNER JOIN (SELECT orderId, SUM(quantity) AS S FROM payment GROUP BY orderId) AS SS ON orders.id=SS.orderId SET orders.quantity=SS.S  WHERE orders.dateId = ? AND orders.loginId = ?;";

		Connection connection = null;
		PreparedStatement updateOrdered = null;
		PreparedStatement deleteOrdered = null;
		try {
			connection = DBConnector.getConnection();
			deleteOrdered = connection.prepareStatement(DELETE_ORDER_QUERY);
			deleteOrdered.setInt(DATE_ID_INDEX, dateId);
			deleteOrdered.setInt(LOGIN_ID_INDEX, user.getId());
			deleteOrdered.executeUpdate();
			updateOrdered = connection.prepareStatement(UPDATE_ORDER_QUERY);
			updateOrdered.setInt(DATE_ID_INDEX, dateId);
			updateOrdered.setInt(LOGIN_ID_INDEX, user.getId());
			updateOrdered.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);

		} finally {
			DBConnector.closeStatement(deleteOrdered);
			DBConnector.closeConnection(connection, updateOrdered, null);
		}

	}

	@Override
	public List<UserOrder> getUserOrders(Integer dateId) throws DAOException {
		final int ORDER_ID_INDEX = 1;
		final int DATE_ID_INDEX = 1;
		final int LOGIN_INDEX = 2;
		final int LOGIN_EMAIL = 3;
		final int LOGIN_PHONE = 4;
		final int DATE_INDEX = 5;
		final int TICKET_TYPE_INDEX = 6;
		final int ORDER_QUANTITY_INDEX = 7;
		final int PAYMENT_QUANTITY_INDEX = 8;
		final String SELECT_QUERY = "SELECT orders.id, logins.name, logins.email, logins.phone, orders.date, orders.categoryId, orders.quantity, SS.sQuantity FROM orders INNER JOIN logins ON orders.loginId = logins.id LEFT JOIN (SELECT orderId, SUM(quantity) AS sQuantity FROM payment GROUP BY payment.orderId) AS SS ON SS.orderId = orders.id WHERE orders.dateId = ? AND ((orders.quantity - SS.sQuantity)> 0 OR SS.sQuantity IS NULL);";

		Connection connection = null;
		PreparedStatement statement = null;
		List<UserOrder> list = new ArrayList<UserOrder>();
		ResultSet rs = null;
		try {
			connection = DBConnector.getConnection();
			statement = connection.prepareStatement(SELECT_QUERY);
			statement.setInt(DATE_ID_INDEX, dateId);
			rs = statement.executeQuery();
			while (rs.next()) {
				list.add(new UserOrder(rs.getInt(ORDER_ID_INDEX), new User (rs
						.getString(LOGIN_INDEX), rs.getString(LOGIN_EMAIL), rs.getString(LOGIN_PHONE)), rs.getDate(DATE_INDEX),
						new Order(Constants.tickets.get(rs
								.getInt(TICKET_TYPE_INDEX)), rs
								.getInt(ORDER_QUANTITY_INDEX)
								- rs.getInt(PAYMENT_QUANTITY_INDEX))));
			}
			return list;
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} finally {
			DBConnector.closeConnection(connection, statement, rs);
		}

	}

	@Override
	public void pay(List<Payment> payList) throws DAOException {
		final String INSERT_QUERY = "INSERT INTO payment( orderId, date, quantity) VALUES (?, NOW(), ?);";
		final String SELECT_QUERY = "SELECT SUM(payment.quantity), orders.quantity FROM  jeeproject.orders LEFT JOIN jeeproject.payment ON payment.orderId = orders.id WHERE orders.id = ?;";
		
		final int ORDER_ID_INDEX = 1;
		final int QUANTITY_INDEX = 2;
		final int PAYMENT_QUANTITY_INDEX = 1;
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement selStatement = null;
		ResultSet rs = null;
		try {
			connection = DBConnector.getConnection();
			// TODO order quantity > avalible
			selStatement = connection.prepareStatement(SELECT_QUERY);
			statement = connection.prepareStatement(INSERT_QUERY);
			for (Payment pay : payList) {
				selStatement.setInt(ORDER_ID_INDEX, pay.getOrderId());
				statement.setInt(ORDER_ID_INDEX, pay.getOrderId());
				statement.setInt(QUANTITY_INDEX, pay.getQuantity());
				synchronized (this.getClass()) {
                    rs = selStatement.executeQuery();
                   if (rs.next() && rs.getInt(QUANTITY_INDEX) - rs.getInt(PAYMENT_QUANTITY_INDEX)>=pay.getQuantity()){
					statement.executeUpdate();
					} else {
						throw new DAOException(TICKETS_ORDERED_LESS_THAN_YOU_WANT_TO_PAY);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);
		} finally {
			DBConnector.closeConnection(connection, statement, rs);
		}

	}

	@Override
	public void cancelOrder(List<Integer> orderIds) throws DAOException {
		final int ORDER_ID_INDEX = 1;
		final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE orders.id = ? AND orders.id NOT IN (SELECT payment.orderId FROM payment);";
		final String UPDATE_ORDER_QUERY = "UPDATE orders INNER JOIN (SELECT orderId, SUM(quantity) AS S FROM payment GROUP BY orderId) AS SS ON orders.id=SS.orderId SET orders.quantity=SS.S  WHERE orders.id = ?;";

		Connection connection = null;
		PreparedStatement updateOrdered = null;
		PreparedStatement deleteOrdered = null;
		try {
			connection = DBConnector.getConnection();
			deleteOrdered = connection.prepareStatement(DELETE_ORDER_QUERY);
			updateOrdered = connection.prepareStatement(UPDATE_ORDER_QUERY);
			for (Integer id : orderIds) {
				deleteOrdered.setInt(ORDER_ID_INDEX, id);
				deleteOrdered.executeUpdate();
				updateOrdered.setInt(ORDER_ID_INDEX, id);
				updateOrdered.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(Constants.INTERNAL_ERROR, e);

		} finally {
			DBConnector.closeStatement(deleteOrdered);
			DBConnector.closeConnection(connection, updateOrdered, null);
		}

	}
}
