package com.pluralsight.courseinfo.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetrievalService {
	private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";
	
	private static final HttpClient CLIENT = HttpClient.newBuilder()
			.followRedirects(HttpClient.Redirect.ALWAYS)
			.build();
	
	public List<PluralsightCourse> getCoursesFor(String authorID) {
		
		/**
		 * Create a builder http request 
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
			
			return switch (response.statusCode()) {
				case 200 -> null;
				case 400 -> List.of(); //zero courses
				default -> throw new RuntimeException("Unexpected status code: " + response.statusCode());
			};
			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Could not call the pluralsight api: " + e);
			
		}
	}
}
