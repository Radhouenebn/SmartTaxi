package com.smarttaxi.serverside.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smarttaxi.serverside.model.Conducteur;

@Path("/conducteur")
public class ConducteurResources {

		@GET
	    @Path("/afficher")
	    @Produces(MediaType.APPLICATION_JSON)
		public String afficher_map() throws JSONException
		{
			//pour le teste , instantiation de trois conducteurs
				Conducteur c1 = new Conducteur(); c1.setFullname("ahmed");c1.setLat("36.814628");c1.setLon("10.168620");
			//	Conducteur c2 = new Conducteur(); c2.setFullname("radhouene");c2.setLat(36.816350);c2.setLon(10.168325);
			//	Conducteur c3 = new Conducteur(); c3.setFullname("amir");c3.setLat(36.814699);c3.setLon(10.166158);
			//produire un ficier json 
		   // qui contient un array contenant des format json (3 fichiers dans ce teste 
			JSONObject jcon1 = new JSONObject();
		//	JSONObject jcon2 = new JSONObject();
		//	JSONObject jcon3 = new JSONObject();
			jcon1.put("fullname", c1.getFullname()); jcon1.put("latitude",c1.getLat()); jcon1.put("longitude",c1.getLon());
			//jcon2.put("fullname", c2.getFullname()); jcon2.put("latitude",c1.getLat()); jcon2.put("longitude",c1.getLon());
			//jcon3.put("fullname", c3.getFullname()); jcon3.put("latitude",c1.getLat()); jcon3.put("longitude",c1.getLon());
			JSONArray arr = new JSONArray();
			arr.put(0,jcon1);
		//	arr.put(1,jcon2);
		//	arr.put(2,jcon3);
			JSONObject total = new JSONObject();
			total.put("conducteurs", arr);
			return total.toString();
		}
		
		
			
	
	
	
	
}
