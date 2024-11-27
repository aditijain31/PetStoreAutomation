package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String userName, String fName, String lName, String useremail, String pwd,
			String ph) {

		User userData = new User();
		userData.setId(Integer.parseInt(userID));
		userData.setUsername(userName);
		userData.setFirstName(fName);
		userData.setLastName(lName);
		userData.setEmail(useremail);
		userData.setPassword(pwd);
		userData.setPhone(ph);

		Response response = UserEndPoints.createUser(userData);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testReadUser(String userName) {
		Response response = UserEndPoints.readUser(userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testUpdateUser(String userName) {

		Faker faker = new Faker();
		User userData = new User();

		userData.setLastName(faker.name().lastName());
		userData.setPhone(faker.phoneNumber().cellPhone());

		Response response = UserEndPoints.updateUser(userData, userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);

		response = UserEndPoints.readUser(userName);
		response.then().log().all();
	}

	@Test(priority = 4, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUser(String userName) {

		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
