package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userpayload;
	public Logger logger;
	
	@BeforeClass
	public void setUpData()
	{
		faker = new Faker();
		userpayload = new User();
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger =LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*****************creating user*********************");
		Response res = UserEndpoints.createUser(userpayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****************user created successfully*********************");
	}
	
	@Test(priority=2)
	public void testGetUser()
	{
		logger.info("*****************reading user info*********************");
		Response res = UserEndpoints.readUser(this.userpayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****************info read suucessfully*********************");
	}
	
	@Test(priority=3)
	public void testPutUser()
	{
		logger.info("*****************updating user info*********************");
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		Response res = UserEndpoints.updateUser(this.userpayload.getUsername(), userpayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****************user updated*********************");
		
		logger.info("*****************reading user info*********************");
		//response after update we need to send get request again for validation
		Response responseafterupdate = UserEndpoints.readUser(this.userpayload.getUsername());
		responseafterupdate.then().log().all();
		Assert.assertEquals(responseafterupdate.getStatusCode(),200);
		String emailid = responseafterupdate.jsonPath().get("email").toString();
		Assert.assertEquals(emailid,userpayload.getEmail());
		logger.info("*****************info read suucessfully*********************");
		
	}
	
	@Test(priority=4)
	public void testDeleteUser()
	{
		logger.info("*****************deleting user*********************");
		Response res = UserEndpoints.deleteUser(this.userpayload.getUsername());
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*****************user deleted successfully*********************");
	}
	
	
	

}
