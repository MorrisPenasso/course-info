package com.pluralsight.courseinfo.cli;

public class CourseRetriever {

	public static void main(String[] args) {

		System.out.println("CourseRetriever started!");
		
		if(args.length == 0) {
			System.out.println("Please provide an author name as first parameter.");
			return;
		}
		
		try {
			retrieveCourses(args[0]);
			
		} catch (Exception e) {
		
			System.out.println("Error during retrieve courses");
			e.printStackTrace();
		}
	}

	private static void retrieveCourses(String authorId) {

		System.out.println("Retrieving courses for author: " + authorId);
	}
	

}
