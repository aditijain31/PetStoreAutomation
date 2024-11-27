package api.endpoints;

// This file is being used to perform the CRUD operations.

import static io.restassured.RestAssured.*;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {
	
	public static Response createUser(User data){
		
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(data)
		.when()
			.post(Routes.post_URL);
		
		return response;
	}
	
	public static Response readUser(String userName){
		
		Response response = given()
			.pathParam("username", userName)
		.when()
			.get(Routes.get_URL);
		
		return response;
	}
	
	public static Response updateUser(User data, String userName){
		
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(data)
		.when()
			.put(Routes.update_URL);
		
		return response;
	}
	
	public static Response deleteUser(String userName){
		
		Response response = given()
				.pathParam("username", userName)
			.when()
				.delete(Routes.delete_URL);
			
			return response;
	}

}
