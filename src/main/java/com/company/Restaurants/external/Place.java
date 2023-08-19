package com.company.Restaurants.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Place {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("vicinity")
	private String vicinity;

	public Place() {}
	
	public Place(String name, String vicinity) {
		this.name = name;
		this.vicinity = vicinity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}
}
