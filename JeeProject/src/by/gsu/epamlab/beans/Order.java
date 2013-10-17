package by.gsu.epamlab.beans;

public class Order {
	private Ticket ticket;
	private int quantity;
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(Ticket ticket, int quantity) {
		super();
		this.ticket = ticket;
		this.quantity = quantity;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSum(){
		return ticket.getPrice()*quantity;
	}
}
