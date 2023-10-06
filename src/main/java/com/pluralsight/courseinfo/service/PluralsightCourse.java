package com.pluralsight.courseinfo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignore others propertes
@JsonIgnoreProperties(ignoreUnknown = true)
public record PluralsightCourse(String id, String title, String duration, String contentUrl, boolean isRetired) {
	
	

}
