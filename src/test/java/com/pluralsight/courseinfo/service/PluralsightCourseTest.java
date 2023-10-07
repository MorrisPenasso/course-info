package com.pluralsight.courseinfo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PluralsightCourseTest {

	//explain that this is a parameterized test
	@ParameterizedTest
	
	//three parameters: input, expected. Thins test will be executed with this three different inputs
	@CsvSource(textBlock = """ 
			01:08:54.9613338, 68
			00:05:37, 5
			00:00:00, 0
			""")
	void durationInMinutes(String input, long expected) {
		PluralsightCourse course = new PluralsightCourse("id", "title", input, "url", false);
		assertEquals(expected, course.durationInMinutes());
	}
}
