package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints2 {
	
	
	//for properties file
	public static ResourceBundle getUrl()
	{
		ResourceBundle resource = ResourceBundle.getBundle("routes"); //reading and loading prop file
		return resource;
	}
	
	
	public static Response createUser(User payload)
	
	{
		String post_url = getUrl().getString("post_url");
	    Response response=	given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(payload)
		
		.when()
		.post(post_url); //no need to create object because we have used static
	return response;
	}
	
	
	
public static Response readUser(String userName)
	
	{
	String get_url = getUrl().getString("get_url");
	Response response=	given()
		.pathParam("username", userName)		
		.when()
		.get(get_url); //no need to create object because we have used static
	return response;
	}

public static Response updateUser(String userName,User payload)

{
	String put_url = getUrl().getString("put_url");
Response response=	given()
	.contentType(ContentType.JSON)
	.accept(ContentType.JSON)
	.body(payload)
	.pathParam("username", userName)
	
	.when()
	.put(put_url); //no need to create object because we have used static
return response;
}

public static Response deleteUser(String userName)

{
	String delete_url = getUrl().getString("put_url");
Response response=	given()
	.pathParam("username", userName)		
	.when()
	.delete(delete_url); //no need to create object because we have used static
return response;
}


}
