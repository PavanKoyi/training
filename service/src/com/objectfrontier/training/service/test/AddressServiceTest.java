package com.objectfrontier.training.service.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.service.ConnectionManager;
import com.objectfrontier.training.service.exceptionhandling.AppException;
import com.objectfrontier.training.service.exceptionhandling.ExceptionCodes;
import com.objectfrontier.training.service.pojo.Address;
import com.objectfrontier.training.service.services.AddressService;

public class AddressServiceTest {

	AddressService addressService;
	ConnectionManager connectionManager = new ConnectionManager();
	Connection connection;
	boolean flag;

	@BeforeTest
	public Connection init() throws SQLException, IOException {

		connection = connectionManager.initConnection();
		addressService = new AddressService();
		return connection;
	}

	@AfterTest
	private void release() throws SQLException {

		connectionManager.releaseConnection(connection, flag);

	}

	@AfterClass
	private void truncate() throws SQLException {

		connection.createStatement().execute("DELETE FROM service_address");
		connection.createStatement().execute(" ALTER TABLE service_address 	AUTO_INCREMENT = 1");

	}

	@Test(dataProvider = "dpCreate_positive", priority = 1, enabled = false)
	private void testCreate_positive(Connection connection, Address address, Address expectedAddress) throws SQLException {

		try {
			Address actualAddress = addressService.create(connection, address);
			Assert.assertEquals(actualAddress.toString(),expectedAddress.toString());
			flag = true;
		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpCreate_positive() throws SQLException, IOException {

		connection = connectionManager.initConnection();

		Address address = new Address(1,522019, "rangaraoStreet", "Guntur");
		Address expectedAddress = new Address(1,522019, "rangaraoStreet", "Guntur");

		return new Object[][]{
			{connection, address, expectedAddress}
			};
	}


	@Test(dataProvider = "dp_create_negative", priority = 2, enabled = true)
	private void test_create_nagative (Address address,
										List<ExceptionCodes> errorCodes) throws SQLException {

		try {
			AddressService addressService = new AddressService();
			addressService.create(connection, address);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorCode(), errorCodes);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dp_create_negative () {

		Address address = new Address(522019, null, null);

		List<ExceptionCodes> errorCodes = new ArrayList<>();
		errorCodes.add(ExceptionCodes.STREET_NULL);
		errorCodes.add(ExceptionCodes.CITY_NULL);
		errorCodes.add(ExceptionCodes.POSTALCODE_NULL);

		return new Object[][] {
			{connection, address, errorCodes},
		};

	}

	@Test(dataProvider = "dpRead_positive", priority = 3, enabled = false)
	private void testRead_positive(long id,
									Address expectedAdrdess) throws SQLException {

		try {
			Address actualAddress = addressService.read(connection, id);
			Assert.assertEquals(actualAddress.toString(), expectedAdrdess.toString());
			flag = true;
		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpRead_positive() throws SQLException {

		long id = 1;
		Address expectedAddress = new Address(1,522019, "rangaraoStreet", "Guntur");

		return new Object[][]{

			{id, expectedAddress}

		};
	}

	@Test(dataProvider = "dpRead_negative", priority = 4, enabled = false)
	private void testRead_negative(long id, String expectedException) throws SQLException {

		try {
			addressService.read(connection, id);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpRead_negative() throws SQLException, IOException {

		connectionManager.initConnection();
		long id = 21;

		String expectedException = ExceptionCodes.ID_UNAVAILABLE.getErrorMessage();

		return new Object[][]{

			{id, expectedException}

			};
	}

	@Test (dataProvider = "dpSearch_positive", priority = 5, enabled = false)
	private void testSearch_positive(String searchElement, List<Address> expectedFoundList) throws SQLException {

		try {

			AddressService addressService = new AddressService();
			List<Address> foundList = addressService.search(connection, searchElement);
			System.out.println(foundList.toString());
			Assert.assertEquals(foundList.toString(), expectedFoundList.toString());
			flag = true;

		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpSearch_positive() {

		String searchElement = "ra";
		Address expectedAddress = new Address(1,522019, "rangaraoStreet", "Guntur");
		List<Address> expectedFoundList = new ArrayList<>();
		expectedFoundList.add(expectedAddress);

		return new Object[][] {

			{searchElement, expectedFoundList}

			};

	}

	@Test(dataProvider = "dpUpdate_positive", priority = 6, enabled = false)
	private void testUpdtae_positive(Address address, Address expectedAddress) throws SQLException {

		try {
			Address actualAddress = addressService.update(connection, address);
			Assert.assertEquals(actualAddress.toString(), expectedAddress.toString());
			flag = true;
		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpUpdate_positive() throws SQLException {

		Address address = new Address(1, 522019, "StreetName", "CityName");
		Address expectedAddress = new Address(1, 522019, "StreetName", "CityName");

		return new Object[][]{

			{address, expectedAddress}

		};
	}

	@Test(dataProvider = "dpUpdate_negative", priority = 7, enabled = false)
	private void testUpdtaeAddress_negative(Address address, String expectedException) throws SQLException {

		try {
			AddressService addressService = new AddressService();
			addressService.update(connection, address);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpUpdate_negative() throws SQLException {

		Address address = new Address(21, 522019, "StreetName", "CityName");
		String expectedException = ExceptionCodes.ID_UNAVAILABLE.getErrorMessage();

		return new Object[][]{

			{address, expectedException}

		};

	}

	@Test(dataProvider = "dpDelete_positive", priority = 8, enabled = false)
	private void testDelete_positive(long id) throws SQLException {

		try {
			int affectedRows = addressService.delete(connection, id);
			int expectedRowsToAffect = 1;
			Assert.assertEquals(affectedRows, expectedRowsToAffect);
			flag = true;
		} catch (AppException e) {
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpDelete_positive() throws SQLException {

		long id = 1;
		return new Object[][]{
			{id}
		};
	}

	@Test(dataProvider = "dpDelete_negative", priority = 9, enabled = false)
	private void testDelete_negative(long id, String expectedException)
			throws SQLException {

		try {
			AddressService addressService = new AddressService();
			addressService.delete(connection, id);
			flag = true;
		} catch (AppException e) {
			Assert.assertEquals(e.getErrorMessage(), expectedException);
			flag = false;
		}
	}

	@DataProvider
	private Object[][] dpDelete_negative() throws SQLException {

		long id = 21;
		String expectedException = ExceptionCodes.ID_UNAVAILABLE.getErrorMessage();
		return new Object[][]{
			{id, expectedException}
		};
	}


}
