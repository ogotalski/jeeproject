package by.gsu.epamlab.interfaces;

import java.util.Collection;
import java.util.List;

import by.gsu.epamlab.beans.Order;
import by.gsu.epamlab.beans.Payment;
import by.gsu.epamlab.beans.TicketSummary;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.beans.UserOrder;
import by.gsu.epamlab.exceptions.DAOException;

public interface ITicketDAO {
    
	public Collection<TicketSummary> getTicketsSummaries(int id, User user) throws DAOException;
	public void order(int dateId,User user, List<Order> orders) throws DAOException;
	public void cancelOrder(int dateId, User user) throws DAOException;
	public List<UserOrder> getUserOrders(Integer dateId)throws DAOException;
	public void pay(List<Payment> payList) throws DAOException;
	void cancelOrder(List<Integer> orderIds) throws DAOException;
	public Object getTicketsSummaries(int dateId) throws DAOException;
}
