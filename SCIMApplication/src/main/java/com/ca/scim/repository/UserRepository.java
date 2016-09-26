package com.ca.scim.repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ca.scim.datatypes.GroupWrapper;
import com.ca.scim.datatypes.Meta;
import com.ca.scim.datatypes.User;
import com.ca.scim.datatypes.UserSearch;

public class UserRepository {
	private static final String ROOT_URI = "http://172.17.1.1:8080/scim/v1/Users/";
	private List<User> users = new ArrayList<User>();
	private static UserRepository userRepo;
	
	private UserRepository(){
		populateInitialUsers();
	}

	public static UserRepository getInstance(){
		if (userRepo == null){
			userRepo = new UserRepository();			
		}
		return userRepo;
	}
	
	private void populateInitialUsers() {
		User alex = new User();
		alex.setId("1");
		alex.setName("Alexandre");
		alex.setUserName("alex");
		alex.setPassword("password");
		alex.setPhoneNumber("11991535339");
		alex.setEmail("alexandre.siqueira@ca.com");
		alex.setType("Employee");
		alex.setActive(true);
		
		GroupWrapper group1 = new GroupWrapper();
		group1.setValue("displayName=Services");
		alex.setGroups(new GroupWrapper[]{group1});
		
		Meta meta = new Meta();
		meta.setLocation(UserRepository.ROOT_URI + alex.getId());		
		alex.setMeta(meta);
		
		users.add(alex);
	}
	
	public List<User> getUsers(String filter){
		List<User> usersSearch = new ArrayList<User>();
		String[] filterParam = filter.split("\"");
	
		usersSearch = users.stream()
				.filter(f -> filter.equals("*") || f.getUserName().equals(filterParam[1]))
				.collect(Collectors.toList());
		return usersSearch;
	}
	
	public List<UserSearch> getUsersSearch(String filter){
		List<UserSearch> usersSearch = new ArrayList<UserSearch>();
		String[] filterParam = filter.split("\"");
	
		for (User user: users){
			if (filter.equals("*") || user.getUserName().equals(filterParam[1])){
				UserSearch userSearch = new UserSearch();
				userSearch.setId(user.getId());
				userSearch.setUserName(user.getUserName());
				userSearch.setMeta(user.getMeta());
				usersSearch.add(userSearch);
			}
		}

		return usersSearch;
	}
	
	public User getUser(String id) {
		Optional<User> user = users.stream()
			.filter(f -> f.getId().equals(id))
			.findFirst();
		
		return user.isPresent() == true ? user.get() : null;
	}
	
	public URI addUser(User user) throws Exception{
		user.setId("" + (users.size() + 1));
		URI uri = new URI(UserRepository.ROOT_URI + user.getId());
		user.getMeta().setLocation(uri.toString());
		users.add(user);
		return uri;
	}
	
	public void deleteUser(User user){
		users.remove(user);
	}
	
	public boolean editUser(User editedUser){
		boolean found = false;
		for(int i = 0; i < users.size(); i++){
			User user = users.get(i);
			if (user.getId().equals(editedUser.getId())){
				editedUser.setMeta(user.getMeta());
				users.set(i, editedUser);
				found = true;
				break;
			}
		}
		return found;
	}
}
