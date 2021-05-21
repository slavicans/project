package com.iktpreobuka.project.entities;

public class UserEntity {
	protected Integer id;
	protected String firstName;
	protected String lastName;
	protected String userName;
	protected String password;
	protected String email;
	protected Roles eUserRole;
	
	public UserEntity() {
		super();
	}


	public UserEntity(Integer id, String firstName, String lastName, String userName, String password, String email,Roles EUserRole) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.eUserRole=EUserRole;
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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


	public Roles geteUserRole() {
		return eUserRole;
	}


	public void seteUserRole(Roles eUserRole) {
		this.eUserRole = eUserRole;
	}



	
}
