package by.gsu.epamlab.beans;

import java.sql.Date;

public class UserOrder {
	int orderId;
	User user;
	Date date;
	Order order;

	public UserOrder() {
		user = new User();
	}

	public UserOrder(int orderId, User user, Date date, Order order) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.date = date;
		this.order = order;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
