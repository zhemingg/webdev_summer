package webdev.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Course;
import webdev.repositories.CourseRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseServices {
	@Autowired
	CourseRepository courseRepository;

	@GetMapping("/api/course")
	public Iterable<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	@PostMapping("/api/course")
	public Course createCourse(@RequestBody Course course) {
		Date time = new Date();
		System.out.println(time);
		course.setModified(time);
		course.setCreated(time);
		return courseRepository.save(course);
	}
	
	@GetMapping("/api/course/{id}")
	public Course findCourseById(@PathVariable("id") int id) {	
		System.out.println("1*******************");
		Optional<Course> data =  courseRepository.findById(id);
		System.out.println("*******************");
		if (data.isPresent()) {
			System.out.println(data.get()+"*******************");
			return data.get();
		}
		return null;	
	}
	
	
	@DeleteMapping("/api/course/{courseId}")
	public void deleteCourse(
	@PathVariable("courseId") int id) {
		courseRepository.deleteById(id);
	}


}
