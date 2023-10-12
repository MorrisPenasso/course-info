package com.pluralsight.courseinfo.service;

import java.time.Duration;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignore others propertes
@JsonIgnoreProperties(ignoreUnknown = true)
public record PluralsightCourse(String id, String title, String duration, String contentUrl, boolean isRetired) {
	
	//Return the minutes from the duration hh:mm:ss
	public long durationInMinutes() {
		
		//Get the duration from 00:00 and the parse from 00:00:00 to 00:00 and get the minutes
		return Duration.between(LocalTime.MIN, LocalTime.parse(duration)).toMinutes();
	}

}
