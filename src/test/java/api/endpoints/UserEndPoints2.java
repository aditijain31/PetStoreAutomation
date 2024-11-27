package api.endpoints;

// This file is being used to perform the CRUD operations.

import static io.restassured.RestAssured.*;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints2 {
	
	static ResourceBundle getURL(){
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // Load routes.properties file
		return routes;
	}
	
	public static Response createUser(User data){
		
		String post_url = getURL().getString("post_url");
		
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(data)
		.when()
			.post(post_url);
		
		return response;
	}
	
	public static Response readUser(String userName){
		
		String get_url = getURL().getString("get_url");
		
		Response response = given()
			.pathParam("username", userName)
		.when()
			.get(get_url);
		
		return response;
	}
	
	public static Response updateUser(User data, String userName){
		
		String update_url = getURL().getString("update_url");
		
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(data)
		.when()
			.put(update_url);
		
		return response;
	}
	
	public static Response deleteUser(String userName){
		
		String delete_url = getURL().getString("delete_url");
		
		Response response = given()
				.pathParam("username", userName)
			.when()
				.delete(delete_url);
			
			return response;
	}

}
