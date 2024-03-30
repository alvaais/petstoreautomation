package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataProviderTest {
	//create user
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPost(String UserId,String userName ,String fname, String lname, String useremail,String pwd,String phone)
	
	{
		User userpayload = new User();
		userpayload.setId(Integer.parseInt(UserId));
		userpayload.setUsername(userName);
		userpayload.setFirstname(fname);
		userpayload.setLastname(lname);
		userpayload.setEmail(useremail);
		userpayload.setPassword(pwd);
		userpayload.setPhone(phone);
			
		Response res = UserEndpoints.createUser(userpayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	}
	
	//get all the details
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testGetUser(String username)
	{
		Response res = UserEndpoints.readUser(username);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	}
	
	//delete user based on username
	@Test(priority=3,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testDeleteByName(String username)
	{
		Response res = UserEndpoints.deleteUser(username);
		Assert.assertEquals(res.getStatusCode(),200);
	}
}
