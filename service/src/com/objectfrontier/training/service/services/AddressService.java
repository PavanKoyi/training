package com.objectfrontier.training.service.services;

import static com.objectfrontier.training.service.interfaces.Statements.ADDRESS_CREATE_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.ADDRESS_DELETE_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.ADDRESS_READALL_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.ADDRESS_READ_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.ADDRESS_UPDATE_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.EMAIL_VALIDATION_STATEMENT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.objectfrontier.training.service.exceptionhandling.AppException;
import com.objectfrontier.training.service.exceptionhandling.ExceptionCodes;
import com.objectfrontier.training.service.pojo.Address;

public class AddressService {

	public boolean validate(Address address) {

		if (address.getStreet() == null
				|| address.getStreet().trim().length() == 0) {
			throw new AppException(ExceptionCodes.STREET_NULL);
		} else if (address.getCity() == null
				|| address.getCity().trim().length() == 0) {
			throw new AppException(ExceptionCodes.CITY_NULL);
		} else if (address.getPostalCode() == 0) {
			throw new AppException(ExceptionCodes.POSTALCODE_NULL);
		} else
			return true;
	}

	public List<Address> search(Connection connection, String searchElement)
			throws SQLException {

		PreparedStatement searchQuery = connection.prepareStatement(EMAIL_VALIDATION_STATEMENT.toString());
		searchQuery.setString(1, "%" + searchElement + "%");
		ResultSet rs = searchQuery.executeQuery();

		List<Address> foundList = new ArrayList<>();
		Address address = null;

		while (rs.next()) {
			address = constructAddress(rs);
			foundList.add(address);
		}

		if (foundList.isEmpty()) {
			throw new AppException(ExceptionCodes.NO_ELEMENT_FOUND_EXCEPTION);
		}

		return foundList;
	}

	public Address create(Connection conn, Address address) throws SQLException {

		boolean isValidAddress = validate(address);

		if (isValidAddress == true) {

			PreparedStatement createQuery = conn.prepareStatement(ADDRESS_CREATE_STATEMENT.toString(),
																  Statement.RETURN_GENERATED_KEYS);

				createQuery.setString(1, address.getStreet());
				createQuery.setString(2, address.getCity());
				createQuery.setInt(3, address.getPostalCode());
				createQuery.executeUpdate();
				ResultSet rs = createQuery.getGeneratedKeys();

				if (rs.next()) {
					address.setId(rs.getInt(1));
				}


		}

		return address;
	}

	public Address update(Connection conn, Address address) throws SQLException {

		int affectedRows;

			PreparedStatement updateSql = conn.prepareStatement(ADDRESS_UPDATE_STATEMENT.toString());

			updateSql.setString(1, address.getStreet());
			updateSql.setString(2, address.getCity());
			updateSql.setInt(3, address.getPostalCode());
			updateSql.setLong(4, address.getId());
			affectedRows = updateSql.executeUpdate();


		if (affectedRows >= 1) {
			return address;
		} else {
			throw new AppException(ExceptionCodes.ID_UNAVAILABLE);
		}

	}

	public Address read(Connection conn, long id) throws SQLException {

		Address address;

		PreparedStatement readSql = conn.prepareStatement(ADDRESS_READ_STATEMENT.toString());

			readSql.setLong(1, id);

			ResultSet rs = readSql.executeQuery();

			address = null;
			while (rs.next()) {
				address = constructAddress(rs);
			}

		if (address == null) {
			throw new AppException( ExceptionCodes.ID_UNAVAILABLE);
		}

		return address;

	}

	private Address constructAddress(ResultSet rs) throws SQLException {

		Address address = new Address();

			address.setId(rs.getInt("id"));
			address.setStreet(rs.getString("street"));
			address.setCity(rs.getString("city"));
			address.setPostalCode(rs.getInt("postal_code"));


		return address;

	}

	public List<Address> readAll(Connection conn) throws SQLException {

		List<Address> addressList;

			PreparedStatement readAllSql = conn.prepareStatement(ADDRESS_READALL_STATEMENT.toString());

			ResultSet rs = readAllSql.executeQuery();

			addressList = new ArrayList<>();

			Address address = null;
			while (rs.next()) {

				address = constructAddress(rs);
				addressList.add(address);
			}


		return addressList;

	}

	public int delete(Connection connection, long id) throws SQLException {

		int affectedRows;

			PreparedStatement deleteQuery = connection.prepareStatement(ADDRESS_DELETE_STATEMENT.toString());
			deleteQuery.setLong(1, id);
			affectedRows = deleteQuery.executeUpdate();


		if (affectedRows != 1) {
			throw new AppException(ExceptionCodes.ID_UNAVAILABLE);
		}

		return affectedRows;
	}

}
