package by.gsu.epamlab.enums;

public enum Role {
		GUEST,
		USER,
		COURIER,
		ADMIN;
 public String toString (){
			return this.name().toLowerCase();
		}
}
