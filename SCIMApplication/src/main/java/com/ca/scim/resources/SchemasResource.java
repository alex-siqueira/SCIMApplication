package com.ca.scim.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path ("/Schemas")
public class SchemasResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSchemas() {
		Response response = null;
		try {
			response = Response.ok(getSchemaJson("")).build();
		} catch (IOException ioEx){
			response = Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex){
			response = Response.serverError().build();
		}
		
		return response;
	}
	
	
	@GET
	@Path("/{endpoint}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSchemaByEndpoint(@PathParam(value = "endpoint") String endpoint) {
		Response response = null;
		try {
			response = Response.ok(getSchemaJson(endpoint)).build();
		} catch (IOException ioEx){
			response = Response.status(Status.NOT_FOUND).build();
		} catch (Exception ex){
			response = Response.serverError().build();
		}
		
		return response;
	}
	
	private String getSchemaJson(String name) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/Schema" + name + ".json"), Charset.forName("UTF-8"));
		String line = reader.readLine();
		
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		reader.close();
		
		return sb.toString();
	}	
}
