package com.objectfrontier.training.service.exceptionhandling;

public enum ExceptionCodes {

	STREET_NULL(1, "Street is empty"),
	CITY_NULL(2, "City is empty"),
	POSTALCODE_NULL(3, "Postalcode is empty"),
	ID_UNAVAILABLE(4, "Entered Id is not available in the table"),
	FIRST_NAME_NULL(5, "Entered firstName is null"),
	LAST_NAME_NULL(6, "Entered last name is null"),
	EMAIL_NULL(7, "Entered emailId is null"),
	DATE_OF_BIRTH_NULL(8, "Entered date of birth is null"),
	DATE_FORMAT_EXCEPTION(9, "Entered date is not in dd-MM-yyyy format"),
	NAME_DUPLICATE_EXCEPTION(10, "The FirstName and LastName are duplicated"),
	EMAIL_DUPLICATE_EXCEPTION(11, "The emailId entered is duplicated"),
	NO_ELEMENT_FOUND_EXCEPTION(12, "There is no element found for given search elements"),
	CONNECTION_NOT_FOUND_EXCEPTION(13, "Connection is not established properly"),
	ADDRESS_NULL_EXCEPTION(14, "Address is not inserted properly");

	private final int id;
	private final String errorMessage;

	ExceptionCodes(int id, String errorMessage) {
		this.id = id;
		this.errorMessage = errorMessage;
	}

	public int getId() {
		return id;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
