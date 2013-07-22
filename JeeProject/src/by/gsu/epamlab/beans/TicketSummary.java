package by.gsu.epamlab.beans;

public class TicketSummary {
     private Ticket ticket;
     private int avalible;
     private int paid;
     private int ordered;
	
     public TicketSummary() {
		ticket = null;
		paid = 0;
		ordered = 0;
		avalible = 0;
	}
     
	public TicketSummary(Ticket ticket) {
		this();
		this.ticket = ticket;
		avalible = ticket.getCount();
	}

	public TicketSummary(Ticket ticket, int avalible) {
		this();
		this.ticket = ticket;
		this.avalible = avalible;
	}

	public TicketSummary(Ticket ticket,int avalible, int paid, int ordered) {
		
		//TODO throws
		this();
		this.ticket = ticket;
		
		this.avalible = avalible;
		this.paid = paid;
		this.ordered = ordered;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public int getAvalible() {
		return avalible;
	}

	public void setAvalible(int avalible) {
		this.avalible = avalible;
	}

	public void calcAvalible (int ordered){
		avalible = ticket.getCount()- ordered;
	}
	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public int getOrdered() {
		return ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}


     
	
}
