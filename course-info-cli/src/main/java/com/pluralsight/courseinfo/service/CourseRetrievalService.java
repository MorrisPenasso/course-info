package com.pluralsight.courseinfo.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CourseRetrievalService {
	private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";
	
	/**
	 * Create a http client
	 * and follow redirects setting from the server
	 */
	private static final HttpClient CLIENT = HttpClient.newBuilder()
			.followRedirects(HttpClient.Redirect.ALWAYS)
			.build();
	
	//Create an object mapper that permit to manipulate java object in json
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public List<PluralsightCourse> getCoursesFor(String authorID) {
		
		/**
		 * Create a http builder request 
		 * with an URI (URL) object 
		 * formatted replacing "$s" with authorID
		 * build the http request
		 */
		HttpRequest request = HttpRequest
				.newBuilder(URI.create(PS_URI.formatted(authorID)))
				.GET()
				.build();
						
		try {
			//Send the request and get the bytes of response of httpserver and trasmorm it to a string
			HttpResponse<String> response = CLIENT.send(request , HttpResponse.BodyHandlers.ofString());
			
			//response cases
			return switch (response.statusCode()) {
				case 200 -> toPluralsightCourses(response);
				
				case 400 -> List.of(); //create list with zero elements
				default -> throw new RuntimeException("Unexpected status code: " + response.statusCode());
			};
			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Could not call the pluralsight api: " + e);
			
		}
	}

	private List<PluralsightCourse> toPluralsightCourses(HttpResponse<String> response) throws JsonProcessingException, JsonMappingException {
		
		//
		/**
		 * Build the java type:
		 * create a type factory
		 * construct a parametric type with the class PluralsightCourse that specify the return type 
		 */
		JavaType returnType = OBJECT_MAPPER.getTypeFactory()
				.constructParametricType(List.class, PluralsightCourse.class);
		
		//Read the body of the response and convert it to a java PluralsightCourse object
		return OBJECT_MAPPER.readValue(response.body(), returnType);
	}
}
