package com.ca.scim.datatypes;

public class User {
	//private String[] schemas = {"urn:scim:schemas:core:1.0"};
	private String id;
	private String userName;
	private String name;
	private String email;
	private String phoneNumber;
	private String type;
	private boolean active;
	private String password;
	private GroupWrapper[] groups;
	private Meta meta;
	
	public User() {
		this.meta = new Meta();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String userType) {
		this.type = userType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public GroupWrapper[] getGroups() {
		return groups;
	}
	
	public void setGroups(GroupWrapper[] groups) {
		this.groups = groups;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
