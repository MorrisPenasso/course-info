package com.pluralsight.courseinfo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.pluralsight.courseinfo.repository.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

class CourseStorageServiceTest {

	@Test
	void storePluralsightCourses() {
		
		CourseRepository repository = new InMemoryCourseRepository();
		CourseStorageService courseStorageService = new CourseStorageService(repository);
		
		PluralsightCourse psCourse = new PluralsightCourse("id", "title", "01:08:54.9613338", "/url-1", false);
		courseStorageService.storePluralsightCourses(List.of(psCourse));
		
		Course expected = new Course("id", "title", 68, courseStorageService.PS_BASE_URI + "/url-1", java.util.Optional.empty());
		assertEquals(expected, repository.getAllCourses().get(0));
	}
	
	static class InMemoryCourseRepository implements CourseRepository {

		private final List<Course> courses = new ArrayList<>();

		@Override
		public void saveCourse(Course course) {

			courses.add(course);
		}

		@Override
		public List<Course> getAllCourses() {
			return courses;
		}
		
	}

}
