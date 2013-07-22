package by.gsu.epamlab.beans;

public class Ticket {
	
	private String name;
	private int id;
	private int count;
	private int price;
	 
	public Ticket() {
		name = null;
		id = 0;
		count = 0;
		price = 0;
	}
	
	

	public Ticket(int id, String name, int count, int price) {
		
		this.id = id;
		this.name = name;
		this.count = count;
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getId(){
		return id;
		
	}
	public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(int id) {
		this.id = id;
	}

}
