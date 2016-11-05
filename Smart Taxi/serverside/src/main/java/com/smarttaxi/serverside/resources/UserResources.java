package com.smarttaxi.serverside.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.smarttaxi.serverside.model.User;



@Path("/user")
public class UserResources 
{
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login (User user) throws JSONException 
	{
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		if (user.getUsername().equals("admin") && user.getPassword().equals("admin"))
			{
				json.put("Status", 1);
				json2.put("username", "admin");
				json2.put("fullname", "admin");
				json3.put("Result", json);
				json3.put("User", json2);
			}
			
			else  
				{json.put("Status", 0);
				json2.put("username", "******");
				json2.put("fullname", "******");
				json3.put("Result", json);
				json3.put("User", json2);
			 }
		String jstring =json3.toString();
		return jstring; 
	}
		
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createuser(User user) throws JSONException
	{
		JSONObject json = new JSONObject();
		if(user.getUsername().equals("admin"))   //tester si username existe ou pas dans la base de données 
		{
			json.put("Status", 0);        // return 0 si le compte existe deja 
		}
		
		else
		{
			//User newuser = new User(user.getUsername(),user.getPassword(),user.getFullname(),user.getEmail(),user.getPhone());
			// placer le nouveau comte dans la BD
			json.put("Status", 1);			//return 1 s'il s'agit d'un nouveau compte
		}  
		String jstring =json.toString();
		return jstring;
	}
	
	
	
	
			
}
