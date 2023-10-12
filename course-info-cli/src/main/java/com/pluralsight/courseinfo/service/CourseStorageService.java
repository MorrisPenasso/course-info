package com.pluralsight.courseinfo.service;

import java.util.List;

import com.pluralsight.courseinfo.repository.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

public class CourseStorageService {
	
	public static final String PS_BASE_URI = "https://app.pluralsight.com";

	private final CourseRepository courseRepository;
	
	public CourseStorageService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	public void storePluralsightCourses(List<PluralsightCourse> courses) {
		for(PluralsightCourse psCourse : courses) {
			
			Course course = new Course(psCourse.id(), psCourse.title(), psCourse.durationInMinutes(), PS_BASE_URI + psCourse.contentUrl(), java.util.Optional.empty());
			courseRepository.saveCourse(course);
		}
	}
}
