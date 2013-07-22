package by.gsu.epamlab.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Play {
     private String name;
     private String author;
     private String genre;
     private String description;
     private String imageURI;
     private List<DBDate> dates;
     public Play() {
    	 //TODO
 	}
	public Play(String name, String author, String ganre) {
		this.name = name;
		this.author = author;
		this.genre = ganre;
		this.description = null;
	    this.imageURI = null;
		this.dates = new ArrayList<DBDate>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String ganre) {
		this.genre = ganre;
	}
	public List<DBDate> getDates() {
		return dates;
	}
	public void setDates(List<DBDate> dates) {
		this.dates = dates;
	}
	
    public void addDate(DBDate date){
    	dates.add(date);
    }
    public void addDate(int id, Date date){
    	dates.add(new DBDate(id,date));
    }
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURI() {
		return imageURI;
	}
	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}
	
    
}
