package com.pluralsight.courseinfo.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pluralsight.courseinfo.repository.CourseRepository;
import com.pluralsight.courseinfo.service.CourseRetrievalService;
import com.pluralsight.courseinfo.service.CourseStorageService;
import com.pluralsight.courseinfo.service.PluralsightCourse;

public class CourseRetriever {
	private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

	public static void main(String[] args) {

		LOG.info("CourseRetriever started!");
		
		if(args.length == 0) {
			LOG.warn("Please provide an author name as first parameter.");
			return;
		}
		
		try {
			
			//retrieve courses
			retrieveCourses(args[0]);

			
		} catch (Exception e) {
		
			LOG.error("Error during retrieve courses: " + e);
			e.printStackTrace();
		}
	}

	private static void retrieveCourses(String authorId) {

		LOG.info("Retrieving courses for author: '{}'", authorId);
		
		//Create an CourseRetrievalService object
		CourseRetrievalService service = new CourseRetrievalService();
		
		CourseRepository repository = CourseRepository.openCourseRepository("./courses.db");
		CourseStorageService courseStorageService = new CourseStorageService(repository);
		
		/**
		 * retrieve courses and filter the list with courses that are not retired
		 * ( convert list to strem and riconvert to list ) 
		 */
		List<PluralsightCourse> coursesToStore = service.getCoursesFor(authorId).stream()
				.filter(course -> !course.isRetired()).toList();
		
		LOG.info("Retrieved the following {} courses {}", coursesToStore.size(), coursesToStore);
		
		courseStorageService.storePluralsightCourses(coursesToStore);
		LOG.info("Courses stored!");
	}
}