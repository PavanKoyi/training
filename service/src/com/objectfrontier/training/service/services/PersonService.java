package com.objectfrontier.training.service.services;

import static com.objectfrontier.training.service.interfaces.Statements.EMAIL_VALIDATION_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.NAME_VAIODATION_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.PERSON_CREATE_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.PERSON_DELETE_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.PERSON_READALL_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.PERSON_READ_STATEMENT;
import static com.objectfrontier.training.service.interfaces.Statements.PERSON_UPDATE_STATEMENT;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.objectfrontier.training.service.exceptionhandling.AppException;
import com.objectfrontier.training.service.exceptionhandling.ExceptionCodes;
import com.objectfrontier.training.service.pojo.Address;
import com.objectfrontier.training.service.pojo.Person;

public class PersonService {

	public void validate(Person person) {

		if (person.getFirst_name() == null) {throw new AppException(ExceptionCodes.FIRST_NAME_NULL);}
		else if (person.getLast_name() == null) {throw new AppException(ExceptionCodes.LAST_NAME_NULL);}
		else if (person.getEmail() == null) {throw new AppException(ExceptionCodes.EMAIL_NULL);}
		else if (person.getBirth_date() == null) {throw new AppException(ExceptionCodes.DATE_OF_BIRTH_NULL);}

	}

