package com.objectfrontier.training.service.pojo;

import java.sql.Date;

public class Person {

	private long id;
	private String first_name;
	private String last_name;
	private String email;
	private String birth_date;
	private Date created_date;
	private Address address;

	public Person() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public Person(String first_name, String last_name, String email,
				  String birth_date, Address address) {

		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.birth_date = birth_date;
		this.address = address;
	}

	public Person(long id, String first_name, String last_name, String email,
			String birth_date, Address address) {

		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.birth_date = birth_date;
		this.address = address;
	}


	public Person(long id, String first_name, String last_name, String email,
			String birth_date, Date created_date, Address address) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.birth_date = birth_date;
		this.created_date = created_date;
		this.address = address;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public Person(String first_name, String last_name, String email,
			String birth_date) {

		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.birth_date = birth_date;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", first_name=" + first_name
				   + ", last_name=" + last_name + ", email=" + email
				   + ", birth_date=" + birth_date + ", created_date="
				   + created_date + ", address=" + address + "]";
	}

}
