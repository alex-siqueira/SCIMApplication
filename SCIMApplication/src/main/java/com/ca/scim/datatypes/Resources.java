package com.ca.scim.datatypes;

import java.util.List;

public class Resources <T> {
	//private int startIndex;
	private int totalResults;
	//private int itemsPerPage;
	private List<T> Resources;

	public Resources() {

	}

	public List<T> getResources() {
		return Resources;
	}

	public void setResources(List<T> resources) {
		Resources = resources;
		//startIndex = 1;
		totalResults = resources.size();
		//itemsPerPage = resources.size();
	}

/*	public int getStartIndex() {
		return startIndex;
	}*/

	public int getTotalResults() {
		return totalResults;
	}

/*	public int getItemsPerPage() {
		return itemsPerPage;
	}
	*/
}
