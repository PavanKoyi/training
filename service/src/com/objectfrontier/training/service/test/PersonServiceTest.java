package com.objectfrontier.training.service.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.service.ConnectionManager;
import com.objectfrontier.training.service.exceptionhandling.AppException;
import com.objectfrontier.training.service.exceptionhandling.ExceptionCodes;
import com.objectfrontier.training.service.pojo.Address;
import com.objectfrontier.training.service.pojo.Person;
import com.objectfrontier.training.service.services.PersonService;

@Test
public class PersonServiceTest {

	ConnectionManager connectionManager = new ConnectionManager();
	Connection connection;
	boolean flag;

	@BeforeTest
	public Connection init() throws SQLException, IOException {

		connection = connectionManager.initConnection();
		return connection;
	}

/*	@AfterClass
	private void truncate() throws SQLException {

		connection.createStatement().execute("DELETE FROM service_person");
		connection.createStatement().execute(" ALTER TABLE service_person AUTO_INCREMENT = 1");
		connection.createStatement().execute("DELETE FROM service_address");
		connection.createStatement().execute(" ALTER TABLE service_address 	AUTO_INCREMENT = 1");

	}
*/

	@AfterTest
	private void release() throws SQLException {
		connectionManager.releaseConnection(connection, flag);
	}

