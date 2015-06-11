package demo.model;

import java.io.Serializable;



public class User implements Serializable {

	private Integer id;
	
	private String name;

	private String surname;

	public Integer getId() {
		return id;
	}

	public User() {
		super();
	}

	public User(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public User(Integer id, String name, String surname) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
