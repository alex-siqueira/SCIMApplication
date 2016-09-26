package com.ca.scim.datatypes;

public class Group {
	//private String[] schemas = {"urn:scim:schemas:core:1.0"};
	private String id;
	private String displayName;
	private String description;
	private Meta meta;

	public Group(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String name) {
		this.displayName = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
