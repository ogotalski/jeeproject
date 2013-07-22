package by.gsu.epamlab.beans;

public class Payment {
private int orderId;
private int quantity;
public Payment() {
	super();
	// TODO Auto-generated constructor stub
}
public Payment(int orderId, int quantity) {
	super();
	this.orderId = orderId;
	this.quantity = quantity;
}
public int getOrderId() {
	return orderId;
}
public void setOrderId(int orderId) {
	this.orderId = orderId;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}


}