	@Test(dataProvider = "dpCreate_positive", priority = 1, enabled = true)
	private void testCreate_positive (Person person,
									  Person expectedPerson) throws SQLException, ParseException {

		try {
			PersonService personService = new PersonService();
			Person actualPerson = personService.create(connection, person);
			Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
			flag = true;
		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpCreate_positive () throws IOException {

		Path path = Paths.get("D:\\dev\\training\\koyi.raghavarao\\service\\dataFiles\\createPositive.csv");
        Stream<String> in = Files.lines(path);

        List<String[]> inputList = in.map(a -> a.split(",")).collect(Collectors.toList());
        List<Person> actualPersonList = new ArrayList<>();
        List<Person> expectedPersonList =  new ArrayList<>();
        in.close();

        for (String[] personData : inputList) {

        	Address address = new Address(Integer.parseInt(personData[6]), personData[4], personData[5]);
        	Person person = new Person(personData[0], personData[1],personData[2], personData[3], address);
        	Person expectedPerson = person;
        	actualPersonList.add(person);
        	expectedPersonList.add(expectedPerson);
		}

        Object[][] personArray = new Object[actualPersonList.size()][2];
        for(int i = 0; i < actualPersonList.size(); i++) {

        	personArray[i][0] = actualPersonList.get(i);
        	personArray[i][1] = expectedPersonList.get(i);

        }
        return personArray;

	}

	@Test(dataProvider = "dpCreate_negative", priority = 2, enabled = false)
	private void testCreate_negative (Person person, String expectedException) throws SQLException, ParseException {

		try {
			PersonService personService = new PersonService();
			personService.create(connection, person);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpCreate_negative () throws SQLException, IOException {

		Address address = new Address(522019, "ChowdaryStreet", "Guntur");
		Person person1 = new Person(null, "Kumar", "pavan@gmail.com", "04-09-1997", address);
		Person person2 = new Person("Pavan", null, "pavankumar@gmail.com", "04-09-1997", address);
		Person person3 = new Person("Pavan", "Chowdary", "pavanchowdary@gmail.com", null, address);
		Person person4 = new Person("Pavan", "Kumar", "pavanKumar@gmail.com", "04-09-1997", address);
		Person person5 = new Person("Pavan", "Chowdary", "pavan@gmail.com", "04-09-1997", address);
		Person person6 = new Person("PavanK", "ChowdaryK", null , "04-09-1997", address);

		String expectedException1 = ExceptionCodes.FIRST_NAME_NULL.getErrorMessage();
		String expectedException2 = ExceptionCodes.LAST_NAME_NULL.getErrorMessage();
		String expectedException3 = ExceptionCodes.DATE_OF_BIRTH_NULL.getErrorMessage();
		String expectedException4 = ExceptionCodes.NAME_DUPLICATE_EXCEPTION.getErrorMessage();
		String expectedException5 = ExceptionCodes.EMAIL_DUPLICATE_EXCEPTION.getErrorMessage();
		String expectedException6 = ExceptionCodes.EMAIL_NULL.getErrorMessage();

		return new Object[][]{

			{person1, expectedException1},
			{person2, expectedException2},
			{person3, expectedException3},
			{person4, expectedException4},
			{person5, expectedException5},
			{person6, expectedException6},
		};

	}

	@Test (dataProvider = "dpRead_positive", priority = 3, enabled = false)
	private void testRead_positive (long id, Person expectedPerson) throws SQLException {

		try {
			PersonService personService = new PersonService();
			Person actualPerson = personService.read(connection, id);
			Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpRead_positive () throws SQLException {

		long id = 1;
		Address expectedAddress = new Address(1, 522019, "ChowdaryStreet", "Guntur");
		Person expectedPerson = new Person(1,
										   "Pavan",
										   "Kumar",
										   "pavan@gmail.com",
										   "1997-09-04",
										   expectedAddress);

		return new Object[][] {

			{id, expectedPerson}

		};
	}

	@Test (dataProvider = "dpRead_negative", priority = 4, enabled = false)
	private void testRead_negative (long id, String expectedException) throws SQLException {

		try {
			PersonService personService = new PersonService();
			personService.read(connection, id);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpRead_negative () throws SQLException {

		long id = 101;
		String expectedException = ExceptionCodes.ID_UNAVAILABLE.getErrorMessage();

		return new Object[][] {
			{id, expectedException}
		};
	}

	@Test(dataProvider = "dpUpdate_positve", priority = 5, enabled = false)
	public void testUpdate_Positive(Person person, Person expectedPerson) throws Exception {

		try {
			PersonService personService = new PersonService();
			Person actualPerson = personService.update(connection, person);
			Assert.assertEquals(actualPerson.toString(), expectedPerson.toString());
			flag = true;
		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	public Object[][] dpUpdate_positve() throws Exception {

		Address address = new Address(1,522019, "ChowdaryStreet", "Guntur");
		Person person = new Person(1,"Pavan", "kkkkk", "kkk@gmail.com", "04-09-1997", address);

		Address expectedAddress = new Address(1, 522019, "ChowdaryStreet", "Guntur");
		Person expectedPerson = new Person(1, "Pavan", "kkkkk", "kkk@gmail.com", "04-09-1997", expectedAddress);

		return new Object[][]{

			{person, expectedPerson}

			};
	}

	@Test(dataProvider = "dpUpdate_negative", priority = 6, enabled = false)
	public void testUpdate_negative(Person person, String expectedException) throws Exception {

		try {
			PersonService personService = new PersonService();
			personService.update(connection, person);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	public Object[][] dpUpdate_negative() throws Exception {

		Address address = new Address(1,522019, "ChowdaryStreet", "Guntur");
		Person person = new Person(22,"Kalyan", "Cbjhkdrg", "kalyan@gmail.com", "04-09-1997", address);

		String expectedException = ExceptionCodes.ID_UNAVAILABLE.getErrorMessage();

		return new Object[][]{
			{person, expectedException}
			};
	}

	@Test(dataProvider = "dpDelete_positive", priority = 7, enabled = false)
	private void testDelete_positive (long id, int expectedAffectedRows) throws SQLException {

		try {
			PersonService personService = new PersonService();
			int actualAffecedRows = personService.delete(connection, id);
			Assert.assertEquals(actualAffecedRows, expectedAffectedRows);
			flag = true;
		} catch (AppException e) {
		flag = false;
		}
	}

	@DataProvider
	private Object[][] dpDelete_positive () throws SQLException {

		long id =1;
		int expectedAffectedRows = 1;

		return new Object[][] {
			{id, expectedAffectedRows}
		};
	}

	@Test (dataProvider = "dpDelete_nagative", priority = 8, enabled = false)
	private void testDelete_negative (long id, String expectedException) throws SQLException {

		try {
			PersonService personService = new PersonService();
			personService.delete(connection, id);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpDelete_nagative () throws SQLException {

		long id = 1;
		String expectedException = ExceptionCodes.ID_UNAVAILABLE.getErrorMessage();

		return new Object[][] {

			{id, expectedException}
		};
	}

}