	public long dateValidator(Person person) {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
			java.util.Date date = dateFormat.parse(person.getBirth_date());
			long newDate = date.toInstant().toEpochMilli();
			return newDate;
		} catch (ParseException e) {
			throw new AppException(ExceptionCodes.DATE_FORMAT_EXCEPTION);
		}

	}

	public void personNameValidate (Connection connection, Person person) throws SQLException {

			PreparedStatement getPersonNameQuery = connection.prepareStatement(NAME_VAIODATION_STATEMENT.toString());

			getPersonNameQuery.setString(1, person.getFirst_name());
			getPersonNameQuery.setString(2, person.getLast_name());
			ResultSet rs = getPersonNameQuery.executeQuery();

			if (rs.next()) {
				throw new AppException(ExceptionCodes.NAME_DUPLICATE_EXCEPTION);
			}
	}

	public void emailValidate(Connection connection, Person person) throws SQLException {

			PreparedStatement getEmailQuery = connection.prepareStatement(EMAIL_VALIDATION_STATEMENT.toString());

			getEmailQuery.setString(1, person.getEmail());
			ResultSet rs = getEmailQuery.executeQuery();

			if (rs.next()) {
				throw new AppException(ExceptionCodes.EMAIL_DUPLICATE_EXCEPTION);
			}

	}

	public  Person create(Connection connection, Person person) throws SQLException {

		validate(person);
		personNameValidate(connection, person);
		emailValidate(connection, person);
		long birthDate = 0;
		try {
			birthDate = dateValidator(person);
		} catch (Exception e1) {
			throw new AppException(ExceptionCodes.DATE_FORMAT_EXCEPTION);
		}

		AddressService addressServ = new AddressService();
		Address address;
			try {
				address = addressServ.create(connection, person.getAddress());
			} catch (Exception e) {
				throw new AppException(ExceptionCodes.ADDRESS_NULL_EXCEPTION);
			}

			PreparedStatement createQuery = connection.prepareStatement(PERSON_CREATE_STATEMENT.toString(),
																		Statement.RETURN_GENERATED_KEYS);

			Timestamp createdDate = new Timestamp (System.currentTimeMillis());

				createQuery.setString(1, person.getFirst_name());
				createQuery.setString(2, person.getLast_name());
				createQuery.setString(3, person.getEmail());
				createQuery.setDate(4, new Date(birthDate));
				createQuery.setLong(5, person.getAddress().getId());
				createQuery.setTimestamp(6, createdDate);
				createQuery.executeUpdate();

				ResultSet rs = createQuery.getGeneratedKeys();
				if(rs.next()) {
					person.setId(rs.getInt(1));
				}

			person.setAddress(address);

		return person;

	}

	public  Person update(Connection connection, Person person) throws SQLException {

		validate(person);
		personNameValidate(connection, person);
		emailValidate(connection, person);
		long birthDate = 0;
		try {
			birthDate = dateValidator(person);
		} catch (Exception e1) {
			throw new AppException(ExceptionCodes.DATE_FORMAT_EXCEPTION);
		}

		int affectedRows;

			PreparedStatement updateQuery = connection.prepareStatement(PERSON_UPDATE_STATEMENT.toString());

			updateQuery.setString(1, person.getFirst_name());
			updateQuery.setString(2, person.getLast_name());
			updateQuery.setString(3, person.getEmail());
			updateQuery.setDate(4, new Date(birthDate));
			updateQuery.setLong(5, person.getId());

			affectedRows = updateQuery.executeUpdate();

		if (affectedRows == 1) {

			AddressService addressServ = new AddressService();

				try {
					addressServ.update(connection, person.getAddress());
				} catch (Exception e) {
					e.printStackTrace();
				}


		} else {
			throw new AppException(ExceptionCodes.ID_UNAVAILABLE);
		}

		return person;

	}

	public Person read(Connection connection, long id) throws SQLException {

		Person person;

			PreparedStatement readSql = connection.prepareStatement(PERSON_READ_STATEMENT.toString());

			readSql.setLong(1, id);

			ResultSet rs = readSql.executeQuery();

			person = null;

			if (rs.next()) {

				person = constructPerson(rs);

				AddressService addressService = new AddressService();
				Address address = addressService.read(connection, id);
				person.setAddress(address);

			} else {
				throw new AppException(ExceptionCodes.ID_UNAVAILABLE);
			}


		return person;

	}

	private Person constructPerson(ResultSet rs) {
		Person person = new Person();
		try {
			person.setId(rs.getLong("id"));
			person.setFirst_name(rs.getString("first_name"));
			person.setFirst_name(rs.getString("last_name"));
			person.setEmail(rs.getString("email"));
			person.setBirth_date((rs.getDate("birth_date").toString()));
			person.setCreated_date(rs.getDate("created_date"));
		} catch (SQLException e) {
			throw new AppException(ExceptionCodes.CONNECTION_NOT_FOUND_EXCEPTION);
		}
		return person;
	}

	public List<Person> readAll(Connection conn) throws SQLException {

		List<Person> personList;

			PreparedStatement readAllSql = conn.prepareStatement(PERSON_READALL_STATEMENT.toString());

			ResultSet rs = readAllSql.executeQuery();
			personList = new ArrayList<>();

			while (rs.next()) {

				Person person = constructPerson(rs);
				long addressId = rs.getLong("address_id");

				AddressService addressService = new AddressService();
				Address address = addressService.read(conn, addressId);
				person.setAddress(address);

				personList.add(person);
			}


		return personList;

	}

	public int delete(Connection conn, long id) throws SQLException {

		int affectedRows;

			PreparedStatement deleteSql = conn.prepareStatement(PERSON_DELETE_STATEMENT.toString());

			deleteSql.setLong(1, id);
			affectedRows = deleteSql.executeUpdate();


		if (affectedRows == 1) {

			AddressService addrServ = new AddressService();
			try {
				addrServ.delete(conn, id);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			throw new AppException(ExceptionCodes.ID_UNAVAILABLE);
		}

		return affectedRows;

	}

	public static void main(String[] args) throws SQLException, AppException {

/*		PersonService personServ = new PersonService();
		//  Connection conn = getConnection();
		Connection conn = ConnectionManager.initConnection();

		//  Person person = new Person();
		Person person = new Person();

		//  Address address = new Address();
		Address address = new Address();

		Scanner sc = new Scanner(System.in);

		//  Creating person procedure
		System.out.println("Enter Street to insert");
		address.setStreet(sc.next());

		System.out.println("Enter city to insert");
		address.setCity(sc.next());
		System.out.println("Enter the Postal_code to insert");
		address.setPostalCode(sc.nextInt());

		System.out.println("Address is created");

		System.out.println("Enter the first_name");
		person.setFirst_name(sc.next());
		System.out.println("Enter the last_name");
		person.setLast_name(sc.next());
		System.out.println("Enter the emailId");
		person.setEmail(sc.next());
		System.out.println("Enter the BirthDate in YYYY-MM-DD format");
//		person.setBirth_date(new Date(birthDate));
		person.setCreated_date(Date.valueOf(LocalDate.now()));

		person.setAddress(address);
		//  createPerson();
//		personServ.create(conn, person);
		conn.commit();


		//  setting person details for update
		System.out.println("Enter the name for update");
		person.setFirst_name(sc.next());
		person.setLast_name(sc.next());
		System.out.println("Enter the Email for update");
		person.setEmail(sc.next());
		System.out.println("Enter the birth_date to update");
		person.setBirth_date(Date.valueOf(sc.next()));

		System.out.println("Enter the personId to be updated");
		person.setId(sc.nextInt());
		System.out.println("Enter addressId for update");
		address.setId(sc.nextLong());
		address.setStreet("SSSSSStreet");
		address.setCity("CCCCCCity");
		address.setPostalCode(522019);

		person.setAddress(address);

		personServ.update(conn, person);



        //  read a person from the person table
		System.out.println("Enter the address_id");
		address.setId(sc.nextInt());
		person.setAddress(address);
		System.out.println("Enter the id of person to read");
		long id = sc.nextLong();
		personServ.read(conn, id);


		// read all the persons from the person table.
		List<Person> personList =  personServ.readAll(conn);
		System.out.println(personList);


		// deleting from person();
		System.out.println("Enter the id where you want to delete");
		person.setId(sc.nextLong());
		System.out.println("Enre the address_id to be deleted at");
		long id1 = sc.nextLong();
		address.setId(id1);
		person.setAddress(address);
		personServ.delete(conn, person);


		sc.close();

	}
*/
}

}