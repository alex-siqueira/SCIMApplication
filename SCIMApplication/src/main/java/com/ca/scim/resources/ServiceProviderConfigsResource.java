package com.ca.scim.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path ("/ServiceProviderConfigs")
public class ServiceProviderConfigsResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConfigs() {
		Response response = null;
		try {
			response = Response.ok(getConfigJson()).build();
		} catch (IOException ioEx){
			response = Response.serverError().build();
		}
		return response;
	}
	
	private String getConfigJson() throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/ServiceProviderConfigs.json"), Charset.forName("UTF-8"));
		String line = reader.readLine();
		
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		reader.close();
		
		return sb.toString();
	}
}
