package com.objectfrontier.training.service.pojo;

public class Address {

	private long id;
	private int postalCode;
	private String street;
	private String city;



	public Address() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public Address(int postalCode, String street, String city) {
		this.postalCode = postalCode;
		this.street = street;
		this.city = city;
	}

	public Address(long id, int postalCode, String street, String city) {
		this.id = id;
		this.postalCode = postalCode;
		this.street = street;
		this.city = city;
	}


	public Address(int postalCode, String city) {
		this.postalCode = postalCode;
		this.city = city;
	}

	public Address(String street, String city) {
		this.street = street;
		this.city = city;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city
				+ ", postalCode=" + postalCode + "]";
	}

}
