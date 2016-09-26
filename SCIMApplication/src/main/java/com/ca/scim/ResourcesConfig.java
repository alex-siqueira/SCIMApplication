package com.ca.scim;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/scim/v1")
public class ResourcesConfig extends ResourceConfig {
	public ResourcesConfig(){
		packages("com.ca.scim.resources");
	}
}

