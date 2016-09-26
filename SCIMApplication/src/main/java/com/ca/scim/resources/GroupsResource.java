package com.ca.scim.resources;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.ca.scim.datatypes.Group;
import com.ca.scim.datatypes.GroupSearch;
import com.ca.scim.datatypes.Resources;
import com.ca.scim.repository.GroupRepository;
import com.google.gson.Gson;

@Path("/Groups")
public class GroupsResource {

    @Context
    private UriInfo context;
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getGroups(@DefaultValue("*") @QueryParam("filter") String filter, @DefaultValue("displayName") @QueryParam("attributes") String attributes){
		Response response = null;
		
		GroupRepository groupRepo = GroupRepository.getInstance();
		List<GroupSearch> groups = groupRepo.getGroups(filter);
		
		if (groups != null && !groups.isEmpty()){
			Resources<GroupSearch> resources = new Resources<GroupSearch>();
			resources.setResources(groups);
			response = Response.ok(getJsonFromGroup(resources)).build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getGroup(@PathParam("id") String id){
		Response response = null;
		
		GroupRepository groupRepo = GroupRepository.getInstance();
		Group group = groupRepo.getGroup(id);
		
		if (group != null){
			response = Response.ok(getJsonFromGroup(group)).build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}
	
	private String getJsonFromGroup(Object obj){
		Gson gson = new Gson();
		return (gson.toJson(obj));
	}
	
	private Group getGroupFromJson(String json){
		Gson gson = new Gson();
		return (gson.fromJson(json, Group.class));
	}
}
