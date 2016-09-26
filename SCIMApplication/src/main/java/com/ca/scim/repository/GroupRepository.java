package com.ca.scim.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ca.scim.datatypes.Group;
import com.ca.scim.datatypes.GroupSearch;
import com.ca.scim.datatypes.Meta;

public class GroupRepository {
	private static final String ROOT_URI = "http://172.17.1.1:8080/scim/v1/Groups/";
	private List<Group> groups = new ArrayList<Group>();
	private static GroupRepository groupRepo;
	
	private GroupRepository(){
		populateInitialGroups();
	}

	public static GroupRepository getInstance(){
		if (groupRepo == null){
			groupRepo = new GroupRepository();			
		}
		return groupRepo;
	}
	
	private void populateInitialGroups() {
		Group group = new Group();
		group.setId("1");
		group.setDisplayName("Services");
		group.setDescription("Services Team");
		
		Meta meta = new Meta();
		meta.setLocation(GroupRepository.ROOT_URI + group.getId());		
		group.setMeta(meta);
		
		groups.add(group);
		
		Group group2 = new Group();
		group2.setId("2");
		group2.setDisplayName("PreSales");
		group2.setDescription("Pre-Sales Team");
		
		Meta meta2 = new Meta();
		meta2.setLocation(GroupRepository.ROOT_URI + group.getId());		
		group2.setMeta(meta2);
		
		groups.add(group2);
	}
	
	public List<GroupSearch> getGroups(String filter){
		List<GroupSearch> groupsSearch = new ArrayList<GroupSearch>();
		String[] filterParam = filter.split("\"");

		
		for (Group group : groups) {			
			if ( filter.equals("*") || 
				(filterParam[0].startsWith("displayName") && group.getDisplayName().equals(filterParam[1])) ||
				(filterParam[0].startsWith("id") && group.getId().equals(filterParam[1]))){
				GroupSearch groupSearch = new GroupSearch();
				groupSearch.setId(group.getId());;
				groupSearch.setDisplayName(group.getDisplayName());
				groupSearch.setMeta(group.getMeta());
				groupsSearch.add(groupSearch);
			} 
		}
		
		return groupsSearch;		
	}
	
	public Group getGroup(String id) {
		Optional<Group> group = groups.stream()
			.filter(f -> f.getId().equals(id))
			.findFirst();
		
		return group.isPresent() == true ? group.get() : null;
	}
}
