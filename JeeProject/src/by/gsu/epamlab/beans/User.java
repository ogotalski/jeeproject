package by.gsu.epamlab.beans;

import java.util.regex.Pattern;

import by.gsu.epamlab.enums.Role;

public class User {
	private String name;
	private String password;
	private String email;
	private String phone;
	private Role role;
	private int id;

	private static final Pattern USER_PATTERN = Pattern.compile("^[_a-zA-Z][_a-zA-Z0-9]{3,8}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+$");
	private static final Pattern PASS_PATTERN = Pattern.compile("^\\w{3,}$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[1-9]{1}[0-9]+$");
	
	public static void validate(String name, String password, String email,
			String phone, Role role) {
		if (name == null || !USER_PATTERN.matcher(name).matches()) {
			throw new IllegalArgumentException(name);
		}
		if (password == null || !PASS_PATTERN.matcher(password).matches()) {
			throw new IllegalArgumentException(password);
		}
		if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
			throw new IllegalArgumentException(email);
		}
		if (phone== null || !PHONE_PATTERN.matcher(phone).matches() ) {
			throw new IllegalArgumentException(phone);
		}
		if (role == null) {
			throw new IllegalArgumentException();
		}
	}

	public User() {

		// TODO Auto-generated constructor stub
	}

	public User(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public User(String name, String password, String email, String phone,
			Role role, int id) {
		
		validate(name, password, email, phone, role);
		if (id < 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(";");
		builder.append(password);
		builder.append(";");
		builder.append(role);
		builder.append(";");
		builder.append(email);
		builder.append(";");
		builder.append(phone);
		builder.append(";");
		builder.append(id);
		return builder.toString();
	}

}
