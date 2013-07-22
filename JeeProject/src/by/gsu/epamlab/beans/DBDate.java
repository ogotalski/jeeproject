package by.gsu.epamlab.beans;

import java.sql.Date;

public class DBDate {
   private Date date;
   private int id;
public DBDate() {
	super();
	
	// TODO Auto-generated constructor stub
}
public DBDate(int id, Date date) {
	super();
	this.id = id;
	this.date = date;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
   
}
