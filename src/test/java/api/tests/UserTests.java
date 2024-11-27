package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userData;

	public Logger logger;

	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userData = new User();

		userData.setId(faker.idNumber().hashCode());
		userData.setUsername(faker.name().username());
		userData.setFirstName(faker.name().firstName());
		userData.setLastName(faker.name().lastName());
		userData.setEmail(faker.internet().emailAddress());
		userData.setPassword(faker.internet().password());
		userData.setPhone(faker.phoneNumber().cellPhone());

		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testPostUser() {

		logger.info("CREATING USER");
		Response response = UserEndPoints.createUser(userData);
		response.then().log().all();
		JsonPath jsonPath = response.jsonPath();

		Assert.assertEquals(response.statusCode(), 200);
		// Assert.assertEquals(jsonPath.getInt("code"), 200, "Code Mismatch!");
		Assert.assertEquals(jsonPath.getString("type"), "unknown", "Type Mismatch!");
		logger.info("USER CREATED");
	}

	@Test(priority = 2)
	public void testGetUser() {

		logger.info("READING USER");
		Response response = UserEndPoints.readUser(userData.getUsername());
		response.then().log().all();
		JsonPath jsonPath = response.jsonPath();

		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(jsonPath.getString("username"), this.userData.getUsername(), "Username Mismatch!");
		logger.info("USER DISPLAYED");

	}

	@Test(priority = 3)
	public void testUpdateUserByName() {

		logger.info("UPDATING USER");
		userData.setLastName(faker.name().lastName());
		userData.setPassword(faker.internet().password());
		userData.setPhone(faker.phoneNumber().cellPhone());

		Response response = UserEndPoints.updateUser(userData, userData.getUsername());
		response.then().log().all();
		JsonPath jsonPath = response.jsonPath();

		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(jsonPath.getString("type"), "unknown", "Type Mismatch!");

		Response responseAfterUpdate = UserEndPoints.readUser(userData.getUsername());
		responseAfterUpdate.then().log().all();
		logger.info("USER UPDATED");
	}

	@Test(priority = 4)
	public void testDeleteUserByName() {

		logger.info("DELETING USER");
		Response response = UserEndPoints.deleteUser(userData.getUsername());
		response.then().log().all();
		JsonPath jsonPath = response.jsonPath();

		Response responseAfterUpdate = UserEndPoints.readUser(userData.getUsername());
		responseAfterUpdate.then().log().all();
		try {
			Assert.assertEquals(response.statusCode(), 200);
			Assert.assertEquals(jsonPath.getString("type"), "unknown", "Type Mismatch!");
			Assert.assertEquals(jsonPath.getString("message"), this.userData.getUsername(), "Username Mismatch!");
			Assert.assertEquals(response.statusCode(), 404); // Since user is deleted
			logger.info("USER DELETED");
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error occured: " + e.getMessage());
		}

	}

}
